package com.citrix.gotoapps.gotowebinar.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import java.util.HashMap;


public class XMLUtility {
	public String value;
	final static Logger Log = Logger.getLogger(XMLUtility.class.getName());

	public HashMap<String,String> getXMLData() throws Exception {
		String appURL="";
		String testDataPath="";
		String browser="";
		String chromedriverpath="";

		HashMap<String,String> hashMapEnvVariables = new HashMap<String, String>();
		hashMapEnvVariables.clear();
		try {
			File envVarXMLFile = new File("TestData//envconfig.xml"); // XML file path
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(envVarXMLFile);
			doc.getDocumentElement().normalize();
			
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("envconfig");
			
			for (int temp = 0; temp < nList.getLength(); temp++) {
				 
				Node nNode = nList.item(temp);
		 
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					//firefoxPath=eElement.getElementsByTagName("FireFoxPath").item(0).getTextContent();
					//System.out.println("firefoxPath : " + firefoxPath);
					browser=eElement.getElementsByTagName("browser").item(0).getTextContent();
					appURL=eElement.getElementsByTagName("appURL").item(0).getTextContent();
					System.out.println("appURL : " + appURL);
					testDataPath=eElement.getElementsByTagName("testdatapath").item(0).getTextContent();
					chromedriverpath=eElement.getElementsByTagName("chromedriver").item(0).getTextContent();
					hashMapEnvVariables.put("browser", browser);
					hashMapEnvVariables.put("appURL", appURL);
					hashMapEnvVariables.put("testDataPath", testDataPath);
					hashMapEnvVariables.put("chromeDriverPath", chromedriverpath);
				}
			}
		} catch (FileNotFoundException e) {
			Log.info(e);
			e.printStackTrace();
		} catch (IOException e) {
			Log.info(e);
			e.printStackTrace();
		}catch (Exception e) {
			Log.info(e);
			e.printStackTrace();
		}
		return hashMapEnvVariables;
	}
}