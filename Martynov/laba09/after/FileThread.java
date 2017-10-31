
public class FileThread extends Thread{
	public String path = "";
	public UniqueWords uw;
	public FileThread(UniqueWords uw, String path) {
		this.path = path;
		this.uw = uw;
	}
	public void run(){
		try{
			uw.readFile(path);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	public void waitAll(){
		try {
			join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
