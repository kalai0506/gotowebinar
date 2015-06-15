package com.citrix.gotoapps.gotowebinar.pageobjects;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManageWebinarPage {
		final static Logger Log = Logger.getLogger(ScheduleWebinarPage.class.getName());

		private WebDriver driver=null;
		private By webinarKeyLocator=By.className("webinar-data");
		private By myWebinarLocator=By.linkText("My Webinars");
		private WebDriverWait webWait=null;
		
		public ManageWebinarPage(WebDriver driver){
			this.driver=driver;
		}
		
		public Boolean verifyManageWebinarPage(){
			webWait=new WebDriverWait(driver,20);
			webWait.until(ExpectedConditions.titleIs("Manage Webinar"));
			String pageTitle=driver.getTitle();
			Log.info("Page Title is: "+pageTitle);
			if(pageTitle.equals("Manage Webinar")){
				return true;
			}else{
				return false;
			}
		}
		
		public String getWebinarKey(){
			WebElement txtWebinarKey=driver.findElement(webinarKeyLocator);
			String webinarID=txtWebinarKey.getAttribute("data-webinarkey");
			Log.info("webinarID"+webinarID);
			return webinarID;
		}
		
		public Boolean clickMyWebinars(){
			try{
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(myWebinarLocator));
			WebElement linkScheduleWebinar=driver.findElement(myWebinarLocator);
			linkScheduleWebinar.click();
			return true;
			}catch(Exception e){
				Log.error(e);
				return false;
			}
		}
		
		
		
}


