package com.rfcserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * RFC862
 */
public class EchoServer extends Thread {
	public int port = 7;
	private DatagramSocket udpSocket ;
	
	public EchoServer(String name, int port) {
        super(name);
        
        try{
        	  udpSocket = new DatagramSocket(port);
        	  this.port = port;
        } catch (Exception e){
        	e.printStackTrace();
        }    
    }

    public void run() {
    	
        while (!this.isInterrupted()) {
            try {
            	System.out.println("<RFC862 UDP Echo Server> listening for connections @ port :" + port);
                byte[] buf = new byte[512];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                udpSocket.receive(packet);
                buf = packet.getData();
                System.out.println("Sending back Recieved data to client: "+new String(packet.getData(), 0, packet.getLength()));
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                udpSocket.send(packet);                
            } 
            catch (Exception e) {
            	e.printStackTrace();
            	udpSocket.close(); 
            }            
        }
    }
    
    public void stopThread() {
    	System.out.println("<RFC862 UDP Echo Server> Shutting Down !!");    	
    	Thread.currentThread().interrupt();
    	System.out.println("<RFC862 UDP Echo Server> Server Down ? " + this.isInterrupted());
      }


}
