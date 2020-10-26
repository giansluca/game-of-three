package org.gmdev.player.workers;

import org.gmdev.event.message.Message;

public abstract class AbstractWorker {
	
	public static final String EXIT = "EXIT";
	public static final String SEND_EXIT = EXIT;
	public static final String EXIT_QUEUE = "EXIT_QUEUE";
	public static final String LOCALHOST = "127.0.0.1";
	
	private static WorkerBag workerBag;
	
	public static WorkerBag createWorkerBag() {
		workerBag = new WorkerBag();
		return workerBag;
	}
	
	public static void setWorkerBag(WorkerBag newWorkerBag) {
		workerBag = newWorkerBag;
	}
	
	public WorkerBag getWorkerBag() {
		if (workerBag == null)
			throw new IllegalStateException("TILT : WokerBag cannot be null!");
		
		return workerBag;
	}
	
	public abstract void work(Message message) throws Exception;
		
}
