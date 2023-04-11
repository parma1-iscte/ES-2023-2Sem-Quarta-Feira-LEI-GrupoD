package ReadFileRemote;

import java.util.*;
import java.net.*;
import java.io.*;
import com.google.gson.*;

public class ReadFileJsonRemote {

	private URL url;
	private List<JsonObject> list = new ArrayList<>();
	private Gson gson = new Gson();
	
	public ReadFileJsonRemote(URL url) {
		this.url = url;
	}
	
	private void readFromRemote() throws IOException {
		BufferedReader in = 
				new BufferedReader(new InputStreamReader(url.openStream()));
		
		JsonObject json;
		while((json = gson.fromJson(in.readLine(), JsonObject.class)) != null) {
			list.add(json);
		}
	}
	
	public List<JsonObject> getListJsonObject() throws IOException {
		readFromRemote();
		return list;
	}
	
}
