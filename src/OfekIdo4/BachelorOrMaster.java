package OfekIdo4;

// "Regular" degree - covers both Bachelor and Master (same homogeneity group, part 1)
public class BachelorOrMaster extends Lecturer {
	private static final long serialVersionUID = 1L;

	public BachelorOrMaster(String name, String id, String degree_name, int salary, String dep_name) throws InvalidSalaryException {
		super(name, id, degree_name, salary, dep_name);
	}

	@Override
	public int getLevel() {
		return 1;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	public String toString() {
		return super.toString();
	}
}
