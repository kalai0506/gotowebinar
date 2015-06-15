package com.citrix.gotoapps.gotowebinar.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;


public class DateFormats {
	final static Logger Log = Logger.getLogger(DateFormats.class.getName());
	
	public String getStartDay(int daysToAdd){
		DateFormat dateFormat = new SimpleDateFormat("dd");
		GregorianCalendar calCp = new GregorianCalendar();
		calCp.add(Calendar.DAY_OF_MONTH, +daysToAdd);
		int intStartDay=Integer.parseInt(dateFormat.format(calCp.getTime()));
		String strStartDay=String.valueOf(intStartDay);
		Log.debug("Webinar start day: "+strStartDay); //2014/08/06 15:59:48
		return strStartDay;
	}
	
	public String getStartMonth(int daysToAdd){
		DateFormat dateFormat = new SimpleDateFormat("MMMM");
		GregorianCalendar calCp = new GregorianCalendar();
		calCp.add(Calendar.DAY_OF_MONTH, +daysToAdd);
		String strStartMonth=dateFormat.format(calCp.getTime()).toString();
		Log.debug("Webinar start Month: "+strStartMonth); //2014/08/06 15:59:48
		return strStartMonth;
	}
}
