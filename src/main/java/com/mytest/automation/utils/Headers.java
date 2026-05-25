package com.mytest.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author Rajani
 */
public class Headers extends Properties {
	
	public Headers() {
		try {
			this.load(new FileInputStream("src/main/resources/property/headers.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Headers(InputStream file) {
		try {
			this.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 
	public Map<String , String> getAsMap() {
		return this.entrySet().stream().collect(Collectors.toMap(e -> String.valueOf(e.getKey()), e -> String.valueOf(e.getValue())));
	}

}
