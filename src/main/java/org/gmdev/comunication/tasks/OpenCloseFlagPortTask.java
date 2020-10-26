package org.gmdev.comunication.tasks;

import java.io.IOException;
import java.net.ServerSocket;

import org.gmdev.serviceprocessor.ServiceRequest;

public class OpenCloseFlagPortTask implements ServiceRequest {

	private final int sendFirstFlagPort;
	private ServerSocket flagServerSocket;
	
	public OpenCloseFlagPortTask(int sendFirstFlagPort) {
		this.sendFirstFlagPort = sendFirstFlagPort;
	}

	@Override
	public void process() throws Exception {
		flagServerSocket = new ServerSocket(sendFirstFlagPort);
	}
	
	public void close() throws IOException {
		if (flagServerSocket != null)
			flagServerSocket.close();
	}
	
	

}
