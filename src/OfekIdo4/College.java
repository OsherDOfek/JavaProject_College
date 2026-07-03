package OfekIdo4;
import java.util.ArrayList;
public class College<T extends Lecturer>{
	private ArrayList<Committee<T>> com;
	private ArrayList<Department<T>> dep;
	private ArrayList<Lecturer> lec;
	private String college_name;

	public College(String college_name) {
		this.com = new Committee[2];
		this.numOfCommittee = 0;
		this.numOfDepartment = 0;
		this.numOfLecturer = 0;
		this.college_name = college_name;
		this.dep = new Department[2];
		this.lec = new Lecturer[2];
	}

	// Creates a new committee. Throws CommitteeAlreadyExistsException if the name is taken,
	// or InvalidDegreeException if the head isn't at least a Doctor (checked by the Committee constructor).
	public void add_com(String com_name, Lecturer head,int degree_level) throws CommitteeAlreadyExistsException, InvalidDegreeException {
		for(int i = 0; i < this.numOfCommittee; i++) {
			if(this.com[i].getName().equals(com_name))
				throw new CommitteeAlreadyExistsException();
		}
		if(this.numOfCommittee == this.com.length)
			enlarge_com_arr();
		this.com[this.numOfCommittee] = new Committee(com_name, head);
		this.numOfCommittee++;
		return;
	}

	// Duplicates an existing committee: new name is <name>-new, same head and same members
	public void duplicate_committee(String com_name) throws CommitteeAlreadyExistsException, InvalidDegreeException, AlreadyCommitteeMemberException {
		Committee original = get_committee(com_name);
		String newName = original.getName() + "-new";
		for(int i = 0; i < this.numOfCommittee; i++) {
			if(this.com[i].getName().equals(newName))
				throw new CommitteeAlreadyExistsException();
		}
		Committee copy = original.duplicate();
		if(this.numOfCommittee == this.com.length)
			enlarge_com_arr();
		this.com[this.numOfCommittee] = copy;
		this.numOfCommittee++;
	}

	public void enlarge_com_arr() {
		Committee[] temp = new Committee[this.numOfCommittee*2];
		for(int i = 0; i < this.numOfCommittee; i++) {
			temp[i] = this.com[i];
		}
		this.com = temp;
	}

	public void enlarge_lec_arr() {
		Lecturer[] temp = new Lecturer[this.numOfLecturer*2];
		for(int i = 0; i < this.numOfLecturer; i++) {
			temp[i] = this.lec[i];
		}
		this.lec = temp;
	}

	// Adds a lecturer to the college (throws if the name is already taken, and the caller/main
	// already validated that the department, if any, exists). If the lecturer has a department,
	// he is linked to that single department as well.
	public void add_lecturer(Lecturer l1) throws LecturerAlreadyExistsException {
		for(int i = 0; i < this.numOfLecturer; i++) {
			if(this.lec[i].getName().equals(l1.getName()))
				throw new LecturerAlreadyExistsException();
		}
		if(this.lec.length == this.numOfLecturer)
			enlarge_lec_arr();

		this.lec[this.numOfLecturer] = l1;
		this.numOfLecturer++;

		if (!l1.getDep_name().equals("")) {
			for (int i = 0; i < this.numOfDepartment; i++) {
				if (l1.getDep_name().equals(this.dep[i].getDep_name())) {
					try {
						this.dep[i].add_lecturer(l1);
					} catch (AlreadyInDepartmentException e) {
						// unreachable: l1 is a brand new lecturer, can't already be in the department
					}
					break;
				}
			}
		}
		return;
	}
	
	public static void checkValidName(String name) throws InvalidNameException {
        if (name == null || name.isEmpty()) {
            throw new InvalidNameException();
        }
            for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            
            if (!Character.isLetter(c)) {
                throw new InvalidNameException();
            }
        }
    }
	// Checks if the lecturer is in college
	public void check_lecturer_name(String name) throws NameDoesntExistsException,InvalidNameException{
		if(name.equals(""))
			throw new InvalidNameException();
		for(int i = 0; i < this.numOfLecturer; i ++) {
			if(this.lec[i].getName().equals(name)) {
				return;
			}
		}
		throw new NameDoesntExistsException();
	}

	public void check_lecturer_id(String id)throws InvalidIdException{
		if(id == null || id.isEmpty())
			throw new InvalidIdException();
		// מעבר על כל תו ובדיקה אם הוא ספרה
        for (int i = 0; i < id.length(); i++) {
            char c = id.charAt(i);
            if (!Character.isDigit(c)) {
                throw new InvalidIdException();
            }
        }
        return;
	}

	public void check_committe_in_college(String com_name) throws CommitteeDoesntExistsException{
		for(int i = 0; i < this.numOfCommittee; i++) {
			if(this.com[i].getName().equals(com_name))
				return;
		}
		throw new CommitteeDoesntExistsException();
	}

	public void department_exists(String dep_name) throws DepartmentAlreadyExistsException{
		if(get_department(dep_name) != null) {
			throw new DepartmentAlreadyExistsException();
		}
		
	}

	// Creates a new department (the caller/main already validated the name)
	public void add_department(String dep_name, int num_students) throws DepartmentAlreadyExistsException, InvalidStudentNumberException{
	    for (int i = 0; i < this.numOfDepartment; i++) {
	        if (this.dep[i].getDep_name().equals(dep_name)) {
	            throw new DepartmentAlreadyExistsException();
	        }
	    }
	    if (this.numOfDepartment == this.dep.length)
	        enlarge_dep_arr();

	    this.dep[this.numOfDepartment] = new Department(dep_name, num_students);
	    this.numOfDepartment++;
	}

	public void enlarge_dep_arr() {
		Department[] temp = new Department[Math.max(this.numOfDepartment * 2, 2)];
	    for (int i = 0; i < this.numOfDepartment; i++)
	        temp[i] = this.dep[i];
	    this.dep = temp;
	}

	// Returns the average salary of all lecturers in the college (0 when there are none)
	public double avg_salary_college() {
	    if (this.numOfLecturer == 0)
	    	return 0;
    	long sum = 0;
    	for (int i = 0; i < this.numOfLecturer; i++)
        	sum += this.lec[i].getSalary();
    	return (double) sum / this.numOfLecturer;
	}

	public Committee get_committee(String com_name) {
	    for (int i = 0; i < this.numOfCommittee; i++) {
	        if (this.com[i].getName().equals(com_name))
	            return this.com[i];
	    }
	    return null;
	}

	public Lecturer get_lecturer(String lec_name) {
	    for (int i = 0; i < this.numOfLecturer; i++) {
	        if (this.lec[i].getName().equals(lec_name))
	            return this.lec[i];
	    }
	    return null;
	}

	public Department get_department(String dep_name) {
	    for (int i = 0; i < this.numOfDepartment; i++) {
	        if (this.dep[i].getDep_name().equals(dep_name))
	            return this.dep[i];
	    }
	    return null;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    College other = (College) o;
	    return this.college_name.equals(other.college_name);
	}
	
	public String toString() {
	    String s = "College: " + college_name + "\n";
	    s += "Committees:\n";
	    for (int i = 0; i < numOfCommittee; i++)
	        s += "  " + com[i].toString() + "\n";
	    s += "Departments:\n";
	    for (int i = 0; i < numOfDepartment; i++)
	        s += "  " + dep[i].toString() + "\n";
	    s += "Lecturers:\n";
	    for (int i = 0; i < numOfLecturer; i++)
	        s += "  " + lec[i].toString() + "\n";
	    return s;
	}

	public Committee[] getCom() {
		return com;
	}

	public void setCom(Committee[] com) {
		this.com = com;
	}

	public Department[] getDep() {
		return dep;
	}

	public void setDep(Department[] dep) {
		this.dep = dep;
	}

	public Lecturer[] getLec() {
		return lec;
	}

	public void setLec(Lecturer[] lec) {
		this.lec = lec;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}

	public int getNumOfLecturer() {
		return numOfLecturer;
	}

	public int getNumOfDepartment() {
		return numOfDepartment;
	}

	public int getNumOfCommittee() {
		return numOfCommittee;
	}

	public void setNumOfLecturer(int numOfLecturer) {
		this.numOfLecturer = numOfLecturer;
	}

	public void setNumOfDepartment(int numOfDepartment) {
		this.numOfDepartment = numOfDepartment;
	}

	public void setNumOfCommittee(int numOfCommittee) {
		this.numOfCommittee = numOfCommittee;
	}

}
