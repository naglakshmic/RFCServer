package com.rfcserver;

import java.io.*;
import java.util.Scanner;
import java.net.*;

/*
 * RFC862
 */
public class TCPEchoServer extends Thread {
	public int port = 7;
    ServerSocket tcpServerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;

    public TCPEchoServer(String name, int port) {
        super(name);
        try{
           tcpServerSocket = new ServerSocket(port, 10);
           this.port = port;
        } catch (Exception e){
        	e.printStackTrace();
        }    
    }

    public void run() {
    	
        while (!this.isInterrupted()) {
            try {
            	System.out.println("<RFC862 TCP Echo Server> listening for connections @ port :" + port);                
                connection = tcpServerSocket.accept();
                System.out.println("Connection received from " + connection.getInetAddress().getHostName());
                Scanner scanner = new Scanner(connection.getInputStream());
                PrintWriter printWriter = new PrintWriter(connection.getOutputStream(), true);
                String message = scanner.nextLine();
                System.out.println("Received Message Echoing back to Client:"+message);
                printWriter.println(message);
                scanner.close();
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
    	System.out.println("<RFC862 TCP Echo Server> Shutting Down !!");    	
    	Thread.currentThread().interrupt();
    	System.out.println("<RFC862 TCP Echo Server> Server Down ? " + this.isInterrupted());
      }

}
