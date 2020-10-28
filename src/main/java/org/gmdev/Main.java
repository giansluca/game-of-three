package org.gmdev;

import org.gmdev.utils.Factory;

import static org.gmdev.utils.PropertiesLoader.loadPlayerPropertiesFile;

import io.github.giansluca.jargs.Jargs;
import io.github.giansluca.jargs.exception.JargsException;

public class Main { 
	
	public static void main(String[] args) {
		Jargs arguments = parseArguments(args);
		
		if (!arguments.has("playername"))
			throw new IllegalArgumentException("No player name supplied");
		
		loadPlayerPropertiesFile(arguments.getString("playername"));
		
		Factory.getFactory()
			.createPlayerManager().start(arguments);
	}
	
	private static Jargs parseArguments(String[] args) {
		String schema = "playername*, sendfirst%, initnumber#";
		try {
			return new Jargs(schema, args);
		} catch (JargsException e) {
			throw new IllegalStateException(e);
		}
	}
	
	
}
