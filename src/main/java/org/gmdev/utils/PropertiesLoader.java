package org.gmdev.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesLoader {
	
	public static final String P1 = "P1";
	public static final String P2 = "P2";
	public static final String player_1 = "player_1.properties";
	public static final String player_2 = "player_2.properties";
	private static Properties prop;
	
	public static void loadPlayerPropertiesFile(String playerName) {
		if (playerName.equals(P1))
			loadProperties(player_1);	
		else if (playerName.equals(P2))
			loadProperties(player_2);
		else 
			throw new IllegalArgumentException(
					String.format("Wrong player name supplied: [ %s ]", playerName));
	}
	
	synchronized public static void loadProperties(String filePath) {	
		try {
			new PropertiesLoader(filePath);
		} catch (IOException e) {
			throw new IllegalStateException("Error loading property file.");
		}
	}
	
	public static void unloadProperties() {
		prop = null;
	}
	
	public static Properties getProperties() {
		if (prop != null)
			return prop;
		else
			throw new IllegalStateException("Property file not loaded.");
	}
	
	
	
	private PropertiesLoader(String filePath) throws IOException {
		load(filePath);
	}
	
	private void load(String file) throws IOException {
		prop = new Properties();
		InputStream im = findFile(file);
		prop.load(findFile(file));
		im.close();
	}
	
	private InputStream findFile(String file) throws FileNotFoundException {
		InputStream im = findInClasspath(file);
		if (im == null) 
			throw new FileNotFoundException(String.format("File %s not found.", file));
		
		return im;
	}
    
	private InputStream findInClasspath(String file) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
	}
	
}
