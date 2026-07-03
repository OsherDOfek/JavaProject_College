package OfekIdo4;
import java.util.ArrayList;
public abstract class Lecturer<T> {
	private String name;		// lecturer name
	private String id;			// lecturer id
	private String degree_name; // degree name ("c.s" or "engineering")
	private int salary;			// lecturer salary
	private String dep_name;	// department name
	private ArrayList<Committee> commi;	// lecturer's committees
	
	
	// Lecturer constructor
	public Lecturer(String name, String id, String degree_name, int salary, String dep_name) throws InvalidSalaryException {
		if (salary < 0) {
			throw new InvalidSalaryException();
		}
		this.name = name;
		this.id = id;
		this.degree_name = degree_name;
		setSalary(salary);
		this.dep_name = dep_name;
		this.commi = new ArrayList<Committee>();
	}
	
	// Lecturer copy constructor
	public Lecturer(Lecturer lec) {
		this.name = lec.getName();
		this.id = lec.getId();
		this.degree_name = lec.getDegree_name();
		this.salary = lec.getSalary();
		this.dep_name = lec.getDep_name();
		this.commi = (ArrayList<Committee>) lec.commi.clone();
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
	
	public void removeCommittee(String comName) throws CommitteeDoesntExistsException {
	    for (int i = 0; i < this.commi.size(); i++) {
	        if (this.commi.get(i).getName().equals(comName)) {
	            this.commi.remove(i);
	            return;
	        }
	    }
	    throw new CommitteeDoesntExistsException();
	}
	
	public void addCommittee(Committee c) throws CommitteeAlreadyExistsException {
		
		if(this.commi.contains(c)) {
			throw new CommitteeAlreadyExistsException();
		}
		this.commi.add(c);
	}
	
	public String toStringCommittee() {
	    if (this.commi.size() == 0) {
	        return "Committees: None";
	    }
	    
	    String s = "Committees: ";
	    for(int i = 0; i < this.commi.size(); i++) {
	        s += this.commi.get(i).getName();
	        if (i < this.commi.size() - 1) {
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
