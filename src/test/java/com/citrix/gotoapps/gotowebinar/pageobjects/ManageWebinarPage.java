/**
 * @author Kalaiselvan Ulaganathan
 * @Date 06/15/2015
 * @Version 1.0
 * @Description This class contains page objects related to the Manage Webinar page of the application
 **/

package com.citrix.gotoapps.gotowebinar.pageobjects;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ManageWebinarPage {
		final static Logger Log = Logger.getLogger(ManageWebinarPage.class.getName());

		private WebDriver driver=null;
		private By webinarKeyLocator=By.className("webinar-data");
		private By myWebinarLocator=By.linkText("My Webinars");
		private WebDriverWait webWait=null;
		
		public ManageWebinarPage(WebDriver driver){
			this.driver=driver;
		}
		
		/**
		 * To check if the Manage Webinar page is displayed
		 * @return true if Manage Webinar page is displayed
		 */
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
		
		/**
		 * To get Unique ID of every Webinar
		 * @return
		 */
		public String getWebinarKey(){
			WebElement txtWebinarKey=driver.findElement(webinarKeyLocator);
			String webinarID=txtWebinarKey.getAttribute("data-webinarkey");
			Log.info("webinarID"+webinarID);
			return webinarID;
		}
		
		/**
		 * To click My Webinars menu item
		 * @return
		 */
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


