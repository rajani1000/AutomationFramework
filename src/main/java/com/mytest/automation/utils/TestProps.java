package com.mytest.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Rajani
 */
public class TestProps extends Properties {
	private static TestProps instance;
	
	public static String get(String property) {
		if(instance == null)
			instance = new TestProps();
		return instance.getProperty(property);
	}
	
	private TestProps() {
		try {
			this.load(new FileInputStream("src/main/resources/property/test.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
