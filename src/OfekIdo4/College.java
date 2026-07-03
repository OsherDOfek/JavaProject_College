package OfekIdo4;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class College implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String SAVE_FILE = "college.dat";	// binary save file (part 3)

	// part 2: ArrayList instead of arrays - no counters and no enlarge methods needed
	private ArrayList<Committee<Lecturer>> com;
	private ArrayList<Department> dep;
	private ArrayList<Lecturer> lec;
	private String college_name;

	public College(String college_name) {
		this.college_name = college_name;
		this.com = new ArrayList<>();
		this.dep = new ArrayList<>();
		this.lec = new ArrayList<>();
	}

	// Creates a new committee with the degree level chosen at creation time (part 1).
	public void add_com(String com_name, Lecturer head, int required_level) throws CommitteeAlreadyExistsException, InvalidDegreeException {
		if (get_committee(com_name) != null) {
			throw new CommitteeAlreadyExistsException();
		}
		this.com.add(new Committee<>(com_name, head, required_level));
	}

	// Duplicates an existing committee: new name is <name>-new, same head, level and members
	public void duplicate_committee(String com_name) throws CommitteeAlreadyExistsException, InvalidDegreeException, AlreadyCommitteeMemberException, IncompatibleMemberDegreeException {
		Committee<Lecturer> original = get_committee(com_name);
		String newName = original.getName() + "-new";
		if (get_committee(newName) != null) {
			throw new CommitteeAlreadyExistsException();
		}
		this.com.add(original.duplicate());
	}

	// Adds a lecturer to the college (throws if the name is already taken).
	// If the lecturer has a department, he is linked to that department as well.
	public void add_lecturer(Lecturer l1) throws LecturerAlreadyExistsException {
		if (get_lecturer(l1.getName()) != null) {
			throw new LecturerAlreadyExistsException();
		}
		this.lec.add(l1);

		if (!l1.getDep_name().equals("")) {
			Department d = get_department(l1.getDep_name());
			if (d != null) {
				try {
					d.add_lecturer(l1);
				} catch (AlreadyInDepartmentException e) {
					// unreachable: l1 is a brand new lecturer
				}
			}
		}
	}

	public static void checkValidName(String name) throws InvalidNameException {
		if (name == null || name.isEmpty()) {
			throw new InvalidNameException();
		}
		for (int i = 0; i < name.length(); i++) {
			if (!Character.isLetter(name.charAt(i))) {
				throw new InvalidNameException();
			}
		}
	}

	// Checks if the lecturer is in the college
	public void check_lecturer_name(String name) throws NameDoesntExistsException, InvalidNameException {
		if (name.equals(""))
			throw new InvalidNameException();
		if (get_lecturer(name) == null)
			throw new NameDoesntExistsException();
	}

	public void check_lecturer_id(String id) throws InvalidIdException {
		if (id == null || id.isEmpty())
			throw new InvalidIdException();
		for (int i = 0; i < id.length(); i++) {
			if (!Character.isDigit(id.charAt(i))) {
				throw new InvalidIdException();
			}
		}
	}

	public void check_committe_in_college(String com_name) throws CommitteeDoesntExistsException {
		if (get_committee(com_name) == null)
			throw new CommitteeDoesntExistsException();
	}

	public void department_exists(String dep_name) throws DepartmentAlreadyExistsException {
		if (get_department(dep_name) != null) {
			throw new DepartmentAlreadyExistsException();
		}
	}

	// Creates a new department
	public void add_department(String dep_name, int num_students) throws DepartmentAlreadyExistsException, InvalidStudentNumberException {
		if (get_department(dep_name) != null) {
			throw new DepartmentAlreadyExistsException();
		}
		this.dep.add(new Department(dep_name, num_students));
	}

	// Returns the average salary of all lecturers in the college (0 when there are none)
	public double avg_salary_college() {
		if (this.lec.isEmpty())
			return 0;
		long sum = 0;
		for (int i = 0; i < this.lec.size(); i++)
			sum += this.lec.get(i).getSalary();
		return (double) sum / this.lec.size();
	}

	public Committee<Lecturer> get_committee(String com_name) {
		for (int i = 0; i < this.com.size(); i++) {
			if (this.com.get(i).getName().equals(com_name))
				return this.com.get(i);
		}
		return null;
	}

	public Lecturer get_lecturer(String lec_name) {
		for (int i = 0; i < this.lec.size(); i++) {
			if (this.lec.get(i).getName().equals(lec_name))
				return this.lec.get(i);
		}
		return null;
	}

	public Department get_department(String dep_name) {
		for (int i = 0; i < this.dep.size(); i++) {
			if (this.dep.get(i).getDep_name().equals(dep_name))
				return this.dep.get(i);
		}
		return null;
	}

	// ---------- part 3: binary file save/load ----------

	// Saves the whole college (and everything it references) to a binary file
	public void saveToFile() throws IOException {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
			out.writeObject(this);
		}
	}

	// Loads the college from the binary file. Returns null if there is no saved file.
	public static College loadFromFile() {
		File f = new File(SAVE_FILE);
		if (!f.exists()) {
			return null;	// no saved data - the system starts empty
		}
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(f))) {
			return (College) in.readObject();
		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Warning: couldn't load saved data, starting fresh");
			return null;
		}
	}

	// ---------- Getters and Setters ----------

	public ArrayList<Committee<Lecturer>> getCom() {
		return com;
	}

	public void setCom(ArrayList<Committee<Lecturer>> com) {
		this.com = com;
	}

	public ArrayList<Department> getDep() {
		return dep;
	}

	public void setDep(ArrayList<Department> dep) {
		this.dep = dep;
	}

	public ArrayList<Lecturer> getLec() {
		return lec;
	}

	public void setLec(ArrayList<Lecturer> lec) {
		this.lec = lec;
	}

	public String getCollege_name() {
		return college_name;
	}

	public void setCollege_name(String college_name) {
		this.college_name = college_name;
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
		for (int i = 0; i < com.size(); i++)
			s += "  " + com.get(i).toString() + "\n";
		s += "Departments:\n";
		for (int i = 0; i < dep.size(); i++)
			s += "  " + dep.get(i).toString() + "\n";
		s += "Lecturers:\n";
		for (int i = 0; i < lec.size(); i++)
			s += "  " + lec.get(i).toString() + "\n";
		return s;
	}
}
