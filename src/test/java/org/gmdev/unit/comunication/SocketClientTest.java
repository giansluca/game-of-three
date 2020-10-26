package org.gmdev.unit.comunication;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import java.util.function.Consumer;

import org.gmdev.comunication.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SocketClientTest {
	
	SocketClient underTest;
	
	SocketServerManager socketServerManager;
	
	@Mock
	Consumer<String> messageCallback;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		socketServerManager = spy(new SocketServerManager());
		underTest = new SocketClient();
	}
	
	@AfterEach
	void tearDown(TestInfo testInfo) throws Exception {
		socketServerManager.stopServer();
		socketServerManager.closeSendFirstFlagPort();
	}
	
	@Test
	void itShoulSendAMessage() throws Exception {
		// Given
		int port = 8888;
		String message = "TEST";
		socketServerManager.startServer(messageCallback, port);
		Thread.sleep(100);
		
		// When
		underTest.startConnection("localhost", port);
		underTest.sendMessage(message);
		underTest.closeConnection();
		Thread.sleep(100);
		
		// Then
		verify(messageCallback, times(1)).accept(message);
	}

}
