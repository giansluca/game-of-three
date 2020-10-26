package org.gmdev.comunication;

import java.io.*;
import java.net.Socket;

public class SocketClient {
	
	private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    public void startConnection(String address, int port) throws IOException {
    	socket = new Socket(address, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
 
    public void sendMessage(String message) {
        out.println(message);
    }
 
    public void closeConnection() throws IOException {
        if (in != null)
        	in.close();
        
        if (out != null)
        	out.close();
        
        if (socket != null)
        	socket.close();
    }
    
}
