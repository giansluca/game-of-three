package org.gmdev;

import org.gmdev.utils.Factory;

import static org.gmdev.utils.PropertiesLoader.loadPlayerPropertiesFile;

import io.github.giansluca.jargs.Jargs;

public class Main { 
	
	public static void main(String[] args) throws Exception {
		String schema = "playername*, sendfirst%, initnumber#";
		Jargs arguments = new Jargs(schema, args);
		
		if (!arguments.has("playername"))
			throw new IllegalArgumentException("No player name supplied");
		
		loadPlayerPropertiesFile(arguments.getString("playername"));
		
		Factory.getFactory()
			.createPlayerManager().start(arguments);
	}
	
}
