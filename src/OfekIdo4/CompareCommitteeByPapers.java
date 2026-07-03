package OfekIdo4;
import java.util.Comparator;
public class CompareCommitteeByPapers implements Comparator<Committee>{
	public int compare(Committee com1, Committee com2) {
		int sub = com1.sum_of_papers() - com2.sum_of_papers();
		if(sub > 0)
			return 1;
		if(sub < 0)
			return -1;
		return 0;	
	}
}
