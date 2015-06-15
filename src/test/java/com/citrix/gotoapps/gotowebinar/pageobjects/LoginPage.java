package com.citrix.gotoapps.gotowebinar.pageobjects;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.citrix.gotoapps.gotowebinar.functionlibrary.UserAuthentication;

public class LoginPage {
		final static Logger Log = Logger.getLogger(UserAuthentication.class.getName());

		private WebDriver driver=null;
		private By emailIDLocator=By.id("emailAddress");
		private By passwordLocator=By.id("password");
		private By signInLocator=By.id("submit");
		private By logOutLocator=By.linkText("Log Out");
		private WebDriverWait webWait=null;
		
		public LoginPage(WebDriver driver){
			this.driver=driver;
		}
		
		public Boolean verifyLoginPage(){
			webWait=new WebDriverWait(driver,20);
			webWait.until(ExpectedConditions.titleIs("Citrix Secure Sign In"));
			String pageTitle=driver.getTitle();
			Log.info("Page Title is: "+pageTitle);
			if(pageTitle.equals("Citrix Secure Sign In")){
				return true;
			}else{
				return false;
			}
		}
		
		public void enterEmailAddress(String emailID){
			Log.info("Email Address: "+emailID);
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(emailIDLocator));
			WebElement txtEmailID=driver.findElement(emailIDLocator);
			txtEmailID.clear();
			txtEmailID.sendKeys(emailID);
		}
		
		
		public void enterPassword(String password){
			WebElement txtEmailID=driver.findElement(passwordLocator);
			txtEmailID.clear();
			txtEmailID.sendKeys(password);
		}
		
		public void clickSignIn(){
			WebElement btnSignIn=driver.findElement(signInLocator);
			btnSignIn.click();
			Log.info("Sign In button clicked");
		}
		
		public void clickLogOut(){
			try{
			webWait=new WebDriverWait(driver,10);
			webWait.until(ExpectedConditions.presenceOfElementLocated(logOutLocator));
			WebElement logOutElement=driver.findElement(logOutLocator);
			logOutElement.click();
			}catch(Exception e){
				Log.error(e);
			}
		}
		
}

