package com.mytest.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Rajani
 */
public class Environments extends Properties{
	
	public Environments() {
		try {
			this.load(new FileInputStream("src/main/resources/property/environments.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
