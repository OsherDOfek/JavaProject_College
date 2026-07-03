package OfekIdo4;

public class BachelorOrMaster extends Lecturer{
	
	public BachelorOrMaster(String name, String id, String degree_name, int salary, String dep_name) throws InvalidSalaryException {
		super(name,id,degree_name,salary,dep_name);
	}
	
	@Override
	public boolean equals(Object o) {
	    return super.equals(o);
	}
	public int getLevel() {
		return 1;
	}
	public String toString() {
		return super.toString();
	}
}
