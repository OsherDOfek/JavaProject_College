package OfekIdo4;

public class Doctor extends Lecturer implements Comparable{
	protected String[] papersList;
	protected int numOfPapers;
	
	public Doctor(String name, String id, String degree_name, int salary, String dep_name) throws InvalidSalaryException {
		super(name,id,degree_name,salary,dep_name);
		this.papersList = new String[2];
		this.numOfPapers = 0;
	}

	public String[] getPapersList() {
		return papersList;
	}

	public void setPapersList(String[] papersList) {
		this.papersList = papersList;
	}

	// Adds a new published paper to the doctor's list (enlarges the array if needed)
	public void add_paper(String paper) {
		if(this.numOfPapers == this.papersList.length) {
			String[] temp = new String[this.papersList.length*2];
			for(int i = 0; i < this.numOfPapers; i++) {
				temp[i] = this.papersList[i];
			}
			this.papersList = temp;
		}
		this.papersList[this.numOfPapers++] = paper;
	}

	// builds a readable string of the papers list (avoids printing the raw array reference)
	public String toStringPapers() {
		if(this.numOfPapers == 0)
			return "Papers: None";
		String s = "Papers: ";
		for(int i = 0; i < this.numOfPapers; i++) {
			s += this.papersList[i];
			if(i < this.numOfPapers - 1)
				s += ", ";
		}
		return s;
	}
	
	@Override
	public int compareTo(Object o) {
		if(this.numOfPapers < ((Doctor)o).numOfPapers)
			return -1;
		else if(this.numOfPapers > ((Doctor)o).numOfPapers)
			return 1;
		return 0;
	}
	

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	public int getNumOfPapers() {
		return numOfPapers;
	}

	public void setNumOfPapers(int numOfPapers) {
		this.numOfPapers = numOfPapers;
	}
	
	public int getLevel() {
		return 2;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (!super.equals(o))
	    	return false;
	    Doctor other = (Doctor) o;
	    return this.numOfPapers == other.numOfPapers;
	}
	
	public String toString() {
		return super.toString() + ", " + toStringPapers();
	}
}
