package OfekIdo4;
import java.io.Serializable;
import java.util.ArrayList;

// Not generic: a department holds lecturers of every degree (no homogeneity rule here)
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	private String dep_name;
	private int num_student;
	private ArrayList<Lecturer> lecturers;	// part 2: ArrayList instead of array + counter

	// Department constructor
	public Department(String dep_name, int num_student) throws InvalidStudentNumberException {
		setNum_student(num_student);
		this.dep_name = dep_name;
		this.lecturers = new ArrayList<>();
	}

	// add lecturer to the department's list
	public void add_lecturer(Lecturer lec) throws AlreadyInDepartmentException {
		if (this.lecturers.contains(lec)) {
			throw new AlreadyInDepartmentException();	// already in the department
		}
		this.lecturers.add(lec);
	}

	// returns the average salary of the lecturers in the department
	public double avg_salary() {
		if (this.lecturers.isEmpty())
			return 0;
		long sum = 0;
		for (int i = 0; i < this.lecturers.size(); i++) {
			sum += this.lecturers.get(i).getSalary();
		}
		return (double) sum / this.lecturers.size();
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
		if (num_student < 0) {
			throw new InvalidStudentNumberException();
		}
		this.num_student = num_student;
	}

	public ArrayList<Lecturer> getLecturers() {
		return lecturers;
	}

	public void setLecturers(ArrayList<Lecturer> lecturers) {
		this.lecturers = lecturers;
	}

	public String toStringLecturer() {
		if (this.lecturers.isEmpty())
			return "Lecturers: None";
		String s = "Lecturers: ";
		for (int i = 0; i < this.lecturers.size(); i++) {
			s += this.lecturers.get(i).getName();
			if (i < this.lecturers.size() - 1)
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
		return "Department --> name: " + this.dep_name + ", number of students: " + num_student + ", " + toStringLecturer();
	}
}
