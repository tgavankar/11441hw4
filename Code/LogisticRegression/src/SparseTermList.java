import java.io.Serializable;
import java.util.Arrays;

/**
 * Class to represent data vector, allows for easy implementation changes.
 */
public class SparseTermList implements Serializable {
	private static final long serialVersionUID = 8347221082845553086L;
	double[] list;
	int length;
	
	public SparseTermList(int size) {
		list = new double[size];
		length = size;
	}
	
	public double get(int i) {
		return list[i];
	}
	
	public void put(int i, double s) {
		list[i] = s;
	}
	
	public void normalize() {
		double norm = 0.0;
		// Don't use/change [0]
		for(int i=1; i<length; i++) {
			norm += list[i] * list[i];
		}
		norm = Math.sqrt(norm);
		
		for(int i=1; i<length; i++) {
			list[i] /= norm;
		}
	}
	
	public SparseTermList deepCopy() {
		SparseTermList out = new SparseTermList(length);
		for(int i=0; i<length; i++) {
			if(get(i) != 0.0) {
				out.put(i, get(i));
			}
		}
		return out;
	}
	
	@Override
	public String toString() {
		return Arrays.toString(list);
	}
}
