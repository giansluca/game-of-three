package org.gmdev.player.workers;

import static org.gmdev.event.message.Message.StartMode.*;
import static org.gmdev.utils.Logger.log;

import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;

import org.gmdev.comunication.SocketServerManager;
import org.gmdev.event.message.*;
import org.gmdev.event.message.Message.StartMode;
import org.gmdev.player.Player;

public class ListenWorker extends AbstractWorker {

	public static final int WINNER = 1;
	
	private BlockingQueue<String> messageQueue;
	private Player player;
	private SocketServerManager socketServerManager;

	public ListenWorker() {
		WorkerBag workerBag = getWorkerBag();
		messageQueue = workerBag.getMessageQueue();
		player = workerBag.getPlayer();
		socketServerManager = workerBag.getSocketServerManager();
	}
	
	@Override
	public void work(Message message) throws Exception  {
		StartMode mode = ((ListenMessage) message).getStartMode();
		
		if (socketServerManager.isPortBusy(LOCALHOST, player.listenPort))
			throw new IllegalStateException(
					String.format("%s already started, port in use", player.name));
		
		if (mode.equals(SEND_FIRST))
			if (socketServerManager.isPortBusy(LOCALHOST, player.sendFirstFlagPort))
				throw new IllegalStateException(
						"The other player is already running in 'START' mode");
		
		socketServerManager.startServer(messageCallback, player.listenPort); 
		log(String.format("%s ready to listen", player.name));
	}
	
	public Consumer<String> messageCallback = message -> {
		if (message.equals(EXIT)) {
			log(String.format("%s Received Exit signal", player.name));
			putMessageOnQueue(EXIT_QUEUE);
			return;
		}
			
		int number = Integer.parseInt(message);
		log(String.format("%s received number: %d", player.name, number));
			 
		number = getNumberToSendBack(number, player.name);
			
		if (number == WINNER) {
			log(String.format(
					"%s is the WINNER - sendig Exit signal to other player", player.name));
			
			putMessageOnQueue(SEND_EXIT);
		} else {
			log(String.format(
					"%s sending to other player number: %d", player.name, number));
			
			putMessageOnQueue(String.valueOf(number));
		}
	};
	
	private void putMessageOnQueue(String message) {
		try {
			messageQueue.put(message);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
	
	public int getNumberToSendBack(int number, String playerName) {
		int numberToAdd = getNumberToAdd(number);
		number += numberToAdd;
		log(String.format("%s add: %d", playerName, numberToAdd));
		
		number /= 3;
		log(String.format("%s result after division by three: %d", playerName, number));
		
		return number;
	}
	
	private int getNumberToAdd(int number) {
		if (number % 3 == 0)
			return 0;
		else if ((number + 1) % 3 == 0)
			return +1;
		else
			return -1;
	}
	

}
