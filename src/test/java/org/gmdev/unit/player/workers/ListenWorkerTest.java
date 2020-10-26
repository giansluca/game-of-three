package org.gmdev.unit.player.workers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import static org.gmdev.utils.PropertiesLoader.player_1;
import static org.gmdev.player.workers.AbstractWorker.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import org.gmdev.comunication.*;
import org.gmdev.event.message.ListenMessage;
import org.gmdev.event.message.Message.StartMode;
import org.gmdev.player.Player;
import org.gmdev.player.workers.*;
import org.gmdev.utils.PropertiesLoader;

class ListenWorkerTest {
	
	ListenWorker underTest;
	
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
		underTest = new ListenWorker();
	}

	@ParameterizedTest
    @CsvSource({
            "15, 5",
            "16, 5",
            "17, 6"
    })
	void itShouldReturnTheCorrectNumberToSendBack(int number, int expected) {
		// Given a number
		// When
		int isValid = underTest.getNumberToSendBack(number, "Player Test");
		
		// Then
		assertThat(isValid).isEqualTo(expected);
	}
	
	@Test
	void itShouldThrowInfListenPortIsNotFree() throws Exception {
		// Given
		String playerName = player.name;
		
		// ... a valid message
		ListenMessage listenMessage = new ListenMessage(StartMode.ONLY_LISTEN);
		
		given(socketServerManager.isPortBusy(anyString(), anyInt())).willReturn(true);
		
		// When
		// Then
		assertThatThrownBy(() -> underTest.work(listenMessage))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining(playerName + " Already started, port in use");
	}
	
	@Test
	void itShouldThrowInfFlagPortIsNotFree() throws Exception {
		// Given
		// ... a valid message
		ListenMessage listenMessage = new ListenMessage(StartMode.SEND_FIRST);
			
		given(socketServerManager.isPortBusy(anyString(), anyInt())).willReturn(false, true);
			
		// When
		// Then
		assertThatThrownBy(() -> underTest.work(listenMessage))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("The other player is already running in 'START' mode");
		}
	
	@Test
	void itShouldStartTheServerWhitNoErrors() throws Exception {
		// Given
		// ... a valid message
		ListenMessage listenMessage = new ListenMessage(StartMode.ONLY_LISTEN);
		
		doNothing().when(socketServerManager).startServer(any(), anyInt());
		
		// When
		underTest.work(listenMessage);
		
		// Then
		verify(socketServerManager, times(1)).startServer(any(), anyInt());
	}
	
	@Test
	void itShouldPutExitQueueOnQueue() throws Exception {
		// Given
		String number = EXIT;
		String expected = EXIT_QUEUE;
		
		doNothing().when(socketServerManager).stop();
		
		// When
		underTest.messageCallback.accept(number);
		
		// Then
		assertThat(messageQueue.take()).isEqualTo(String.valueOf(expected));
	}
	
	@Test
	void itShouldPutSendExitMessageOnQue() throws Exception {
		// Given
		String number = "3";
		String expected = SEND_EXIT;
						
		// When
		underTest.messageCallback.accept(number);
		
		// Then
		assertThat(messageQueue.take()).isEqualTo(String.valueOf(expected));
	}
	
	@Test
	void itShoulPutReceivedNumberOnQueue() throws Exception {
		// Given
		String number = "99";
		String expected = "33";
		
		// When
		underTest.messageCallback.accept(number);
		
		// Then
		assertThat(messageQueue.take()).isEqualTo(String.valueOf(expected));
	}
	
	
}
