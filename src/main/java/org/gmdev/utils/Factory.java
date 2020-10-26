package org.gmdev.utils;

import org.gmdev.event.subscriber.*;
import org.gmdev.event.subscriber.EventSubscriber.Subscriber;
import org.gmdev.player.PlayerManager;
import org.gmdev.player.workers.*;

public class Factory {
	
	private static Factory factory;
	
	public static Factory getFactory() {
		if (factory == null)
			factory = new Factory();
		
		return factory;
	}
	
	private Factory() {}
	
	public PlayerManager createPlayerManager() {
		return new PlayerManager();
	}
	
	public EventSubscriber createEventSubscriber(Subscriber subscriber) {
		AbstractWorker worker;
		switch (subscriber) {
			case SEND_SUBSCRIBER:
				worker = new SendWorker();
				return new SendEventSubscriber(worker);
			case SENDFIRST_SUBSCRIBER:
				worker = new SendFirstWorker();
				return new SendFirstEventSubscriber(worker);
			case LISTEN_SUBSCRIBER: 
				worker = new ListenWorker();
				return new ListenEventSubscriber(worker);
			default:
				throw new IllegalStateException("TILT should never get here.");
		}
	}
	
}
