package OfekIdo4;

public class Professor extends Doctor {
	private static final long serialVersionUID = 1L;

	private String granting_body; // the body/institution that granted the professorship

	public Professor(String name, String id, String degree_name, int salary, String dep_name, String granting_body) throws InvalidSalaryException {
		super(name, id, degree_name, salary, dep_name);
		this.granting_body = granting_body;
	}

	public String getGranting_body() {
		return granting_body;
	}

	public void setGranting_body(String granting_body) {
		this.granting_body = granting_body;
	}

	@Override
	public int getLevel() {
		return 3;
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;
		Professor other = (Professor) o;
		return this.granting_body.equals(other.granting_body);
	}

	public String toString() {
		return super.toString() + ", granting body: " + this.granting_body;
	}
}
