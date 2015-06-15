/**
 * @author Kalaiselvan Ulaganathan
 * @Date 06/15/2015
 * @Version 1.0
 * @Description This is the driver script for the test suite. Web driver instance and other resources are
 *              initiated here
 **/
package com.citrix.gotoapps.gotowebinar.testscripts;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.citrix.gotoapps.gotowebinar.frameworklibrary.FrameworkMethods;
import com.citrix.gotoapps.gotowebinar.functionlibrary.UserAuthentication;
import com.citrix.gotoapps.gotowebinar.utility.XMLUtility;

public class DriverTest {
	//Web driver Instance used in the test suite
	protected static WebDriver driver; 
	final static Logger Log = Logger.getLogger(DriverTest.class.getName());
	protected static String testDataPath="";
	private UserAuthentication lgn = null;
	
	 /**
	 * To initiate the test suite by setting Environment variables, creating web driver instance and
	 * launching browser to start test execution. 
	 * @throws Exception - Handle any exception
	 */
	@BeforeSuite
	public void invokeBrowser() throws Exception {
		Log.info("Test suite execution starts now");
		XMLUtility xmlUtil=new XMLUtility();
		HashMap<String,String> envXMLVariables=xmlUtil.getXMLData();
		String browser=envXMLVariables.get("browser");
		String appURL=envXMLVariables.get("appURL");
		testDataPath=envXMLVariables.get("testDataPath");
		FrameworkMethods objFrmWrk=new FrameworkMethods();
		driver = objFrmWrk.getWebDriver(browser);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);	
		driver.get(appURL);
		driver.manage().window().maximize();
		DOMConfigurator.configure("resources//log4j.xml");
	}
	
	/**
	 * User Authentication before the test begin as the same user is used in all tests
	 * @throws Exception
	 */
	@BeforeTest
	public void login() throws Exception{
		lgn = new UserAuthentication(driver);
		Assert.assertEquals(lgn.login(testDataPath),"User Logged in","User login failed");
	}
	
	/**
	 * This method is used to gracefully exit the test execution by signing out of the 
	 * application and closing the browser instance.
	 * @throws Exception the exception
	 */
	@AfterSuite
	public void testSuiteComplete() throws Exception {
		lgn.signOut();
		driver.close();
		driver.quit();
		Log.info("End of Test suite execution");
	}
}
