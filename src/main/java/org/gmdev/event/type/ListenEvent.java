package org.gmdev.event.type;

import static org.gmdev.event.type.Event.EventType.LISTEN;

import org.gmdev.event.message.Message;

public class ListenEvent implements Event {
	
	private final EventType name = LISTEN;
	private final Message message;
	
	public ListenEvent(Message message) {
		this.message = message;
	}
	
	@Override
	public EventType getType() {
		return name;
	}
	
	@Override
	public Message getMessage() {
		return message;
	}
	
}
