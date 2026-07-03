package OfekIdo4;
import java.util.ArrayList;

public class Doctor extends Lecturer implements Comparable<Doctor> {
	private static final long serialVersionUID = 1L;

	protected ArrayList<String> papersList;	// part 2: ArrayList instead of array + counter

	public Doctor(String name, String id, String degree_name, int salary, String dep_name) throws InvalidSalaryException {
		super(name, id, degree_name, salary, dep_name);
		this.papersList = new ArrayList<>();
	}

	public ArrayList<String> getPapersList() {
		return papersList;
	}

	public void setPapersList(ArrayList<String> papersList) {
		this.papersList = papersList;
	}

	// Adds a new published paper to the doctor's list
	public void add_paper(String paper) {
		this.papersList.add(paper);
	}

	public int getNumOfPapers() {
		return this.papersList.size();
	}

	// builds a readable string of the papers list
	public String toStringPapers() {
		if (this.papersList.isEmpty())
			return "Papers: None";
		String s = "Papers: ";
		for (int i = 0; i < this.papersList.size(); i++) {
			s += this.papersList.get(i);
			if (i < this.papersList.size() - 1)
				s += ", ";
		}
		return s;
	}

	// compare doctors/professors by number of published papers
	@Override
	public int compareTo(Doctor o) {
		return Integer.compare(this.getNumOfPapers(), o.getNumOfPapers());
	}

	@Override
	public int getLevel() {
		return 2;
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		Doctor other = (Doctor) o;
		return this.getNumOfPapers() == other.getNumOfPapers();
	}

	public String toString() {
		return super.toString() + ", " + toStringPapers();
	}
}
