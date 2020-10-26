package org.gmdev.event.type;

import static org.gmdev.event.type.Event.EventType.SENDFIRST;

import org.gmdev.event.message.Message;

public class SendFirstEvent implements Event {
	
	private final EventType name = SENDFIRST;
	private final Message message;
	
	public SendFirstEvent(Message message) {
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
