
public class MyThread extends Thread{
	
	private UsualMatrix um1, um2, res;
	private int begin, end;

	
	public MyThread(UsualMatrix um1, UsualMatrix um2, UsualMatrix res, int begin, int end) {
		this.um1 = um1;
		this.um2 = um2;
		this.res = res;
		this.begin = begin;
		this.end = end;
	}
	
	public void run(){
		
		for(int i = begin; i < end; i++){
			int row = i / um2.getColumns();
        	int column = i % um2.getColumns();
        	for (int j = 0; j < um2.getRows(); j++){
        		res.setValue(row, column, res.getValue(row, column) + um1.getValue(row, j) * um2.getValue(j, column)); 
        	}    
		}        
		
	}
	
	public void waitProduct(){
		
		try {
			join();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	

}
