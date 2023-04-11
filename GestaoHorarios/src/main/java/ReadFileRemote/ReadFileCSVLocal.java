package ReadFileRemote;

import java.util.*;
import java.io.*;

public class ReadFileCSVLocal {
	
	private String path;
	private List<String> list = new ArrayList<>();
	
	public ReadFileCSVLocal(String path) {
		this.path = path;
	}
	
	private void readFromLocal() throws IOException {
		File file = new File(path);
		BufferedReader in = 
				new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line;
		while((line = in.readLine()) != null)
			list.add(line);
	}

	public List<String> getListCSV() throws IOException{
		readFromLocal();
		return list;
	}
	
}
