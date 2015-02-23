package com.rfcserver;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.DatagramChannel;

/*
 * RFC868 UDP Time Server
 */
public class TimeServer extends Thread {
	public int port = 37;
	protected DatagramChannel channel;
	
	public TimeServer(String name, int port) {
        super(name);
        
        try{
        	this.channel = DatagramChannel.open();
    		this.channel.socket().bind (new InetSocketAddress (port));
    		this.port = port;
        } catch (Exception e){
        	e.printStackTrace();
        }    
    }

    public void run() {
    	
    	ByteBuffer longBuffer = ByteBuffer.allocate (8); // allocate a buffer to hold a long value
    	longBuffer.order (ByteOrder.BIG_ENDIAN); // assure big-endian (network) byte order
    	longBuffer.putLong (0, 0); // zero the whole buffer to be sure
    	longBuffer.position (4); // position to first byte of the low-order 32 bits
    	ByteBuffer buffer = longBuffer.slice();	// slice the buffer, gives view of the low-order 32 bits
    	   	
        while (!Thread.currentThread().isInterrupted())
        {
        	buffer.clear();
            try {
            	System.out.println("<RFC868 UDP Time Server> listening for connections @ port :" + port);
            	SocketAddress socketAddress = this.channel.receive (buffer);

    			if (socketAddress == null) {
    				continue;	// defensive programming
    			}
    			// ignore content of recived datagram per rfc 868
    			System.out.println ("<RFC868 UDP Time Server> Client Connected :" + socketAddress);
    			buffer.clear();		// sets pos/limit correctly
    			// set 64-bit value, slice buffer sees low 32 bits
    			long time = RFCUtility.getRFC868Time();
    			System.out.println ("<RFC868 UDP Time Server> Responding to client :" + time);
    			longBuffer.putLong (0,time);
    			this.channel.send (buffer, socketAddress);                 
            } catch (Exception e) {
            	try {
					this.channel.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
            }
         }        
    }
    
    public void stopThread() {
    	System.out.println("<RFC868 UDP Time Server> Shutting Down !!");    	
    	Thread.currentThread().interrupt();
    	System.out.println("<RFC868 UDP Time Server> Server Down ? " + this.isInterrupted());
    }
}
