package org.gmdev.event;

import java.util.*;

import org.gmdev.event.subscriber.EventSubscriber;
import org.gmdev.event.type.Event;
import org.gmdev.event.type.Event.EventType;

public class EventManager {
	
	private Map<EventType, List<EventSubscriber>> subscribers;
	
	public EventManager() {
		subscribers = new HashMap<>();
		for (EventType eventTypeName : EventType.values())
			subscribers.put(eventTypeName, new ArrayList<>());
	}
	
	public void subscribe(EventSubscriber subscriber, EventType...eventTypes) {
		for (var eventType : eventTypes)
			subscribers.get(eventType).add(subscriber);
	}
	
	public void unsubscribe(EventSubscriber subscriber, EventType eventTypeName) {
		subscribers.get(eventTypeName).remove(subscriber);
	}
	
	public void notifySubscribers(Event event) throws Exception {
		var subcribersByType = subscribers.get(event.getType());
		for (EventSubscriber subcriber : subcribersByType)
			subcriber.notify(event);
	}

	public Map<EventType, List<EventSubscriber>> getSubscribers() {
		return subscribers;
	}
	
}
