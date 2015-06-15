/**
 * <h1>Excel Utility</h1>
 * This class contains methods to write or read data from Excel Files in .XLSX format
 * @author adcxzhg
 * @version 1.0
 * @since 04/12/2015
 */

package com.citrix.gotoapps.gotowebinar.utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ExcelUtils {
	final  Logger Log = Logger.getLogger(ExcelUtils.class.getName());
	private XSSFSheet ExcelWSheet;
	private XSSFWorkbook ExcelWBook;
	private XSSFCell Cell;
	private String excelFilePath;
	private String excelSheetName;
	private HashMap<String, String> testDataFromExcel = new HashMap<String, String>();
	private FileInputStream excelFileIn=null;
	private FileOutputStream excelFileOut=null;
	
	/**
	 * Constructor to set the excel file & its sheet to be used
	 * @param excelFilePath
	 * @param excelSheetName
	 */
	public ExcelUtils(String excelFilePath,String excelSheetName){
		this.excelFilePath=excelFilePath;
		this.excelSheetName=excelSheetName;
	}
	

	/**
	 * This method is used to instantiate Excel workbook & sheet open for reading data
	 * @param None
	 * @throws Exception
	 */
	public void setExcelFile()
			throws Exception {
		try {
			// Open the Excel file
			excelFileIn = new FileInputStream(excelFilePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(excelFileIn);
			ExcelWSheet = ExcelWBook.getSheet(excelSheetName);
			Log.debug("Opened the Excel File "+excelFilePath+" Sheet "+excelSheetName);	
		} catch(FileNotFoundException e){
			Log.info("Issue in accessing file:"+e);
			throw (e);
		} catch (Exception e) {
			throw (e);
		}

	}


	
	/**
	 * This method is used to read data from the excel file
	 * @param testCaseName
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, String> getExcelData(String rowIdentifier) throws Exception {
		//System.out.println(excelFilePath);
		setExcelFile();

		//int totRowNum = ExcelWSheet.getLastRowNum(); //Total no of rows in the excel sheet
		//Log.info("Total No. of rows is: " + totRowNum);
		int totRowNum = ExcelWSheet.getPhysicalNumberOfRows();
		//totRowNum=totPhyRowNum;
		//Log.info("Total Phy No. of rows is: " + totPhyRowNum);
		int totColNum = ExcelWSheet.getRow(0).getLastCellNum();
		Log.debug("Total No. of cols is: " + totColNum);//Total no of cols in the excel sheet

		//Get the column names from the excel sheet & store in a temporary array 
		String[] arrTemp = new String[totColNum];
		for (int i = 0; i < totColNum; i++) {
			arrTemp[i] = getCellData(0, i); //assume that first row will always have the column names
			//Log.info("Column " + i + " is " + arrTemp[i]);
		}
		
		//Retrieve the value from excel
		for (int rowID = 1; rowID < totRowNum; rowID++) { //skipping the first row as it will has column names always.
			String testCaseNameInExcel = getCellData(rowID, 0); //Assume 1st cell will have the test case name in the excel sheet
			//Log.info("TC Name retrived from Excel: " + testCaseNameInExcel);
			//Retrieve the column values only from the row to which test case name matched.
			if (rowIdentifier.equals(testCaseNameInExcel)) {
				Log.debug("Test Case Name matched with Excel: " + testCaseNameInExcel);
				Log.debug("Printing the columns in the row matched: ");
				for (int colID = 0; colID < totColNum; colID++) {
					String cellData = getCellData(rowID, colID);
					testDataFromExcel.put(arrTemp[colID], cellData);
					Log.debug("Column Name: "+arrTemp[colID] + " Value: " + cellData);
				}
				break;
			}
		}
		excelFileIn.close();
		return testDataFromExcel;
	}
	
	/**
	 * This method is used to get Value from the Cell in Excel based on row and column values supplied
	 * @param RowNum
	 * @param ColNum
	 * @return
	 * @throws Exception
	 */
	public String getCellData(int RowNum, int ColNum) throws Exception {
		String cellData= null;
		try {

			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

			switch (Cell.getCellType()) {
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING:
                cellData = Cell.getStringCellValue();
                break;
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(Cell)) {
                	SimpleDateFormat form = new SimpleDateFormat("hh:mm");
                    cellData = form.format(Cell.getDateCellValue());
                } else {
                	Double tempValue=Cell.getNumericCellValue();
                    cellData=Integer.toString(tempValue.intValue());                   
                }
                break;
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_BOOLEAN:
                System.out.println(Cell.getBooleanCellValue());
                break;
            case org.apache.poi.ss.usermodel.Cell.CELL_TYPE_FORMULA:
                System.out.println(Cell.getCellFormula());
                break;
            default:
            	cellData="";
            	Log.debug("Cell Type is:"+Cell.getCellType()+" Value is "+cellData);
			}	
			Log.debug("Cell Type is:"+Cell.getCellType()+" Value is "+cellData);
		} catch (Exception e) {
			cellData="";
			return cellData;
		}
		return cellData;
	}

	/**
	 * Method to set data from the application in to the test data excel
	 * @param rowIdentifier
	 * @param colName
	 * @param cellData
	 * @return
	 * @throws Exception
	 */
	public String setExcelData(String rowIdentifier,String colName, String cellData) throws Exception {
		String dataSetStatus="Data setup success in excel";
		try{
			setExcelFile();		
			//int totRowNum = ExcelWSheet.getLastRowNum(); //Total no of rows in the excel sheet
			//Log.info("Total No. of rows is: " + totRowNum);
			int totRowNum = ExcelWSheet.getPhysicalNumberOfRows();
			//totRowNum=totPhyRowNum;
			//Log.info("Total Phy No. of rows is: " + totPhyRowNum);
			int totColNum = ExcelWSheet.getRow(0).getLastCellNum();
			Log.debug("Total No. of cols is: " + totColNum);//Total no of cols in the excel sheet
	
			//Get the column names from the excel sheet & store in a temporary array 
			String[] arrTemp = new String[totColNum];
			for (int i = 0; i < totColNum; i++) {
				arrTemp[i] = getCellData(0, i); //assume that first row will always have the column names
				//Log.info("Column " + i + " is " + arrTemp[i]);
			}
			
			//Retrieve the value from excel
			for (int rowID = 1; rowID < totRowNum; rowID++) { //skipping the first row as it will has column names always.
				String colNameInExcel = getCellData(rowID, 0); //Assume 1st cell will have the test case name in the excel sheet
				//Log.info("TC Name retrived from Excel: " + testCaseNameInExcel);
				//Retrieve the column values only from the row to which test case name matched.
				if (rowIdentifier.equals(colNameInExcel)) {
					Log.info("Test Case Name matched with Excel: " + colNameInExcel);
					Log.info("Printing the columns in the row matched: ");
					for (int colID = 0; colID < totColNum; colID++) {
						if(arrTemp[colID].equalsIgnoreCase(colName)){
							Log.info("Column Name matched with Excel: " + colName);
							dataSetStatus=setCellData(rowID, colID,cellData);
							Log.info("Column Name: "+arrTemp[colID] + " Value: " + cellData);
							break;
						}
						
					}
					break;
				}
			}
			excelFileIn.close();
			return dataSetStatus;
		}catch(Exception e){
			return "Issue in setting data in excel";
		}
		
	}
	

	/**
	 * This method is used to set data in to the excel file based on Row & column numbers
	 * @param RowNum
	 * @param ColNum
	 * @param cellData
	 * @return
	 * @throws FileNotFoundException
	 * @throws Exception
	 */
	public String setCellData(int RowNum, int ColNum,String cellData) throws FileNotFoundException,Exception {
		String dataSetStatus="Data setup success in excel";
		try {
			excelFileOut = new FileOutputStream(excelFilePath);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			Cell.setCellType(org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING);
			Cell.setCellValue(cellData);	
		    ExcelWBook.write(excelFileOut);
		    excelFileOut.close();
		} catch (FileNotFoundException e) {
			Log.error("Something failed"+e);
			dataSetStatus="Test data excel may be in use by other process or kept open";
		    return dataSetStatus;
		}catch (Exception e) {
			Log.error("Something failed"+e);
			dataSetStatus="Issue in setting data in excel";
		    return dataSetStatus;
		}
	    return dataSetStatus;
	}
}