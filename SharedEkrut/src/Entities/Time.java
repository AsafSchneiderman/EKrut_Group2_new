package Entities;

import java.io.Serializable;

public class Time implements Serializable {
		
		private int hour;
	    private int minute;
	    private int second;
	    private String orderId;
	    
	    public Time(int hour, int minute, int second) {
	        this.hour = hour;
	        this.minute = minute;
	        this.second = second;
	    }

	    public Time(String currentTime) {
	        String[] time = currentTime.split(":");
	       
	        hour = Integer.parseInt(time[0]);
	        minute = Integer.parseInt(time[1]);
	        second = Integer.parseInt(time[2]);
	       
	    }
	    public Time(String currentTime,String orderId) {
	        String[] time = currentTime.split(":");
	       
	        hour = Integer.parseInt(time[0]);
	        minute = Integer.parseInt(time[1]);
	        second = Integer.parseInt(time[2]);
	        this.orderId = orderId;
	    }

	    public String getOrderId() {
			return orderId;
		}

		public String getCurrentTime(){
	    	String hour_ = String.valueOf(hour);
	    	String minute_ = String.valueOf(minute);
	    	String second_ = String.valueOf(second);
	    	if (hour<10) {
	    		hour_ = String.format("%02d", hour);
	    		
	    	}
	    	if (minute<10) {
	    		minute_ = String.format("%02d", minute);
	    	}
	    	if (second<10) {
	    		second_ = String.format("%02d", second);
	    	}
	    	
	        return hour_ + ":" + minute_ + ":" + second_;
	    }

	    public boolean oneSecondPassed() {
	    if(hour > 0 && minute == 0)
	    {
	    	minute = 59;
	    	hour --;
	    }
	    if(minute > 0 && second == 0)
	    {
	    	second = 60;
	    	minute --;
	    }
	    if(minute > 0 || second > 0 || hour > 0)
	    {
	    	second--;
	    	return false;
	    }
	    else if(second == 0)
	    {
	    	second = 0;
        	return true;
	    }
	    return false;
	   
	    }
	   }	       
	        
	    



