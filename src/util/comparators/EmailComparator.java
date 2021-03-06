package util.comparators;

import java.util.Comparator;

public class EmailComparator<T extends Discente> implements Comparator<T>{
	
	@Override
	public int compare(T o1, T o2) {
		
		if(o1.getEmail().compareTo(o2.getEmail()) == 0) {
			o1.getMatricula().compareTo(o2.getMatricula());
		}
		
		return o1.getEmail().compareTo(o2.getEmail());
	}
}