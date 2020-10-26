package org.gmdev.player.workers;

import org.gmdev.event.message.*;
import org.gmdev.player.Player;

import static org.gmdev.utils.Logger.log;

import org.gmdev.comunication.*;

public class SendWorker extends AbstractWorker {

	private Player player;
	private SocketClient socketClient;
	
	public SendWorker() {
		WorkerBag workerBag = getWorkerBag();
		player = workerBag.getPlayer();
		socketClient = workerBag.getSocketClient();
	}
	
	@Override
	public void work(Message message) throws Exception {
		SendMessage sendMessage = (SendMessage) message;	
		String number = sendMessage.getNumber();
		
		Thread.sleep(1000);
		socketClient.startConnection(player.otherPlayerAddress, player.otherPlayerPort);
		socketClient.sendMessage(number);
		socketClient.closeConnection();
		
		if (number.equals(SEND_EXIT))
			log(String.format("%s Exit", player.name));
		
	}

}
