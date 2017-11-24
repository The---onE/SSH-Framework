package xmx.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class Result {
	
	public abstract Result setStatus(int status);
	
	public abstract Result setPrompt(String prompt);
	
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
	
	public String toJson(String dateFormat) {
		Gson gson = new GsonBuilder().setDateFormat(dateFormat).create(); 
		return gson.toJson(this);
	}
}
