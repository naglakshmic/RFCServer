package com.rfcserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * RFC867 TCP Day Time Server
 */
public class TCPDayTimeServer extends Thread {
	public int port = 13;
    ServerSocket tcpServerSocket;
    Socket connection = null;
   
	public TCPDayTimeServer(String name, int port) {
        super(name);
        try {
        	tcpServerSocket = new ServerSocket(port, 10);
        	this.port = port;
        } catch (Exception e){
        	e.printStackTrace();
        }    
    }

    public void run() {
    	
        while (!this.isInterrupted()) {
            try {
            	System.out.println("<RFC867 TCP DayTime Server> listening for connections @ port :" + port);
            	connection = tcpServerSocket.accept();
            	System.out.println("Connection received from " + connection.getInetAddress().getHostName());
            	PrintWriter printWriter = new PrintWriter(connection.getOutputStream(), true);
            	String message = RFCUtility.getRFC867DayTime();
            	printWriter.println(message);            	
            } catch (Exception e) {
            	e.printStackTrace();
            }
            finally{
            	try{
            		connection.close();
            	} catch(IOException e){
            		e.printStackTrace();
            	}
            }
        }
    }
    
    public void stopThread() {
    	System.out.println("<RFC867 TCP DayTime Server> Shutting Down !!");       	
    	Thread.currentThread().interrupt();
    	System.out.println("<RFC867 UDP DayTime Server> Server Down ? :" + this.isInterrupted());
    }
}

