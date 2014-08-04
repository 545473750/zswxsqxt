package com.opendata.rs.action.Commom;

import java.io.FileInputStream;
import java.util.Properties;

public class ReadPropertyFile {
	private static final String KEY = "res.server.url";
	private static final String FILE_NAME = "application.properties";
	public static String readFile(String path) throws Exception {
		Properties file = new Properties();
		FileInputStream stream = new FileInputStream(path+FILE_NAME);
		file.load(stream);
		return file.getProperty(KEY);
	}
}
