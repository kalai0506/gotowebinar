/**
 * @author Kalaiselvan Ulaganathan
 * @Date 06/15/2015
 * @Version 1.0
 * @Description This class contains page objects related to the Home/Main page of the application
 **/

package com.citrix.gotoapps.gotowebinar.pageobjects;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
		private WebDriver driver=null;
		private By signInLocator=By.linkText("Sign In");
		private WebDriverWait webWait=null;
		final static Logger Log = Logger.getLogger(HomePage.class.getName());

		public HomePage(WebDriver driver){
			this.driver=driver;
		}
		/**
		 * To check if the current page is Home Page of the application
		 * @return true if the title matches
		 */
		public Boolean verifyHomePage(){
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(signInLocator));
			String pageTitle=driver.getTitle();
			Log.info("Page Title is: "+pageTitle);
			if(pageTitle.equals("Webinar & Online Conference | GoToWebinar")){
				return true;
			}else{
				return false;
			}

		}
		
		/**
		 * To click the Sign in link in the application Home Page
		 */
		public void clickSignIn(){
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(signInLocator));
			WebElement linkSignIn=driver.findElement(signInLocator);
			linkSignIn.click();
		}
}
