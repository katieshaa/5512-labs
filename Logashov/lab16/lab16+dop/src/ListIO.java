import java.io.*;
import java.util.ArrayList;
public class ListIO {
	private String filename;
	public ListIO(String filename) {
		this.filename = filename;
	}
	public synchronized ArrayList<ArrayList<String>> read() {
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		String line = null;
		int counterLine = 0;
		int size = 0;
		try(
			BufferedReader reader = new BufferedReader(new FileReader(this.filename));
		) {
			while((line = reader.readLine()) != null) {
				counterLine++;
				size = list.size();
				if(line.startsWith("*")) {
					list.add(new ArrayList<>());
					list.get(size).add(line.substring(1));
				}
				else if(line.startsWith("    *") && size != 0)
					list.get(size - 1).add(line.substring(5));
				else 
					System.out.println("Error in " + counterLine + " string.");
			}
		}
		catch(IOException exception) {
				exception.printStackTrace();
		}
		return list;
	}
}
