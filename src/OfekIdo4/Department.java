package OfekIdo4;
import java.util.ArrayList;
public class Department<T extends Lecturer> {
	private String dep_name;
	private int num_student;
	private ArrayList<T> lecturers;
	
	
	// Department constructor
	public Department(String dep_name,int num_student) throws InvalidStudentNumberException {
		setNum_student(num_student);
		this.dep_name = dep_name;
		this.lecturers = new ArrayList<T>();
	}

	// add lecturer to the department's array
	public void add_lecturer(Lecturer lec) throws AlreadyInDepartmentException {
		if(this.lecturers.contains(lec)) {
			throw new AlreadyInDepartmentException();								// already in the department
		}
		this.lecturers.add((T)lec);
	}
	
	
	// returns the average salary of the lecturers in the departments
	public double avg_salary() {
		if(this.lecturers.size() == 0)
			return 0;
		int sum = 0;
		for (int i = 0; i < this.lecturers.size(); i++) {
			sum += this.lecturers.get(i).getSalary(); 
		}
		return (double) sum/this.lecturers.size();
	}

	
	// Getters and Setters
	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public int getNum_student() {
		return num_student;
	}

	public void setNum_student(int num_student) throws InvalidStudentNumberException {
		if(num_student < 0) {
			throw new InvalidStudentNumberException();
		}
		this.num_student = num_student;
	}


	public ArrayList<T> getLecturers() {
		return lecturers;
	}

	public void setLecturers(ArrayList<T> lecturers) {
		this.lecturers = lecturers;
	}

	public String toStringLecturer() {
		if(this.lecturers.size() == 0)
			return "Lecturer: None";
		
		String s = "Lecturers: ";
		for(int i = 0; i < this.lecturers.size(); i++) {
			s += this.lecturers.get(i).getName();
			if(i < this.lecturers.size() - 1)
				s += ", ";
		}
		return s;
	}
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Department other = (Department) o;
	    return this.dep_name.equals(other.dep_name);
	}
	
	// department toString func
	public String toString() {
		return "Department --> name:" + this.dep_name + ", number of student:" + num_student + ", " + toStringLecturer();
	}
	
	
}
