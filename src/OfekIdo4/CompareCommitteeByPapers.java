package OfekIdo4;
import java.util.Comparator;

// compares committees by the total number of papers of their members
public class CompareCommitteeByPapers implements Comparator<Committee<?>> {
	public int compare(Committee<?> com1, Committee<?> com2) {
		return Integer.compare(com1.sum_of_papers(), com2.sum_of_papers());
	}
}
