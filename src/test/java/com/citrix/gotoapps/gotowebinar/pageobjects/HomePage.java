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
		public void clickSignIn(){
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(signInLocator));
			WebElement linkSignIn=driver.findElement(signInLocator);
			linkSignIn.click();
		}
}
