package org.gmdev.event.message;

public class ListenMessage implements Message {

	private StartMode startMode;
		
	public ListenMessage(StartMode startMode) {
		this.startMode = startMode;		
	}
	
	public StartMode getStartMode() {
		return startMode;
	}
	
}
