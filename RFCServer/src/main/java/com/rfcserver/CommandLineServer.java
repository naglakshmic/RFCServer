package com.rfcserver;

import com.rfcserver.DayTimeServer;
import com.rfcserver.EchoServer;
import com.rfcserver.ManageShutDown;
import com.rfcserver.TCPDayTimeServer;
import com.rfcserver.TCPEchoServer;

public class CommandLineServer {
	
	public static void main(String[] args) {
		final int ECHO_SERVER_PORT = 7;
		final int TIME_SERVER_PORT = 37;
		final int DAYTIME_SERVER_PORT = 13;
		EchoServer echoServer = null;
		TCPEchoServer tcpEchoServer = null;
		TimeServer timeServer = null;
		TCPTimeServer tcpTimeServer = null;
		DayTimeServer dayTimeServer = null;
		TCPDayTimeServer tcpDayTimeServer = null;
				
		switch(args.length){
			case 1: case 2: case 3:
				for (String element : args) {
					switch (Integer.parseInt(element)){					
						case ECHO_SERVER_PORT:
							System.out.println("STARTING SERVER ECHO SERVER: " + ECHO_SERVER_PORT);
							// RFC 862: ECO Protocol, URL: https://tools.ietf.org/html/rfc862 @ Port 7
							echoServer = new EchoServer("UDP Echo Server", ECHO_SERVER_PORT);
							echoServer.start();		 
							tcpEchoServer = new TCPEchoServer("UDP Echo Server", ECHO_SERVER_PORT);
							tcpEchoServer.start();							
							break;
						case TIME_SERVER_PORT:
							System.out.println("STARTING SERVER TIME_SERVER_PORT: "+TIME_SERVER_PORT);
							// RFC 868: Time Protocol, URL: https://tools.ietf.org/html/rfc868 @ Port 37
							timeServer = new TimeServer("UDP Time Server", TIME_SERVER_PORT);
							timeServer.start();
							tcpTimeServer = new TCPTimeServer("UDP Time Server", TIME_SERVER_PORT);
							tcpTimeServer.start();							
							break;
						case DAYTIME_SERVER_PORT:
							System.out.println("STARTING SERVER DAYTIME Server: "+ DAYTIME_SERVER_PORT);
							// RFC 867: Daytime Protocol - https://tools.ietf.org/html/rfc867 @ Port 13
							// Example: Tuesday, February 22, 1982 17:37:43-PST
							dayTimeServer = new DayTimeServer("UDP Daytime Server", DAYTIME_SERVER_PORT);
							dayTimeServer.start();		 
							tcpDayTimeServer = new TCPDayTimeServer("TCP Daytime Server", DAYTIME_SERVER_PORT);
							tcpDayTimeServer.start();							
							break;
						default:
							System.out.println("Cannot start server @port: " + element+ ". Port supported : 7, 37 and 13 ");
			            	break;
					}		    
				}
				break;
				
            default:
            	System.out.println("Usage java com.rfcserver.CommandLineServer <PORT1> <PORT2> <PORT3>\n"
						+"\n for example for default java com.rfcserver.CommandLineServer 7 37 13 ");
            	System.exit(-1);
            	break;
		}
				
		// SIGINT implementation
	    try {
	        Runtime.getRuntime().addShutdownHook(new ManageShutDown(echoServer, 
	        														tcpEchoServer, 
	        														timeServer, 
	        														tcpTimeServer, 
	        														dayTimeServer, 
	        														tcpDayTimeServer));
	        System.out.println("Listening for SIGINT");
	    } catch (Throwable t) {
	        System.out.println("Could not add SIGINT listner");
	    }
 	}
}

