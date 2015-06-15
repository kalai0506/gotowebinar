/**
 * This class is used for User Authentication and sign out of the application.
 * @author Kalaiselvan Ulaganathan
 * @version 1.0
 * @since 06/15/2015
 */

package com.citrix.gotoapps.gotowebinar.functionlibrary;

import java.util.HashMap;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.citrix.gotoapps.gotowebinar.pageobjects.HomePage;
import com.citrix.gotoapps.gotowebinar.pageobjects.LoginPage;
import com.citrix.gotoapps.gotowebinar.utility.ExcelUtils;

public class UserAuthentication{
	final static Logger Log = Logger.getLogger(UserAuthentication.class.getName());
	private WebDriver driver=null;
	private LoginPage objLogin=null;
	
	public UserAuthentication(WebDriver driver){
		this.driver=driver;
	}
	
	public String login(String testDataPath) throws Exception {
		ExcelUtils excel=new ExcelUtils(testDataPath+ "//Login.xlsx","Sheet1");
		//Extract user authentication details from Login.xls in test data folder
		HashMap<String, String> mapLogin = excel.getExcelData("login1");
		String userName = (String) mapLogin.get("UserName");
		Log.info("UserName"+userName);
		String password = (String) mapLogin.get("Password");
		Log.debug("Inside Login method: Username "+userName+" Password "+password);
		try {
			Log.debug("click enter secure button");
			HomePage objHome=new HomePage(driver);
			if(objHome.verifyHomePage()){
				objHome.clickSignIn();
				objLogin=new LoginPage(driver);
				if(objLogin.verifyLoginPage()){
					objLogin.enterEmailAddress(userName);
					objLogin.enterPassword(password);
					objLogin.clickSignIn();
				}else{
					Log.info("Login Page not loaded properly");
					throw new NoSuchElementException();
				}

			}else{
				Log.info("Home Page not loaded properly");
				throw new NoSuchElementException();
			}
			return "User Logged in";
		} catch (Exception e) {
			Log.error("User Login failure", e);
			return "User Login Failure";
		}
	}
		
	public void signOut() throws Exception {
		objLogin.clickLogOut();
	}
	

}
