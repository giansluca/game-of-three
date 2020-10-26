package org.gmdev.event.type;

import org.gmdev.event.message.Message;

public interface Event {
	
	EventType getType();
	
	Message getMessage();
	
	public enum EventType {
		LISTEN,
		SEND,
		SENDFIRST;
	}
	
}
