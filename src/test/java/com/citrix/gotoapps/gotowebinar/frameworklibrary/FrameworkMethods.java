/**
 * @author Kalaiselvan Ulaganathan
 * @version 1.0
 * @since 06/15/2015
 * @Description This class is used to provide re-usable framework level methods.
 */

package com.citrix.gotoapps.gotowebinar.frameworklibrary;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class FrameworkMethods {
	protected WebDriver driver=null;

	/**
	 * This method creates web driver instance based on the browser selected
	 * @param browser
	 * @param chromeDriverExe ChromeDriver.exe file location
	 * @param firefoxPath Firefox browser exe file location
	 * @return Web driver instance
	 * @throws Exception
	 */
	public WebDriver getWebDriver(String browser, String chromeDriverExe,String firefoxPath) throws Exception{
		if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "misc/chromedriver");
			driver=new ChromeDriver();
		}else if(browser.equalsIgnoreCase("htmlunit")){
			driver=new HtmlUnitDriver();
		}else {
			System.setProperty("webdriver.firefox.bin", firefoxPath);
			driver=new FirefoxDriver();
		}
		return driver;
	}
	
	/**
	 * This is a resuable framework function to take screenshots on test failure
	 * @param driver
	 * @param testCaseID
	 * @throws Exception
	 */
	public void takeScreenShot(WebDriver driver,String testCaseID) throws Exception{
		try{
            //take screenshot and save it in a file
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            //copy the file to the required path
            FileUtils.copyFile(screenshot,new File("Screenshots\\"+testCaseID+".jpeg"));

        }catch(Exception e){
            //if it fails to take screenshot then this block will execute
            System.out.println("Failure to take screenshot "+e);
        }

	}
}
