package org.gmdev.player.tasks;

import static org.gmdev.player.workers.AbstractWorker.*;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.gmdev.event.EventManager;
import org.gmdev.event.message.SendMessage;
import org.gmdev.event.type.SendEvent;
import org.gmdev.player.Player;
import org.gmdev.player.workers.WorkerBag;
import org.gmdev.serviceprocessor.ServiceRequest;

public class StartMessageQueueConsumerTask implements ServiceRequest {
	
	public static final String I_WON = "I_WON";
	public static final String OTHER_WON = "OTHER_WON";
	public static final String ERROR = "ERROR";
	public static final String STOP = "STOP";
	
	private final EventManager eventManager;
	private final WorkerBag workerBag;
	private final Consumer<String> exitCallback;
	private String playerWinnerName;
	
	public StartMessageQueueConsumerTask(
			EventManager eventManager, 
			WorkerBag workerBag,
			Consumer<String> exitCallback) {
		
		this.eventManager = eventManager;
		this.workerBag = workerBag;
		this.exitCallback = exitCallback;
	}

	@Override
	public void process() throws Exception {
		BlockingQueue<String> messageQueue = workerBag.getMessageQueue();
		while (true) {
			String message = messageQueue.take();
			if (message.equals(EXIT_QUEUE)) {
				setPlayerWinnerName(OTHER_WON);
				break;
			}
				
			if (message.equals(ERROR) || message.equals(STOP))
				break;
					
			var sendMessage = new SendMessage(message);
			eventManager.notifySubscribers(new SendEvent(sendMessage));
				
			if (message.equals(SEND_EXIT)) {
				setPlayerWinnerName(I_WON);
				break;
			} 
		}
		
		exitCallback.accept(playerWinnerName);
	}
	
	private void setPlayerWinnerName(String winner) {
		Player player = workerBag.getPlayer();
		playerWinnerName = winner == I_WON ? player.name : player.otherPlayerName;
	}
	
	

}
