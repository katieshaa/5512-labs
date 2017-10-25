import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;

public class UniqueWords{
	private HashMap<String, Integer> hm;
	public UniqueWords() {
		hm = new HashMap<String, Integer>();
	}
	public synchronized void add(String w){
		if(hm.containsKey(w))
			hm.put(w, hm.get(w) + 1);
		else
			hm.put(w, 1);
	}
	
	public void readFiles(String ... strings) throws Exception{
		FileThread[] threads = new FileThread[strings.length];
		int i = 0;
		for(String s: strings){
			threads[i] = new FileThread(this, s);
			threads[i].start();
			i++;
		}
		for(int j = 0; j < i; j++){
			threads[j].waitAll();
		}
	}
	public void readFile(String path) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(path));
		String word = "", line = "";
		System.out.println("Start here...");
		while((line = in.readLine()) != null){
			System.out.println(line);
			for(int i = 0; i < line.length();i++){
				while(line.charAt(i) != ' '){
					word += line.charAt(i);
					if(line.length() - 1 == i)
						break;
					else
						i++;
				}
				add(word);
				word = "";
			}
			
		}
	//	System.out.println("Finish here..." + '\n');
		in.close();
	}
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < hm.size(); i++){
			sb.append(hm.keySet().toArray()[i] + " " + hm.values().toArray()[i] + '\n');
		}
		return sb.toString();
	}
	public static void main(String[] args){
		UniqueWords uw = new UniqueWords();
		try {
			
			long t1 = System.currentTimeMillis();
		//	for(int i = 0; i < args.length; i++){
		//		uw.readFile(args[i]);
		//	}
			//uw.readFile("C:\\Users\\Игорь\\Desktop\\secmessage09.txt");
			//uw.readFile("C:\\Users\\Игорь\\Desktop\\message09.txt");
			//uw.readFile("C:\\Users\\Игорь\\Desktop\\message.txt");

			long t2 = System.currentTimeMillis();
			
			//uw.readFiles("C:\\Users\\Игорь\\Desktop\\message.txt", "C:\\Users\\Игорь\\Desktop\\message09.txt", "C:\\Users\\Игорь\\Desktop\\secmessage09.txt");
			
			String[] str = new String[args.length];
			for(int i = 0; i < args.length; i++){
				str[i] = args[i];
			}
			long t3 = System.currentTimeMillis();
			uw.readFiles(str);
			
			long t4 = System.currentTimeMillis();
			System.out.println(uw);
			System.out.println("Without P = " + (t2 - t1));
			System.out.println("With P = " + (t4 - t3));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}
