package org.gmdev.unit.player.workers;

import static org.gmdev.utils.PropertiesLoader.player_1;
import static org.mockito.Mockito.*;

import java.util.concurrent.*;

import org.gmdev.comunication.*;
import org.gmdev.event.message.*;
import org.gmdev.player.Player;
import org.gmdev.player.workers.*;
import org.gmdev.utils.PropertiesLoader;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SendWorkerTest {
	
	SendWorker underTest;
	
	@Mock
	Message message;
	
	@Mock
	WorkerBag workerBag;
	
	BlockingQueue<String> messageQueue;
	Player player;
	SocketServerManager socketServerManager;
	SocketClient socketClient;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		PropertiesLoader.loadProperties(player_1);
		
		messageQueue = spy(new LinkedBlockingDeque<>(5));
		player = spy(Player.getPlayer());
		socketServerManager = spy(new SocketServerManager());
		socketClient = spy(new SocketClient());
		
		workerBag = new WorkerBag(
				messageQueue, player, socketServerManager, socketClient);
		
		AbstractWorker.setWorkerBag(workerBag);
		underTest = new SendWorker();
	}
	
	@Test
	void itShouldSendTheMessage() throws Exception {
		// Given
		String number = "99";
		
		SendMessage sendMessage = new SendMessage(number);
		
		doNothing().when(socketClient).startConnection(anyString(), anyInt());
		doNothing().when(socketClient).sendMessage(anyString());
		doNothing().when(socketClient).closeConnection();
		
		// When
		// Then
		underTest.work(sendMessage);
	}
	
}
