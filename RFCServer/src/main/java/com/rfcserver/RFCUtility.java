package com.rfcserver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.IOException;

public class RFCUtility {

	private static long TIME_SINCE_1900_1970 = 2208988800L;
	
	public static long getTime(byte[] timeData) throws IOException
	{
		long time;
 		time = 0L;
		time |= (((timeData[0] & 0xff) << 24) & 0xffffffffL);
		time |= (((timeData[1] & 0xff) << 16) & 0xffffffffL);
		time |= (((timeData[2] & 0xff) << 8) & 0xffffffffL);
		time |= ((timeData[3] & 0xff) & 0xffffffffL);
		return time;
		// Reference to the 32 bit conversion code: http://www.javanio.info/filearea/bookexamples/unpacked/com/ronsoft/books/nio/channels/TimeServer.java
	}
	
	public static long getRFC868Time()
	{
		long epocTime = new Date().getTime(); // milliseconds from 1970 - current time
        long rfc868Time =  epocTime/1000 + TIME_SINCE_1900_1970; 
        System.out.println("Time from 1900 to Epoc time:"+TIME_SINCE_1900_1970+"(seconds)\nTime Since Epoc:"+epocTime/1000+"(seconds)\nRFC868 Time:"+rfc868Time);
        return rfc868Time;  
	}
	
	public static String getRFC868TimeString()
	{
		long epocTime = new Date().getTime(); // milliseconds from 1970 - current time
        long rfc868Time =  epocTime + TIME_SINCE_1900_1970;
        String time = String.valueOf(rfc868Time);
        System.out.println("Time before Epoc time:"+TIME_SINCE_1900_1970+" Time Since Epoc:"+epocTime+" RFC868 Time:"+rfc868Time);
        return time;
	}
	
	public static String getRFC867DayTime()
	{
		  SimpleDateFormat dt1 = new SimpleDateFormat("EEEEE, MMMMM dd, yyyy hh:mm:ss-z");
          String date = dt1.format(new Date());
          return date;
	}
}
