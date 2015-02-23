package com.rfcserver;

import com.rfcserver.DayTimeServer;
import com.rfcserver.EchoServer;
import com.rfcserver.TCPDayTimeServer;
import com.rfcserver.TCPEchoServer;

public class ManageShutDown extends Thread {
	 
	private EchoServer echoServer = null;
	private TCPEchoServer tcpEchoServer = null;
	private TimeServer timeServer = null; 
	private TCPTimeServer rfcTcpTimeServer = null; 
	private DayTimeServer dayTimeServer = null; 
	private TCPDayTimeServer tcpDayTimeServer = null;
	  
	public ManageShutDown ( EchoServer echoServer,
			  				TCPEchoServer tcpEchoServer,
			  				TimeServer timeServer, 
			  				TCPTimeServer rfcTcpTimeServer, 
			  				DayTimeServer dayTimeServer, 
			  				TCPDayTimeServer tcpDayTimeServer)
	{
	    super();
	    this.echoServer = echoServer;
		this.tcpEchoServer = tcpEchoServer;
		this.timeServer = timeServer; 
		this.rfcTcpTimeServer = rfcTcpTimeServer; 
		this.dayTimeServer = dayTimeServer; 
		this.tcpDayTimeServer = tcpDayTimeServer;
    }
	   
    public void run() {
	    System.out.println("[Shutdown thread] Shutting down");
	    
	    if(this.echoServer != null){
		    this.echoServer.interrupt();
		    this.echoServer.stopThread();
	    }
	    
	    if(this.tcpEchoServer != null){
			this.tcpEchoServer.interrupt();
			this.tcpEchoServer.stopThread();
	    }
	    
		if(this.timeServer != null){
			this.timeServer.interrupt();
			this.timeServer.stopThread();
		}
		
		if(this.rfcTcpTimeServer != null){
			this.rfcTcpTimeServer.interrupt();
			this.rfcTcpTimeServer.stopThread(); 
		}
		
		if(this.dayTimeServer != null){
			this.dayTimeServer.interrupt();
			this.dayTimeServer.stopThread();
		}
		
		if(this.tcpDayTimeServer != null){
			this.tcpDayTimeServer.interrupt();
			this.tcpDayTimeServer.stopThread();
		}
		System.out.println("[Shutdown thread] Shutdown complete");
	}
}
