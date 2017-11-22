import java.util.Vector;
import java.lang.Thread;

public class MyTread extends Thread {
	Vector Vect;
	UsualMatrix Matrix;
	Vector Result;
	int beginIndex;
	int endIndex;
	
	
	public MyTread(Vector vect, UsualMatrix matr, Vector result, int BeginIndex, int EndIndex) {
							Vect = vect;
							Matrix = matr;
							Result = result;
							beginIndex = BeginIndex;
							endIndex = EndIndex;
	}
	
	
	public void run() {
		for(int index = beginIndex; (index < endIndex) && (index < Result.size()); index++) {
			for(int k = 0; k < Vect.size(); k++) {
				Result.set(index, ((int)Result.get(index) + ((int)Vect.get(k)*Matrix.getElement(k, index))));
			}
		}
	}
}