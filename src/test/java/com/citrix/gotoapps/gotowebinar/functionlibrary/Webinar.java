/**
 * @author Kalaiselvan Ulaganathan
 * @Date 06/15/2015
 * @Version 1.0
 * @Description This class contains business functionality of schedule weninars
 **/
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

	public Webinar(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * This method schedules a webinar by taking values from user's test data excel 
	 * @param mapTestData
	 * @return
	 */
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
			//Verify if My Webinar page is displayed after login
			MyWebinarPage objMyWebinar=new MyWebinarPage(driver);
			if(objMyWebinar.verifyMyWebinarPage()){
				//click Schedule Webinar link 
				objMyWebinar.clickScheduleWebinar();
				//verify if Schedule Webinar page is displayed
				ScheduleWebinarPage objSchdWebinar=new ScheduleWebinarPage(driver);
				if(objSchdWebinar.verifyScheduleWebinarPage()){
					//Fill the schedule webinar form
					objSchdWebinar.enterWebinarTitle(webinarTitle);
					objSchdWebinar.enterDescription(description);
					//Logic for date selector starts here
					objSchdWebinar.openStartDatePicker();
					objSchdWebinar.verifyStartDatePicker();
					String currMonth=objSchdWebinar.getMonth();
					Log.info("Current Month in the calendar: "+currMonth);
					objDate=new DateFormats();
					String webinarStartDay=objDate.getStartDay(daysToStart);
					String webinarStartMonth=objDate.getStartMonth(daysToStart);
					//If webinar date falls in current month
					if(currMonth.equalsIgnoreCase(webinarStartMonth)){
						Log.info("Months are same in calendar & calculated:"+currMonth+" "+webinarStartMonth);
						objSchdWebinar.clickRequiredDate(webinarStartDay);
					}else{
						//If webinar date falls in upcoming months
						Log.info("Months are different in calendar & calculated:"+currMonth+" "+webinarStartMonth);
						objSchdWebinar.clickNextMonth(webinarStartMonth);
						objSchdWebinar.clickRequiredDate(webinarStartDay);
					}
					//Logic for date selector ends here
					objSchdWebinar.enterStartTime(startTime, startTimeAMPM);
					objSchdWebinar.enterEndTime(endTime, endTimeAMPM);
					objSchdWebinar.selectTimeZone(timeZone);
					objSchdWebinar.selectLanguage(language);
					objSchdWebinar.clickSchedule();
					retValue=true;
				}else{
					Log.info("Home Page not loaded properly");
					retValue=false;
				}
			}else{
				Log.info("My Webinar Page not loaded properly");
				retValue=false;
			}
			return retValue;
		}catch(Exception e){
			Log.error("Exception in scheduleWebinar:",e);
			retValue=false;
			return retValue;
		}
	}

	/**
	 * This method verifies the details of the webinar created above.
	 * @param mapTestData
	 * @return
	 */
	public String verifyWebinarDetails(HashMap<String, String> mapTestData) {
		String retValue = "";
		try {
			String webinarTitle = mapTestData.get("Webinar Title");
			int daysToStart = Integer.parseInt(mapTestData
					.get("No Of Days to Start"));
			String startTime = mapTestData.get("Start Time");
			String startTimeAMPM = mapTestData.get("Start Time AMPM");
			String endTime = mapTestData.get("End Time");
			String endTimeAMPM = mapTestData.get("End Time AMPM");
			String webinarKey = null;
			String webinarDate = null;
			String webinarTime = null;
			ManageWebinarPage objManageWebinar = new ManageWebinarPage(driver);
			//Verify the details after the webinar is scheduled
			if (objManageWebinar.verifyManageWebinarPage()) {
				//get the unique key for webinar created
				webinarKey = objManageWebinar.getWebinarKey();
				//Go to my webinar page
				objManageWebinar.clickMyWebinars();
			} else {
				Log.info("Manage Webinar Page not loaded properly");
				retValue = "Manage Webinar Page not loaded properly";
			}
			//Verify if My webinar page is displayed
			MyWebinarPage myWebinar = new MyWebinarPage(driver);
			if (myWebinar.verifyMyWebinarPage()) {
				//Search and filter the webinar scheduled above
				myWebinar.searchWebinar(webinarTitle);
				//Get the Date & Time details from the application
				webinarDate = myWebinar.getWebinarDate(webinarKey);
				webinarTime = myWebinar.getWebinarTime(webinarKey);
				String dateMatchResp=this.verifyDateMatch(webinarDate, daysToStart);
				String timeMatchResp=this.verifyTimeMatch(startTime,startTimeAMPM,endTime,endTimeAMPM,webinarTime);
				Boolean isDateMatching=dateMatchResp.equalsIgnoreCase("success");
				Boolean isTimeMatching=timeMatchResp.equalsIgnoreCase("success");

				if (isDateMatching && isTimeMatching) {
					retValue = "success";
				}else if(!isDateMatching){
					retValue = dateMatchResp;
				}else if(!isTimeMatching){
					retValue = timeMatchResp;
				}
			} else {
				Log.info("My Webinar Page not loaded properly");
				retValue = "My Webinar Page not loaded properly";
			}
			return retValue;
		} catch (Exception e) {
			Log.error("Exception in scheduleWebinar:", e);
			retValue="Exception in scheduleWebinar";
			return retValue;
		}
	}

	/**
	 * To verify if the Webinar Month & Day are matching as scheduled by the user
	 * @param webinarDate
	 * @param daysToStart
	 * @return
	 */
	public String verifyDateMatch(String webinarDate, int daysToStart) {
		String returnMsg = "success";
		objDate = new DateFormats();
		String expectedStartMonth = objDate.getStartMonth(daysToStart).substring(0, 3);
		String expectedStartDay = objDate.getStartDay(daysToStart);
		String[] webinarDateDetails = this.getDayMonth(webinarDate);
		Log.info("Webinar Expected Start Month: "+expectedStartMonth);
		Log.info("Webinar Actual Start Month: "+webinarDateDetails[0]);
		Log.info("Webinar Expected Start Day: "+expectedStartDay);
		Log.info("Webinar Actual Start Day: "+webinarDateDetails[1]);
		
		if (!webinarDateDetails[0].equalsIgnoreCase(expectedStartMonth)) {
			returnMsg = "Month is not matching";
		}
		if (!webinarDateDetails[1].equalsIgnoreCase(expectedStartDay)) {
			returnMsg = "Day is not matching";
		}
		return returnMsg;

	}

	/**
	 * To verify if the Webinar Month & Day are matching as scheduled by the user
	 * @param startTime
	 * @param startTimeAMPM
	 * @param endTime
	 * @param endTimeAMPM
	 * @param webinarTime
	 * @return
	 */
	public String verifyTimeMatch(String startTime, String startTimeAMPM,
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
	
	/**
	 * This Method is used to parse Webinar Month & Day from the Webinar date in the application 
	 * @param webinarDate
	 * @return
	 */
	public String[] getDayMonth(String webinarDate) {
		String[] webinarDateDetails = new String[2];
		
		String pattern = "(^[A-Z][a-z][a-z])(,\\s)([A-Z][a-z][a-z])(\\s)([0-9]+)";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		// Now create matcher object.
		Matcher m = r.matcher(webinarDate);
		while (m.find()) {
			webinarDateDetails[0] = m.group(3);
			webinarDateDetails[1] = m.group(5);
		}
		return webinarDateDetails;
	}

	/**
	 * This Method is used to parse Webinar Time & Zone from the Webinar Time in the application 
	 * @param webinarTime
	 * @return
	 */
	public String[] getTimeAMPMZone(String webinarTime) {
		String[] webinarTimeDetails = new String[4];
		String pattern = "(^[0-9]+:[0-9][0-9])(\\s)([A-Z]M)(\\s-\\s)([0-9]+:[0-9][0-9])(\\s)([A-Z]M)";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(webinarTime);
		while (m.find()) {
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


}
