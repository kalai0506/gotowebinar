package com.citrix.gotoapps.gotowebinar.utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;


public class DateFormats {
	final static Logger Log = Logger.getLogger(DateFormats.class.getName());

	public String getBusinessEndDate(int daysToAdd){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		GregorianCalendar calCp = new GregorianCalendar();
		calCp.add(Calendar.DAY_OF_MONTH, +daysToAdd);

		boolean isSaturday = (calCp.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
	    boolean isSunday = (calCp.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);

		if (isSaturday) {
	    	  calCp.add(Calendar.DAY_OF_MONTH, +2); // is saturday, make it monday
	    } else if (isSunday) {
	    	  calCp.add(Calendar.DAY_OF_MONTH, +1); // is sunday, make it monday
	    } 
		String strEndDate=dateFormat.format(calCp.getTime());
		Log.info(strEndDate); //2014/08/06 15:59:48
		return strEndDate;
	}
	
	public String getStartDay(int daysToAdd){
		DateFormat dateFormat = new SimpleDateFormat("dd");
		GregorianCalendar calCp = new GregorianCalendar();
		calCp.add(Calendar.DAY_OF_MONTH, +daysToAdd);
		String strStartDay=dateFormat.format(calCp.getTime()).toString();
		Log.info(strStartDay); //2014/08/06 15:59:48
		return strStartDay;
	}
	
	public String getStartMonth(int daysToAdd){
		DateFormat dateFormat = new SimpleDateFormat("MMMM");
		GregorianCalendar calCp = new GregorianCalendar();
		calCp.add(Calendar.DAY_OF_MONTH, +daysToAdd);
		String strStartMonth=dateFormat.format(calCp.getTime()).toString();
		Log.info(strStartMonth); //2014/08/06 15:59:48
		return strStartMonth;
	}
}
