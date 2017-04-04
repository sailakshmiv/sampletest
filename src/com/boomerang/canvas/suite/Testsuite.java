package com.boomerang.canvas.suite;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import com.boomerang.canvas.genericlibrary.XmltoJava;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Testsuite extends Testbase implements ITestListener{

	public static String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	public static ExtentReports report;
	public static ExtentTest test;
	
	List<XmlSuite> xmlSuites;
	
	public Testsuite() {
			xmlSuites = new ArrayList<XmlSuite>();
		}

	private void runTests(TestNG tng) throws JAXBException, Exception {
		XmltoJava xmltojava;
		xmltojava = (XmltoJava) com.boomerang.canvas.genericlibrary.GenericClass
				.unmarshallClass(workspace + "/"+ "config.xml", XmltoJava.class);
		for (int k = 0; k < xmltojava.getSuite().length; k++) {

			XmlSuite suite = new XmlSuite();

			ArrayList<XmlTest> tests = new ArrayList<XmlTest>();

			for (int l = 0; l < xmltojava.getSuite()[k].getParameter().length; l++) {

				Map<String, String> parameters = new HashMap<String, String>();

				suite.setName(xmltojava.getSuite()[k].getSuitename());
				List<XmlTest> xmlTest = new ArrayList<XmlTest>();
				XmlTest test = new XmlTest(suite);
				test.setName(xmltojava.getSuite()[k].getParameter()[l].getTestcase());

				parameters.put("suiteName", xmltojava.getSuite()[k].getSuitename());
				parameters.put("Testcase", xmltojava.getSuite()[k].getParameter()[l].getTestcase());
				parameters.put("username", xmltojava.getSuite()[k].getParameter()[l].getUsername());
				parameters.put("password", xmltojava.getSuite()[k].getParameter()[l].getPassword());
				parameters.put("actualerrormessage", xmltojava.getSuite()[k].getParameter()[l].getActualerrormessage());
				parameters.put("startdate", xmltojava.getSuite()[k].getParameter()[l].getStartdate());
				parameters.put("enddate", xmltojava.getSuite()[k].getParameter()[l].getEnddate());
				parameters.put("text", xmltojava.getSuite()[k].getParameter()[l].getText());
				
				test.setParameters(parameters);

				ArrayList<XmlInclude> methodsToRun = new ArrayList<XmlInclude>();
				ArrayList<XmlClass> classes1 = new ArrayList<XmlClass>();
				XmlClass classes = new XmlClass();

				classes.setName("com.boomerang.canvas.suite." + xmltojava.getSuite()[k].getClassname());

				methodsToRun.add(new XmlInclude(xmltojava.getSuite()[k].getParameter()[l].getTestcase()));
				classes.setIncludedMethods(methodsToRun);
				classes1.add(classes);
				test.setXmlClasses(classes1);
				xmlTest.add(test);
				tests.addAll(xmlTest);
			}
			suite.setTests(tests);

			xmlSuites.add(suite);
		}
		tng.setXmlSuites(xmlSuites);
		
		tng.run();
	}
	public void suitetest() throws Exception{
		report = new ExtentReports(workspace+"/test-output"+timeStamp+"/ExtendedReports.html");
		report.loadConfig(new File("extent-config.xml"));
		report.addSystemInfo("Environment","QA-Sanity"); 
		report.addSystemInfo("User Name","SAI"); 
		report.addSystemInfo("Build","1.1.0");
		Testsuite test;
		TestNG tng=new TestNG();
		test= new Testsuite();
			tng.setOutputDirectory("test-output"+timeStamp+"/");
		tng.addListener(test);
		test.runTests(tng);
	}
public static void main(String args[]) throws Exception{
		loadfile();
		Testsuite ts=new Testsuite();
		Testbase tb=new Testbase();
		tb.getBrowser(prop.getProperty("defaultBrowser"),prop.getProperty("defaultClient"),args[0]);
		driver.get(prop.getProperty("defaultClient"));
		ts.suitetest();
		driver.quit();
}

@Override
public void onTestStart(ITestResult result) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTestSuccess(ITestResult result) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTestFailure(ITestResult result) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTestSkipped(ITestResult result) {
	// TODO Auto-generated method stub
	
}

@Override
public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	// TODO Auto-generated method stub
	
}

@Override
public void onStart(ITestContext context) {
	// TODO Auto-generated method stub
	
}

@Override
public void onFinish(ITestContext context) {
	// TODO Auto-generated method stub
	
}
}