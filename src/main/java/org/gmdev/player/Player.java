package org.gmdev.player;

import java.util.Properties;

import org.gmdev.utils.PropertiesLoader;

public class Player {
	
	public final String name;
	public final String otherPlayerName;
	public final int listenPort;
	public final int sendFirstFlagPort;
	public final String otherPlayerAddress;
	public final int otherPlayerPort;
	
	
	private Player(
			String name, 
			String otherPlayerName, 
			int listenPort,
			int sendFirstFlagPort,
			String otherPlayerAddress, 
			int otherPlayerPort) {
		
		this.name = name;
		this.otherPlayerName = otherPlayerName;
		this.listenPort = listenPort;
		this.sendFirstFlagPort = sendFirstFlagPort;
		this.otherPlayerAddress = otherPlayerAddress;
		this.otherPlayerPort = otherPlayerPort;
	}
	
	public static Player getPlayer() {
		Properties prop = PropertiesLoader.getProperties();
		
		return new Player(
			prop.getProperty("name"),
			prop.getProperty("otherPlayerName"),
			Integer.valueOf(prop.getProperty("listenPort")).intValue(),
			Integer.valueOf(prop.getProperty("sendFirstFlagPort")).intValue(),
			prop.getProperty("otherPlayerAddress"),
			Integer.valueOf(prop.getProperty("otherPlayerPort")).intValue()
		);
	}
	
}
