package OfekIdo4;

public class LecturerAlreadyExistsException extends Exception{
	public LecturerAlreadyExistsException() {
		super("Error: lecturer with this name already exists");
	}

}
