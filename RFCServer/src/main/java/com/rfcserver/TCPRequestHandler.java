package com.rfcserver;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

public class TCPRequestHandler extends Thread {
	private Socket connection = null;
	
	public TCPRequestHandler(Socket connection){
		super("TCP Request Handler");
		this.connection = connection;
	}
	
	public void run() {		
		ByteBuffer longBuffer = ByteBuffer.allocate (8); // allocate a buffer to hold a long value
		longBuffer.order (ByteOrder.BIG_ENDIAN); // assure big-endian (network) byte order		
		longBuffer.putLong (0, 0); // zero the whole buffer to be sure		
		longBuffer.position (4); // position to first byte of the low-order 32 bits	
		ByteBuffer buffer = longBuffer.slice(); // slice the buffer, gives view of the low-order 32 bits
		
		try {
			System.out.println ("<RFC868 TCP Time Server> Client Connected :" + connection.getInetAddress().getHostName());
           	buffer.clear();	 
    		long time = RFCUtility.getRFC868Time();
    		System.out.println ("<RFC868 TCP Time Server> Responding to client :" + time);    			
   			longBuffer.putLong (0,time);
   			WritableByteChannel channel = Channels.newChannel(connection.getOutputStream());    			
   			channel.write(buffer);
           	connection.close();
       	}catch ( Exception e) {
            e.printStackTrace();
        }
	}
	
	public void stopThread() {
		this.interrupt();
	}
}
