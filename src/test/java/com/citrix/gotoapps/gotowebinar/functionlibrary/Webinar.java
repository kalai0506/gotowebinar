package com.citrix.gotoapps.gotowebinar.functionlibrary;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.citrix.gotoapps.gotowebinar.pageobjects.ManageWebinarPage;
import com.citrix.gotoapps.gotowebinar.pageobjects.MyWebinarPage;
import com.citrix.gotoapps.gotowebinar.pageobjects.ScheduleWebinarPage;
import com.citrix.gotoapps.gotowebinar.utility.DateFormats;

public class Webinar {
	final static Logger Log = Logger.getLogger(Webinar.class.getName());
	private WebDriver driver = null;
	HashMap<String, String> mapTestData = null;
	private DateFormats objDate = null;
	String startDay = null;
	String startMonth = null;

	public Webinar(WebDriver driver) {
		this.driver = driver;
	}

	public Boolean scheduleWebinar(HashMap<String, String> mapTestData){
		Boolean retValue=false;
		try{
			String webinarTitle=mapTestData.get("Webinar Title");
			String description=mapTestData.get("Description");
			//String type=mapTestData.get("Webinar Type");
			int daysToStart=Integer.parseInt(mapTestData.get("No Of Days to Start"));
			Log.info("Day to start: "+daysToStart);
			String startTime=mapTestData.get("Start Time");
			Log.info("Start Time: "+startTime);
			String startTimeAMPM=mapTestData.get("Start Time AMPM");
			Log.info("Start Time AMPM: "+startTimeAMPM);
			String endTime=mapTestData.get("End Time");
			Log.info("End Time: "+endTime);
			String endTimeAMPM=mapTestData.get("End Time AMPM");
			Log.info("Start Time AMPM: "+endTimeAMPM);
			String timeZone=mapTestData.get("Time Zone");
			Log.info("Time Zone: "+timeZone);
			String language=mapTestData.get("Language");
			Log.info("Language: "+language);
					
			Log.debug("click enter secure button");
			MyWebinarPage myWebinar=new MyWebinarPage(driver);
			if(myWebinar.verifyMyWebinarPage()){
				myWebinar.clickScheduleWebinar();
				ScheduleWebinarPage objWebinar=new ScheduleWebinarPage(driver);
				if(objWebinar.verifyScheduleWebinarPage()){
				objWebinar.enterWebinarTitle(webinarTitle);
				objWebinar.enterDescription(description);		
				objWebinar.openStartDatePicker();
				objWebinar.verifyStartDatePicker();
				String currMonth=objWebinar.getMonth();
				objDate=new DateFormats();
				startDay=objDate.getStartDay(daysToStart);
				startMonth=objDate.getStartMonth(daysToStart);
				if(currMonth.equals(startMonth)){
					objWebinar.clickRequiredDate(startDay);
				}else{
					objWebinar.clickNextMonth();
					objWebinar.clickRequiredDate(startDay);
				}
				objWebinar.enterStartTime(startTime, startTimeAMPM);
				objWebinar.enterEndTime(endTime, endTimeAMPM);
				objWebinar.selectTimeZone(timeZone);
				objWebinar.selectLanguage(language);
				objWebinar.clickSchedule();
				retValue=true;
				}else{
				Log.info("Home Page not loaded properly");
				throw new NoSuchElementException();
				}
			}else{
				Log.info("My Webinar Page not loaded properly");
				throw new NoSuchElementException();
			}
			return retValue;
		}catch(Exception e){
			Log.error("Exception in scheduleWebinar:",e);
			return retValue;
		}
	}

	public Boolean verifyWebinarDetails(HashMap<String, String> mapTestData) {
		Boolean retValue = false;
		try {
			String webinarTitle = mapTestData.get("Webinar Title");
			int daysToStart = Integer.parseInt(mapTestData
					.get("No Of Days to Start"));
			Log.info("Day to start: " + daysToStart);
			String startTime = mapTestData.get("Start Time");
			Log.info("Start Time: " + startTime);
			String startTimeAMPM = mapTestData.get("Start Time AMPM");
			Log.info("Start Time AMPM: " + startTimeAMPM);
			String endTime = mapTestData.get("End Time");
			Log.info("End Time: " + endTime);
			String endTimeAMPM = mapTestData.get("End Time AMPM");
			Log.info("Start Time AMPM: " + endTimeAMPM);
			String timeZone = mapTestData.get("Time Zone");
			Log.info("Time Zone: " + timeZone);
			String language = mapTestData.get("Language");
			Log.info("Language: " + language);
			String webinarKey = null;
			String webinarDate = null;
			String webinarTime = null;
			ManageWebinarPage objManageWebinar = new ManageWebinarPage(driver);

			if (objManageWebinar.verifyManageWebinarPage()) {
				webinarKey = objManageWebinar.getWebinarKey();
				objManageWebinar.clickMyWebinars();
				retValue = true;
			} else {
				Log.info("Manage Webinar Page not loaded properly");
				retValue = false;
				throw new NoSuchElementException();
			}
			MyWebinarPage myWebinar = new MyWebinarPage(driver);
			if (myWebinar.verifyMyWebinarPage()) {
				myWebinar.searchWebinar(webinarTitle);
				webinarDate = myWebinar.getWebinarDate(webinarKey);
				webinarTime = myWebinar.getWebinarTime(webinarKey);
				if (!this.verifyDate(webinarDate, daysToStart)
						.equalsIgnoreCase("success")) {
					retValue = false;
				}
				if (!this.verifyTime(startTime, startTimeAMPM, endTime,
						endTimeAMPM, webinarTime).equalsIgnoreCase("success")) {
					retValue = false;
				}
				retValue = true;
			} else {
				Log.info("My Webinar Page not loaded properly");
				retValue = false;
				throw new NoSuchElementException();
			}
			return retValue;
		} catch (Exception e) {
			Log.error("Exception in scheduleWebinar:", e);
			return retValue;
		}
	}

	public String[] getDayMonth(String webinarDate) {
		String[] webinarDateDetails = new String[2];
		webinarDateDetails[0] = webinarDate.substring(5, 8);
		Log.info("Month: " + webinarDateDetails[0]);
		webinarDateDetails[1] = webinarDate.substring(10, 11).trim();
		Log.info("Day: " + webinarDateDetails[1]);
		return webinarDateDetails;
	}

	public String[] getTimeAMPMZone(String webinarTime) {
		String[] webinarTimeDetails = new String[4];
		String pattern = "(^[0-9]+:[0-9][0-9])(\\s)([A-Z]M)(\\s-\\s)([0-9]+:[0-9][0-9])(\\s)([A-Z]M)";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(webinarTime);
		while (m.find()) {
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
			System.out.println("Found value: " + m.group(3));
			System.out.println("Found value: " + m.group(4));
			System.out.println("Found value: " + m.group(5));
			System.out.println("Found value: " + m.group(6));
			System.out.println("Found value: " + m.group(7));
			webinarTimeDetails[0] = m.group(1);
			webinarTimeDetails[1] = m.group(3);
			webinarTimeDetails[2] = m.group(5);
			webinarTimeDetails[3] = m.group(7);
		}
		Log.info("Start Time: " + webinarTimeDetails[0]);
		Log.info("Start AMPM: " + webinarTimeDetails[1]);
		Log.info("End Time: " + webinarTimeDetails[2]);
		Log.info("End AMPM: " + webinarTimeDetails[3]);
		return webinarTimeDetails;
	}

	public String verifyDate(String webinarDate, int daysToStart) {
		String returnMsg = "success";
		objDate = new DateFormats();
		startMonth = objDate.getStartMonth(daysToStart).substring(0, 2);
		startDay = objDate.getStartDay(daysToStart);
		String[] webinarDateDetails = this.getDayMonth(webinarDate);

		if (!startMonth.equalsIgnoreCase(webinarDateDetails[0])) {
			returnMsg = "Month is not matching";
		}
		if (!startDay.equalsIgnoreCase(webinarDateDetails[1])) {
			returnMsg = "Day is not matching";
		}
		return returnMsg;

	}

	public String verifyTime(String startTime, String startTimeAMPM,
			String endTime, String endTimeAMPM, String webinarTime) {
		String returnMsg = "success";

		String[] webinarTimeDetails = this.getTimeAMPMZone(webinarTime);
		if (!startTime.equalsIgnoreCase(webinarTimeDetails[0])) {
			returnMsg = "Start Time is not matching";
		}
		if (!startTimeAMPM.equalsIgnoreCase(webinarTimeDetails[1])) {
			returnMsg = "Start Time AMPM is not matching";
		}
		if (!endTime.equalsIgnoreCase(webinarTimeDetails[2])) {
			returnMsg = "End Time is not matching";
		}
		if (!endTimeAMPM.equalsIgnoreCase(webinarTimeDetails[3])) {
			returnMsg = "End Time AMPM is not matching";
		}
		return returnMsg;

	}

}
