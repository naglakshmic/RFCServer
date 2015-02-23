package com.rfcserver;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/*
 * RFC867 UDP Daytime Server
 */
public class DayTimeServer extends Thread {
	public int port = 13;
	private DatagramSocket udpSocket ;
  
	public DayTimeServer(String name, int port) {
        super(name);
        try{
        	udpSocket = new DatagramSocket(port);
        	this.port = port;
        } catch (Exception e) {
        	e.printStackTrace();
        }    
    }

    public void run() {
    	
        while (!this.isInterrupted()) {
            try {
            	System.out.println("<RFC867 UDP DayTime Server> listening for connections @ port :" + port);
                byte[] buf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                udpSocket.receive(packet);
                //RFC https://tools.ietf.org/html/rfc867   Tuesday, February 22, 1982 17:37:43-PST
                //SimpleDateFormat dt1 = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy hh:mm:ss-z");
                String date = RFCUtility.getRFC867DayTime(); //dt1.format(new Date());
                buf=date.getBytes();
                System.out.println("Sending back date to client: "+date);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                udpSocket.send(packet);
            } catch (Exception e) {
            	e.printStackTrace();
            	udpSocket.close();
            }
        }       
    }
    
    public void stopThread() {
    	System.out.println("<RFC867 UDP DayTime Server> Shutting Down !!");    	
    	Thread.currentThread().interrupt();
       	System.out.println("<RFC867 UDP DayTime Server> Server Down ? :"+this.isInterrupted());   
    }
}

