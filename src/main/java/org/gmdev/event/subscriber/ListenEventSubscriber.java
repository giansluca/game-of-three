package org.gmdev.event.subscriber;

import org.gmdev.event.type.Event;
import org.gmdev.player.workers.AbstractWorker;

public class ListenEventSubscriber implements EventSubscriber {
	
	private AbstractWorker listenWorker;
	
	public ListenEventSubscriber(AbstractWorker listenWorker) {
		this.listenWorker = listenWorker;
	}
	
	@Override
	public void notify(Event event) throws Exception {
		listenWorker.work(event.getMessage());
	}

}
