package org.gmdev.event.type;

import static org.gmdev.event.type.Event.EventType.SEND;

import org.gmdev.event.message.Message;

public class SendEvent implements Event {
	
	private final EventType name = SEND;
	private final Message message;
	
	public SendEvent(Message message) {
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
