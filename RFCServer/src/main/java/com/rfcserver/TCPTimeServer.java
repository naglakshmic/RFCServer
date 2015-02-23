package com.rfcserver;

import java.net.ServerSocket;
import java.net.Socket;

/*
 * RFC868
 */
public class TCPTimeServer extends Thread {
	public int port = 37;
    ServerSocket tcpServerSocket;
    Socket connection = null;
    
	public TCPTimeServer(String name, int port) {
        super(name);
        try{
        	tcpServerSocket = new ServerSocket(port, 10);
        	this.port = port;
        }catch (Exception e){
        	e.printStackTrace();
        }    
    }

    public void run() {
    	 
		while (!this.isInterrupted()) {
        	try {
        		System.out.println("<RFC868 TCP Time Server> listening for connections @ port :" + port);
        		new TCPRequestHandler(tcpServerSocket.accept()).start();
        	} catch ( Exception e) {	
        	 	e.printStackTrace();        	 	
        	}
        }
    }
    
    public void stopThread() {
    	System.out.println("<RFC868 TCP Time Server> Shutting Down !! ");
    	Thread.currentThread().interrupt();
    	System.out.println("<RFC868 TCP Time Server> Server Down? : " + this.isInterrupted());
    }
}
