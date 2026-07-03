package OfekIdo4;
import java.util.Comparator;

// compares committees by their number of members
public class CompareCommitteeByMembers implements Comparator<Committee<?>> {
	public int compare(Committee<?> com1, Committee<?> com2) {
		return Integer.compare(com1.getMembers().size(), com2.getMembers().size());
	}
}
