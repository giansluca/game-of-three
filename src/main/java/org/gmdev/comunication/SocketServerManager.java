package org.gmdev.comunication;

import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

import org.gmdev.comunication.tasks.*;
import org.gmdev.serviceprocessor.ServiceScheduler;


public class SocketServerManager {
	
	private final ServiceScheduler serviceSheduler;
	private StartStopServerTask startStopServerTask;
	private OpenCloseFlagPortTask openCloseFlagPortTask;
	
	public SocketServerManager() {
		serviceSheduler = ServiceScheduler.getServiceScheduler();
	}
	
	public void startServer(Consumer<String> messageCallback, int serverPort) {
		startStopServerTask = new StartStopServerTask(messageCallback, serverPort);
		serviceSheduler.schedule(startStopServerTask);
		startStopServerTask.waitForServerUp();
	}
	
	public void openSendFirstFlagPort(int sendFirstFlagPort) {
		openCloseFlagPortTask = new OpenCloseFlagPortTask(sendFirstFlagPort);
		serviceSheduler.schedule(openCloseFlagPortTask);
	}
	
	public void stop() {
		closeSendFirstFlagPort();
		stopServer();
	}
	
	public void stopServer() {
		if (startStopServerTask == null) return;
		try {
			startStopServerTask.stop();
		} catch (Exception e) {
			throw new IllegalStateException("FATAL!", e);
		}
	}
	
	public void closeSendFirstFlagPort() {
		if (openCloseFlagPortTask == null) return;
		try {
			openCloseFlagPortTask.close();
		} catch (IOException e) {
			throw new IllegalStateException("FATAL!", e);
		}
	}
	
	public boolean isOtherPlayerUp(String address, int port) {
		return isPortBusy(address, port) ? true : false;
	}
	
	public boolean isPortBusy(String address, int port) { 
		try {
			Socket socket = new Socket(address, port);
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public boolean isServerExited() {
		return startStopServerTask.isExited();
	}
	

}
