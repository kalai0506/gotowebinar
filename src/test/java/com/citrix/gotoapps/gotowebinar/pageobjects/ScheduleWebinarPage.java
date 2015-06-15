/**
 * @author Kalaiselvan Ulaganathan
 * @Date 06/15/2015
 * @Version 1.0
 * @Description This class contains page objects related to the Schedule Webinar page of the application
 **/
package com.citrix.gotoapps.gotowebinar.pageobjects;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ScheduleWebinarPage {
		final static Logger Log = Logger.getLogger(ScheduleWebinarPage.class.getName());
		//Web Elements By variables
		private WebDriver driver=null;
		private By webinarTitleLocator=By.id("name");
		private By descriptionLocator=By.id("description");
		private By startDatePickerLocator=By.xpath(".//*[@id='dateContainer_0']/div[1]/div/img");
		private By startDatePickerTitleLocator=By.xpath(".//*[@id='ui-datepicker-div']/h6");
		private By startDatePickerMonthLocator=By.className("ui-datepicker-month");
		private By nextMonthLinkLocator=By.linkText("Next");
		private By requiredDateLocator=null;
		private By startTimeLocator=By.id("webinarTimesForm.dateTimes_0.startTime");
		private By endTimeLocator=By.id("webinarTimesForm.dateTimes_0.endTime");
		private By startTimeAMPMArrowLocator=By.cssSelector("#webinarTimesForm_dateTimes_0_startAmPm_trig > span.arrow");
		private By startTimeAMPMLocator=By.cssSelector("#webinarTimesForm_dateTimes_0_startAmPm__menu > ul > li.ellipsis");
		private By endTimeAMPMLocator=By.cssSelector("#webinarTimesForm_dateTimes_0_endAmPm__menu > ul > li.ellipsis");
		private By endTimeAMPMArrowLocator=By.cssSelector("#webinarTimesForm_dateTimes_0_endAmPm_trig > span.arrow");
		private By webinarTimeZoneArrowLocator=By.xpath(".//*[@id='webinarTimesForm_timeZone_trig']/span[1]");
		private By webinarTimeZoneLocator=By.cssSelector("#webinarTimesForm_timeZone__menu > ul > li.ellipsis");
		private By webinarLanguageArrowLocator=By.xpath(".//*[@id='language_trig']/span[2]");
		private By webinarLanguageLocator=By.cssSelector("#language__menu > ul > li.ellipsis");
		private By scheduleLocator=By.id("schedule.submit.button");
		private WebDriverWait webWait=null;
		private List<WebElement> listElements=null;
		
		public ScheduleWebinarPage(WebDriver driver){
			this.driver=driver;
		}
		/**
		 * Verifies if the Schedule Webinar page is rendered
		 * @return
		 */
		public Boolean verifyScheduleWebinarPage(){
			webWait=new WebDriverWait(driver,20);
			webWait.until(ExpectedConditions.titleIs("Schedule a Webinar"));
			String pageTitle=driver.getTitle();
			Log.info("Page Title is: "+pageTitle);
			if(pageTitle.equals("Schedule a Webinar")){
				return true;
			}else{
				return false;
			}
		}
		
		public void enterWebinarTitle(String webinarTitle){
			Log.info("New Webinar Title: "+webinarTitle);
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(webinarTitleLocator));
			WebElement txtWebinarTitle=driver.findElement(webinarTitleLocator);
			txtWebinarTitle.clear();
			txtWebinarTitle.sendKeys(webinarTitle);
		}
		
		public void enterDescription(String description){
			WebElement txtareaDescription=driver.findElement(descriptionLocator);
			txtareaDescription.clear();
			txtareaDescription.sendKeys(description);
		}
		
		public void openStartDatePicker(){
			WebElement txtWebinarTitle=driver.findElement(startDatePickerLocator);
			txtWebinarTitle.click();
		}
		
		public Boolean verifyStartDatePicker(){
			webWait=new WebDriverWait(driver,20);
			webWait.until(ExpectedConditions.presenceOfElementLocated(startDatePickerTitleLocator));
			String datePickerTitle=driver.findElement(startDatePickerTitleLocator).getText();
			Log.info("Date Picker Title is: "+datePickerTitle);
			if(datePickerTitle.equals("Choose a date:")){
				return true;
			}else{
				return false;
			}
		}
		
		public String getMonth(){
			String monthText=null;
			webWait=new WebDriverWait(driver,20);
			webWait.until(ExpectedConditions.presenceOfElementLocated(startDatePickerMonthLocator));
			WebElement currentMonth=driver.findElement(startDatePickerMonthLocator);
			monthText=currentMonth.getText();
			return monthText;
		}
		
		public void clickNextMonth(String expectedMonth){
			webWait=new WebDriverWait(driver,20);
			WebElement linkNextMonth=driver.findElement(nextMonthLinkLocator);
			String currMonth=this.getMonth();
			Log.info("Current Month inside clickNextMonth method: "+currMonth);
			int i=1;
			while(!currMonth.equalsIgnoreCase(expectedMonth)){
				Log.info("Clicking next Month link"+i+"times");
				webWait.until(ExpectedConditions.presenceOfElementLocated(nextMonthLinkLocator));
				linkNextMonth.click();
				i++;
				currMonth=this.getMonth();
			}
		}
		
		public void clickRequiredDate(String txtDay){
			requiredDateLocator=By.linkText(txtDay);
			webWait=new WebDriverWait(driver,20);
			webWait.until(ExpectedConditions.presenceOfElementLocated(requiredDateLocator));
			WebElement linkDate=driver.findElement(requiredDateLocator);
			linkDate.click();
		}
		
		public void enterStartTime(String startTime,String AMPM){
			WebElement txtStartTime=driver.findElement(startTimeLocator);
			String bkSpace=Keys.BACK_SPACE.toString();
			txtStartTime.sendKeys(bkSpace+bkSpace+bkSpace+bkSpace);
			txtStartTime.sendKeys(startTime);
			this.selectAMPM(startTimeAMPMArrowLocator,startTimeAMPMLocator, AMPM);
		}
		
		public void enterEndTime(String endTime,String AMPM){
			WebElement txtEndTime=driver.findElement(endTimeLocator);
			String bkSpace=Keys.BACK_SPACE.toString();
			txtEndTime.sendKeys(bkSpace+bkSpace+bkSpace+bkSpace);
			txtEndTime.sendKeys(endTime);
			this.selectAMPM(endTimeAMPMArrowLocator,endTimeAMPMLocator, AMPM);
		}
		
		public void selectAMPM(By arrowLocator,By itemLocator,String AMPM){
			WebElement AMPMselector=driver.findElement(arrowLocator);
			AMPMselector.click();
			listElements=driver.findElements(itemLocator);
			Log.info("Total List Items="+listElements.size());
			for(WebElement li:listElements){
				String valueSelected=li.getAttribute("title");
				Log.info("Current List Item="+valueSelected);
				if(valueSelected.equalsIgnoreCase(AMPM)){
					Log.info("Current List Item is selected="+AMPM);
					li.click();
					break;
				}
			}
		}
		
		public void selectTimeZone(String timeZone){
			WebElement timeZoneselector=driver.findElement(webinarTimeZoneArrowLocator);
			timeZoneselector.click();
			listElements=driver.findElements(webinarTimeZoneLocator);
			Log.info("Total List Items="+listElements.size());
			for(WebElement li:listElements){
				String valueSelected=li.getAttribute("title");
				//Log.info("Titale attribute"+li.getAttribute("title"));
				Log.info("Current List Item="+valueSelected);
				if(valueSelected.equalsIgnoreCase(timeZone)){
					Log.info("Current List Item is selected="+timeZone);
					webWait=new WebDriverWait(driver,30);
					webWait.until(ExpectedConditions.elementToBeClickable(li));
					try{
						li.click();
					}catch(Exception e){
						Log.info("Inside Catch Exception",e);
						   int elementPosition = li.getLocation().getY();
						   String js = String.format("window.scroll(0, %s)", elementPosition);
						   ((JavascriptExecutor)driver).executeScript(js);
						   li.click();
					}
					break;
				}
			}
			/*for(int i=1;i<=(listElements.size());i++){
				WebElement listItem=driver.findElement(By.xpath("//*[@id='webinarTimesForm_timeZone__menu']/ul/li["+i+"]"));
				String valueSelected=listItem.getAttribute("title");
				if(valueSelected.equalsIgnoreCase(timeZone)){
					Log.info("Current List Item is selected="+timeZone);
					webWait=new WebDriverWait(driver,30);
					try{
					webWait.until(ExpectedConditions.elementToBeClickable(listItem));
					listItem.click();
					}catch(Exception e){
						Log.info("Inside Catch Exception",e);
						   int elementPosition = listItem.getLocation().getY();
						   String js = String.format("window.scroll(0, %s)", elementPosition);
						   ((JavascriptExecutor)driver).executeScript(js);
						   listItem.click();
					}
					break;
				}
			}*/
		}
		
		public void selectLanguage(String language){
			WebElement languageSelector=driver.findElement(webinarLanguageArrowLocator);
			languageSelector.click();
			listElements=driver.findElements(webinarLanguageLocator);
			Log.info("Total List Items="+listElements.size());
			for(WebElement li:listElements){
				String valueSelected=li.getAttribute("title");
				//Log.info("Titale attribute"+li.getAttribute("title"));
				Log.info("Current List Item="+valueSelected);
				if(valueSelected.equalsIgnoreCase(language)){
					Log.info("Current List Item is selected="+language);
					li.click();
					break;
				}
			}
		}
		
		public void clickSchedule(){
			WebElement btnSchedule=driver.findElement(scheduleLocator);
			btnSchedule.click();
		}
		
}

