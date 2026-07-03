package OfekIdo4;

public class CommitteeAlreadyExistsException extends Exception{
	public CommitteeAlreadyExistsException() {
		super("Error: Committee already exists");
	}

}
