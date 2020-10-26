package org.gmdev.player.workers;

import java.util.concurrent.BlockingQueue;

import static org.gmdev.utils.Logger.log;

import org.gmdev.comunication.SocketServerManager;
import org.gmdev.event.message.*;
import org.gmdev.event.message.Message.InitNumberMode;
import org.gmdev.player.Player;

public class SendFirstWorker extends AbstractWorker {

	private BlockingQueue<String> messageQueue;
	private Player player;
	private SocketServerManager socketServerManager;
	
	public SendFirstWorker() {
		WorkerBag workerBag = getWorkerBag();
		messageQueue = workerBag.getMessageQueue();
		player = workerBag.getPlayer();
		socketServerManager = workerBag.getSocketServerManager();
	}
	
	@Override
	public void work(Message message) throws Exception   {
		SendFirstMessage sendFirstMessage = (SendFirstMessage) message;
		String initNumber = sendFirstMessage.getInitNumber();
		InitNumberMode initNumberMode = sendFirstMessage.getInitNumberMode();
		
		socketServerManager.openSendFirstFlagPort(player.sendFirstFlagPort);
			
		while (!socketServerManager.isOtherPlayerUp(player.otherPlayerAddress, player.otherPlayerPort))
			wait(String.format(
					"%s is waiting ... [ %s ] is not responding", player.name, player.otherPlayerName), 
					2000);
			
		log(String.format(
				"%s starts with %s number: %s", player.name, initNumberMode, initNumber));
		
		messageQueue.put(initNumber);
	}
	
	public void wait(String message, int interval) throws InterruptedException {
		log(message);
		Thread.sleep(interval);
	}
	
	
}
