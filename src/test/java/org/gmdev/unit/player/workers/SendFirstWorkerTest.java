package org.gmdev.unit.player.workers;

import static org.assertj.core.api.Assertions.*;
import static org.gmdev.utils.PropertiesLoader.player_1;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.gmdev.comunication.*;
import org.gmdev.event.message.Message;
import org.gmdev.event.message.SendFirstMessage;
import org.gmdev.player.Player;
import org.gmdev.player.workers.*;
import org.gmdev.utils.PropertiesLoader;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SendFirstWorkerTest {
	
	SendFirstWorker underTest;
	
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
		underTest = spy(new SendFirstWorker());
	}
	
	@Test
	void itShouldCallWaitForPlayer() throws Exception {
		// Given
		int number = 99;
		
		// ... a valid message
		SendFirstMessage sendFirstMessage = new SendFirstMessage(Optional.of(number));
		
		given(socketServerManager.isPortBusy(anyString(), anyInt())).willReturn(false);
		doNothing().when(socketServerManager).openSendFirstFlagPort(anyInt());
		
		given(socketServerManager.isOtherPlayerUp(anyString(), anyInt())).willReturn(false, true);
		
		doNothing().when(underTest).wait(anyString(), anyInt());
		
		// When
		underTest.work(sendFirstMessage);
		
		// Then
		verify(underTest, atLeastOnce()).wait(anyString(), anyInt());
	}
	
	@Test
	void itShouldPutTheStartNumberiOnQueue() throws Exception {
		// Given
		int number = 99;
		
		// ... a valid message
		SendFirstMessage sendFirstMessage = new SendFirstMessage(Optional.of(number));
		
		given(socketServerManager.isPortBusy(anyString(), anyInt())).willReturn(false);
		doNothing().when(socketServerManager).openSendFirstFlagPort(anyInt());
		given(socketServerManager.isOtherPlayerUp(anyString(), anyInt())).willReturn(true);
		
		// When
		underTest.work(sendFirstMessage);
		
		// Then
		assertThat(messageQueue.take()).isEqualTo("99");
	}
}
