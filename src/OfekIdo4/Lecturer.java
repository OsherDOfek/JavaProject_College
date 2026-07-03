package OfekIdo4;

public abstract class Lecturer {
	private String name;		// lecturer name
	private String id;			// lecturer id
	private String degree_name; // degree name ("c.s" or "engineering")
	private int salary;			// lecturer salary
	private String dep_name;	// department name
	private Committee[] commi;	// lecturer's committees
	private int numOfCommittees;// number of committees
	
	
	// Lecturer constructor
	public Lecturer(String name, String id, String degree_name, int salary, String dep_name) throws InvalidSalaryException {
		if (salary < 0) {
			throw new InvalidSalaryException();
		}
		this.name = name;
		this.id = id;
		this.degree_name = degree_name;
		this.salary = salary;
		this.dep_name = dep_name;

		this.commi = new Committee[2];
		this.numOfCommittees = 0;
	}
	
	// Lecturer copy constructor
	public Lecturer(Lecturer lec) {
		this.name = lec.getName();
		this.id = lec.getId();
		this.degree_name = lec.getDegree_name();
		this.salary = lec.getSalary();
		this.dep_name = lec.getDep_name();
		
		this.commi = new Committee[lec.getCommi().length];
		for (int i = 0; i < lec.getNumOfCommittees(); i++) {
		    this.commi[i] = lec.getCommi()[i];
		}
		this.numOfCommittees = lec.getNumOfCommittees();
	}
	
	public abstract int getLevel();
	
	// Getters and Setters
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDegree_name() {
		return this.degree_name;
	}

	public void setDegree_name(String degree_name) {
		this.degree_name = degree_name;
	}

	public int getSalary() {
		return this.salary;
	}

	public void setSalary(int salary) throws InvalidSalaryException {
		if(salary < 0) {
			throw new InvalidSalaryException();
		}
		this.salary = salary;
	}

	public String getDep_name() {
		return this.dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	
	public Committee[] getCommi() {
		return this.commi;
	}
	
	public int getNumOfCommittees() {
		return numOfCommittees;
	}
	
	public boolean removeCommittee(String comName) {
	    for (int i = 0; i < this.numOfCommittees; i++) {
	        if (this.commi[i].getName().equals(comName)) {
	            for (int j = i; j < this.numOfCommittees - 1; j++) {
	                this.commi[j] = this.commi[j + 1];
	            }
	            this.commi[this.numOfCommittees - 1] = null;
	            this.numOfCommittees--;
	            return true;
	        }
	    }
	    return false;
	}
	
	public void addCommittee(Committee c) {
		
		if(this.numOfCommittees == this.commi.length) {
			// enlarge the size of the com array
			Committee[] temp = new Committee[this.commi.length*2];
			for(int i = 0; i < this.commi.length; i++) {
				temp[i] = this.commi[i];
			}
			this.commi = temp;
		}
		this.commi[this.numOfCommittees++] = c;
	}
	
	public String toStringCommittee() {
	    if (this.numOfCommittees == 0) {
	        return "Committees: None";
	    }
	    
	    String s = "Committees: ";
	    for(int i = 0; i < this.numOfCommittees; i++) {
	        s += this.commi[i].getName();
	        if (i < this.numOfCommittees - 1) {
	            s += ", ";
	        }
	    }
	    return s;
	}
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    Lecturer other = (Lecturer) o;
	    return this.name.equals(other.name) && this.id.equals(other.id);
	}
	
	// Lecturer toString func
	public String toString() {
		return "Lecturer --> name: " + this.name + ", id: " + this.id + ", degree: " + ", degree_name: " + this.degree_name
				+ ", salary: " + this.salary + ", dep_name: " + this.dep_name + ", " + toStringCommittee();
	}	
}
