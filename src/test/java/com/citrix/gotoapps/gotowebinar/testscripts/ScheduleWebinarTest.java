/**
 * @author Kalaiselvan Ulaganathan
 * @Date 06/15/2015
 * @Version 1.0
 * @Description This is the test script to test the module Schedule webinar
 **/
package com.citrix.gotoapps.gotowebinar.testscripts;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.citrix.gotoapps.gotowebinar.frameworklibrary.FrameworkMethods;
import com.citrix.gotoapps.gotowebinar.functionlibrary.Webinar;
import com.citrix.gotoapps.gotowebinar.utility.ExcelUtils;

import java.util.HashMap;

public class ScheduleWebinarTest extends DriverTest {
	final static Logger Log = Logger.getLogger(ScheduleWebinarTest.class.getName());
	ExcelUtils excel = null;
	FrameworkMethods frmWrk=new FrameworkMethods();

	/**
	 * First test method. It asserts if the expected results not returned.
	 * @throws Exception
	 */
	@Test
	public void TC001() throws Exception {
		String testCaseID = "TC001";
		Log.info("Test case " + testCaseID + " execution starts now.");
		excel = new ExcelUtils(testDataPath + "TestData.xlsx", "Sheet1");
		// Extract test data from the excel
		HashMap<String, String> mapTestData = excel.getExcelData(testCaseID);
		String isRunEnabled = (String) mapTestData.get("Test Run");
		// Verify if this test case needs to be executed
		if (isRunEnabled.equalsIgnoreCase("N")) {
			// throw new TestException(isRunEnabled);
			Log.info("Test case " + testCaseID + " has been skipped.");
			throw new SkipException("Not Runnable");
		}
		Webinar objNewWebinar=new Webinar(driver);
		//Create a new webinar schedule
		Assert.assertTrue(objNewWebinar.scheduleWebinar(mapTestData));
		//View the webinar schedule & check if the details provided above are captured properly
		Assert.assertEquals(objNewWebinar.verifyWebinarDetails(mapTestData),"success","Webinar details not matched correctly");
	}


}
