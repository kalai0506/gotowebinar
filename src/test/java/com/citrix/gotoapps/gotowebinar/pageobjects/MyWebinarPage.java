/**
 * @author Kalaiselvan Ulaganathan
 * @Date 06/15/2015
 * @Version 1.0
 * @Description This class contains page objects related to the My Webinar page of the application
 **/

package com.citrix.gotoapps.gotowebinar.pageobjects;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MyWebinarPage {
		private WebDriver driver=null;
		private By myWebinarLocator=By.linkText("My Webinars");
		private By scheduleWebinarLocator=By.linkText("Schedule a Webinar");
		private By searchWebinarLocator=By.id("upcomingWebinar-searchWebinarSearchBox");
		private WebDriverWait webWait=null;
		final static Logger Log = Logger.getLogger(MyWebinarPage.class.getName());

		public MyWebinarPage(WebDriver driver){
			this.driver=driver;
		}
		
		public Boolean verifyMyWebinarPage(){
			try{
			webWait=new WebDriverWait(driver,30);
			webWait.until(ExpectedConditions.presenceOfElementLocated(myWebinarLocator));
			String pageTitle=driver.getTitle();
			Log.info("Page Title is: "+pageTitle);
			if(pageTitle.equalsIgnoreCase("My Webinars")){
				Log.info("My Webinar Page Title is same as Expected: My Webinars Actual: "+pageTitle);
				return true;
			}else{
				Log.info("My Webinar Page Title is different.Expected: My Webinars Actual: "+pageTitle);
				return false;
			}
			}catch(Exception e){
				Log.error(e);
				return false;
			}
		}
		
		public Boolean clickScheduleWebinar(){
			try{
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(scheduleWebinarLocator));
			WebElement linkScheduleWebinar=driver.findElement(scheduleWebinarLocator);
			linkScheduleWebinar.click();
			return true;
			}catch(Exception e){
				Log.error(e);
				return false;
			}
		}
		
		public void searchWebinar(String pageTitle){
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(searchWebinarLocator));
			WebElement searchBox=driver.findElement(searchWebinarLocator);
			searchBox.clear();
			searchBox.sendKeys(pageTitle);
			searchBox.sendKeys(Keys.ENTER);
		}
		
		public String  getWebinarDate(String webinarKey){
			String locaterKey=".//*[@id='webinar-"+webinarKey+"\']/ul[1]/li[2]/span";
			By webinarDateLocator=By.xpath(locaterKey);
			webWait=new WebDriverWait(driver,60);
			webWait.until(ExpectedConditions.presenceOfElementLocated(webinarDateLocator));
			WebElement webinarDateElement=driver.findElement(webinarDateLocator);
			String webinarDate=webinarDateElement.getText();
			Log.info("Webinar Date in Application:"+webinarDate);
			return webinarDate;
		}
		
		public String  getWebinarTime(String webinarKey){
			String locaterKey=".//*[@id='webinar-"+webinarKey+"\']/ul[2]/div[1]/li[2]/span";
			By webinarDateLocator=By.xpath(locaterKey);
			webWait=new WebDriverWait(driver,60);
			webWait.until(ExpectedConditions.presenceOfElementLocated(webinarDateLocator));
			WebElement webinarDateElement=driver.findElement(webinarDateLocator);
			String webinarTime=webinarDateElement.getText();
			Log.info("Webinar Time in Application:"+webinarTime);
			return webinarTime;
		}
		
		
}

