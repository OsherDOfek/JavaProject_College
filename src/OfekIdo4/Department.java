package OfekIdo4;
public class Department {
	private String dep_name;
	private int num_student;
	private Lecturer[] lec_arr;
	private int numOfLecturer;
	
	
	// Department constructor
	public Department(String dep_name,int num_student) throws InvalidStudentNumberException {
		if(num_student < 0) {
			throw new InvalidStudentNumberException();
		}
		this.dep_name = dep_name;
		this.num_student = num_student;
		this.lec_arr = new Lecturer[2];
		this.numOfLecturer = 0;
	}

	// add lecturer to the department's array
	public void add_lecturer(Lecturer lec) throws AlreadyInDepartmentException {
		if(in_lec_arr(lec)) {
			throw new AlreadyInDepartmentException();								// already in the department
		}
		if(this.lec_arr.length == this.numOfLecturer) {			// check if needs to increase the array
			Lecturer[] temp = new Lecturer[this.lec_arr.length*2];
			for(int i = 0; i < this.numOfLecturer; i++) {		// copying the array to a temporary
				temp[i] = this.lec_arr[i];
			}
			this.lec_arr = temp;
		}
		this.lec_arr[this.numOfLecturer++] = lec;					// adding the new lecturer to the array
	}
	
	public boolean in_lec_arr(Lecturer l) {
		for(int i = 0; i < this.numOfLecturer; i ++) {
			if(this.lec_arr[i].getName().equals(l.getName())) {
				return true;
			}
		}
		return false;
	}
	
	// returns the average salary of the lecturers in the departments
	public double avg_salary() {
		if(this.numOfLecturer == 0)
			return 0;
		int sum = 0;
		for (int i = 0; i < this.numOfLecturer ; i++) {
			sum += this.lec_arr[i].getSalary(); 
		}
		return (double) sum/this.numOfLecturer;
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

	public Lecturer[] getLec_arr() {
		return lec_arr;
	}

	
	public int getNumOfLecturer() {
		return numOfLecturer;
	}

	public void setNumOfLecturer(int numOfLecturer) {
		this.numOfLecturer = numOfLecturer;
	}

	public void setLec_arr(Lecturer[] lec_arr) {
	    if (lec_arr != null)
	        this.lec_arr = lec_arr;
	}

	public String toStringLecturer() {
		if(this.numOfLecturer == 0)
			return "Lecturer: None";
		
		String s = "Lecturers: ";
		for(int i = 0; i < this.numOfLecturer; i++) {
			s += this.lec_arr[i].getName();
			if(i < this.numOfLecturer - 1)
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
