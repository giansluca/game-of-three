package org.gmdev.event.subscriber;

import org.gmdev.event.type.Event;
import org.gmdev.player.workers.AbstractWorker;

public class SendEventSubscriber implements EventSubscriber {
	
	private AbstractWorker sendWorker;
	
	public SendEventSubscriber(AbstractWorker sendWorker) {
		this.sendWorker = sendWorker;
	}
	
	@Override
	public void notify(Event event) throws Exception {
		sendWorker.work(event.getMessage());
	}
	

}
