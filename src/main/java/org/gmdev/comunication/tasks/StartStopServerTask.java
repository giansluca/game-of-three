package org.gmdev.comunication.tasks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import org.gmdev.serviceprocessor.ServiceRequest;

public class StartStopServerTask implements ServiceRequest {
	
	public static final String STOP = "STOP";
	
	private final Consumer<String> messageCallback;
	private final int serverPort;
	private final AtomicBoolean exited = new AtomicBoolean(false);
	private ServerSocket serverSocket;
	
	public StartStopServerTask(Consumer<String> messageCallback, int serverPort) {
		this.messageCallback = messageCallback;
		this.serverPort = serverPort;
	}

	@Override
	public void process() throws IOException {
		serverSocket = new ServerSocket(serverPort);
		Socket socket;
		PrintWriter out;
		BufferedReader in;
		
		while(true) {
			socket = serverSocket.accept();
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));

			String message = in.readLine();
			if (message == null)
				continue;

			if (message.equals(STOP))
				break;

			messageCallback.accept(message);
		}
		
		if (socket != null) socket.close();
		if (out != null) out.close();
		if (in != null) in.close();
		
		exited.set(true);
	}
	
	public void stop() throws Exception {
		if (serverSocket == null) return;
		
		sendStopSignal();
		while (!exited.get()) {
			Thread.sleep(50);
		}

		serverSocket.close();		
	}
	
	private void sendStopSignal() throws IOException {
		Socket socket = new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort());
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(STOP);
		socket.close();
	}
	
	public void waitForServerUp() {	
		while (serverSocket == null || !serverSocket.isBound()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				throw new IllegalStateException("FATAL!", e);
			}
		}
	}
	
	public boolean isExited() {
		return exited.get();
	}
	
	
	
}
