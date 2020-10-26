package org.gmdev.player.workers;

import java.util.concurrent.*;

import org.gmdev.comunication.*;
import org.gmdev.player.Player;

public class WorkerBag {
	
	private final BlockingQueue<String> messageQueue;
	private final Player player;
	private final SocketServerManager socketServerManager;
	private final SocketClient socketClient;
	
	public WorkerBag() {
		messageQueue = new LinkedBlockingDeque<>(5);
		player = Player.getPlayer();
		socketServerManager = new SocketServerManager();
		socketClient = new SocketClient();
	}
	
	public WorkerBag(
			BlockingQueue<String> messageQueue, 
			Player player, 
			SocketServerManager socketServerManager,
			SocketClient socketClient) {
		
		this.messageQueue = messageQueue;
		this.player = player;
		this.socketServerManager = socketServerManager;
		this.socketClient = socketClient;
	}

	public BlockingQueue<String> getMessageQueue() {
		return messageQueue;
	}

	public Player getPlayer() {
		return player;
	}

	public SocketServerManager getSocketServerManager() {
		return socketServerManager;
	}

	public SocketClient getSocketClient() {
		return socketClient;
	}
	
	
}
