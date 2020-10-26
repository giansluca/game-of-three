package org.gmdev.unit.comunication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

import org.gmdev.comunication.SocketServerManager;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class SocketServerManagerTest {

	SocketServerManager underTest;
	
	@Mock
	Consumer<String> messageCallback;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		underTest = new SocketServerManager();
	}
	
	@AfterEach
	void tearDown(TestInfo testInfo) throws Exception {
		if(testInfo.getTags().contains("SkipCleanup")) return;
		underTest.stopServer();
		underTest.closeSendFirstFlagPort();
	}
	
	@Test
	void itShouldStartTheServer() throws Exception {
		// Given
		int port = 8888;
		
		// When
		underTest.startServer(messageCallback, port);
		Thread.sleep(100);
		
		// Then
		assertThat(underTest.isPortBusy("localhost", 888)).isFalse();
	}
	
	@Test
	void itShouldDoNothingIfNullValueIsSent() throws Exception {
		// Given
		String address = "localhost";
		int port = 8888;
		
		// When
		underTest.startServer(messageCallback, port);
		Thread.sleep(100);
			
		// ... open a socket and close it will write a NULL value on it 
		Socket so = new Socket(address, port);
		so.close();
		
		// Then
		assertThat(underTest.isServerExited()).isFalse();
	}
	
	@Test
	void isShouldCallTheCallbackFunction() throws Exception {
		// Given
		String address = "localhost";
		int port = 8888;
		String number = "99"; 
				
		// When
		underTest.startServer(messageCallback, port);
		Thread.sleep(100);
		
		Socket so = new Socket(address, port);
		var writer = new PrintWriter(so.getOutputStream(), true);
		writer.println(number);
		so.close();
		Thread.sleep(100);
		
		// Then
		verify(messageCallback, times(1)).accept(number);
	}
	
	@Test
	@Tag("SkipCleanup")
	void itShouldStopMainServerAndFlagServer() throws Exception {
		// Given
		String address = "localhost";
		int port = 8888;
		int flagPort = 6666;
						
		underTest.startServer(messageCallback, port);
		underTest.openSendFirstFlagPort(flagPort);
		Thread.sleep(100);
		
		// When
		underTest.stop();
		Thread.sleep(100);
		
		// Then
		assertThat(underTest.isServerExited()).isTrue();
		assertThat(underTest.isOtherPlayerUp(address, port)).isFalse();
		assertThat(underTest.isOtherPlayerUp(address, flagPort)).isFalse();
	}
	
	@Test
	void itShouldReturnTrueWhenServerisRunning() throws Exception {
		// Given
		String address = "localhost";
		int port = 8888;
		
		// When
		underTest.startServer(messageCallback, port);
		Thread.sleep(100);
		
		// Then
		assertThat(underTest.isOtherPlayerUp(address, port)).isTrue();
	}
	

}
