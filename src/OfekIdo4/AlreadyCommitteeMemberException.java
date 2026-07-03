package OfekIdo4;

public class AlreadyCommitteeMemberException extends Exception{
	public AlreadyCommitteeMemberException() {
		super("Error: lecturer is already the head or a member of this committee");
	}

}
