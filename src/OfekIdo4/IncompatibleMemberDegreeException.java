package OfekIdo4;

// Thrown when a lecturer's degree level doesn't match the committee's level (homogeneity - part 1)
public class IncompatibleMemberDegreeException extends Exception {
	private static final long serialVersionUID = 1L;

	public IncompatibleMemberDegreeException() {
		super("Error: lecturer's degree doesn't match the committee's degree level");
	}
}
