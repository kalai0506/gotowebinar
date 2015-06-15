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

	public WebDriver getWebDriver(String browser) throws Exception{
		if(browser.equalsIgnoreCase("chrome")){
			System.setProperty("webdriver.chrome.driver", "misc/chromedriver");
			driver=new ChromeDriver();
		}else if(browser.equalsIgnoreCase("htmlunit")){
			driver=new HtmlUnitDriver();
		}else {
			//System.setProperty("webdriver.firefox.bin", firefoxPath);
			driver=new FirefoxDriver();
		}
		return driver;
	}
	
	public void takeScreenShot(WebDriver driver) throws Exception{
		try{
            //take screenshot and save it in a file
            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            //copy the file to the required path
            FileUtils.copyFile(screenshot,new File("C:\\SOSA\\Selenium\\TripartyDeal\\Screenshots\\screenshot.jpeg"));

        }catch(Exception e){
            //if it fails to take screenshot then this block will execute
            System.out.println("Failure to take screenshot "+e);
        }

	}
	
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
