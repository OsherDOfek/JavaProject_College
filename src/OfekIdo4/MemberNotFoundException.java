package OfekIdo4;

public class MemberNotFoundException extends Exception{
	public MemberNotFoundException() {
		super("Error: lecturer is not a member of this committee");
	}

}
