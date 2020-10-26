package org.gmdev.event.subscriber;

import org.gmdev.event.type.Event;

public interface EventSubscriber {
	
	void notify(Event event) throws Exception;
	
	public enum Subscriber {
		LISTEN_SUBSCRIBER,
		SEND_SUBSCRIBER,
		SENDFIRST_SUBSCRIBER;
	}
}
