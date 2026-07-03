package OfekIdo4;

public class InvalidDegreeException extends Exception{
	public InvalidDegreeException() {
		super("Error: lecturer must be at least a Doctor to head a committee");
	}

}
