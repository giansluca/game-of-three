package org.gmdev.event.subscriber;

import org.gmdev.event.type.Event;
import org.gmdev.player.workers.AbstractWorker;

public class SendFirstEventSubscriber implements EventSubscriber {
	
	private AbstractWorker sendFirstWorker;
	
	public SendFirstEventSubscriber(AbstractWorker sendFirstWorker) {
		this.sendFirstWorker = sendFirstWorker;
	}

	@Override
	public void notify(Event event) throws Exception {
		sendFirstWorker.work(event.getMessage());
		
	}
	
	
}
