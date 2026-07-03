package OfekIdo4;

public class AlreadyInDepartmentException extends Exception{
	public AlreadyInDepartmentException() {
		super("Error: lecturer is already in this department");
	}

}
