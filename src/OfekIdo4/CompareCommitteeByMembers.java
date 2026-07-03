package OfekIdo4;
import java.util.Comparator;

public class CompareCommitteeByMembers implements Comparator<Committee>{
	public int compare(Committee com1,Committee com2) {
		int sub = com1.getMembers().size() - com2.getMembers().size();
		if(sub > 0)
			return 1;
		if(sub < 0)
			return -1;
		return 0;
	}

}
