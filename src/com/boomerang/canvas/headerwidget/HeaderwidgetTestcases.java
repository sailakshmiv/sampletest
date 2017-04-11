package com.boomerang.canvas.headerwidget;

import java.io.IOException;
import java.lang.reflect.Method;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.boomerang.canvas.genericlibrary.Genericlib;
import com.boomerang.canvas.login.LoginPageActions;
import com.boomerang.canvas.test.listeners.ScreenshotListener;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;

public class HeaderwidgetTestcases extends Testbase implements ITestListener{
	LoginPageActions loginPage=new LoginPageActions();
	Genericlib gl=new Genericlib();
	HeaderwidgetActions hd=new HeaderwidgetActions();
	ScreenshotListener sc=new ScreenshotListener();
	 
	 String headerwidgetfile = "/src/com/boomerang/canvas/testoutput/HeaderandScoreboard.json";

	 @BeforeSuite
	 public void login() throws Exception{
		 if(driver !=null){
			 driver.quit();
			 }
				Testbase tb=new Testbase();
				tb.getBrowser(System.getProperty("Browser"),System.getProperty("os.name"));
				driver.get(System.getProperty("ClientURL"));
				loginPage.login(System.getProperty("Username"), System.getProperty("Password"), "");			
	 }
	 
	@Parameters({"suiteName","testcase"})
	@BeforeMethod
	 public void createreport(ITestContext arg0,String suiteName,String testcase) throws IOException{
		try{
		test = report.startTest(testcase);
	    test.assignCategory(suiteName);
		}
		catch(Exception e){
		e.printStackTrace();
		}
	 }
	@Parameters({"suiteName","testcase"})
	@AfterMethod
		public void screenshot(ITestResult arg0,String suiteName,Method method,String testcase) throws InterruptedException, IOException {
		String screenshotname =suiteName+testcase; 
		try {
			
		     String screenshot = sc.takeScreenshot(arg0,screenshotname);
			 System.setProperty("org.uncommons.reportng.escape-output", "false");
			   Reporter.setCurrentTestResult(arg0); //// output gets lost without this entry
				  Reporter.log(
				  "<a title= \"title\" href=\"" + screenshot + "\">" +
				 "<img width=\"700\" height=\"550\" src=\"" + screenshot +
				  "\"></a>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Test test1 = method.getAnnotation(Test.class);
        if (test1 == null) {
            return;
        }
		int result=arg0.getStatus();
	      String testcase1 =suiteName+testcase.toString(); 
	     if(result ==1){
	      prop.put(testcase1, "PASS");
	      test.log(LogStatus.PASS, testcase+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png"),test1.description());
	     }else if(result ==2){
	      prop.put(testcase1, "FAIL"); 
	      test.log(LogStatus.FAIL, testcase+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png")+test1.description(),arg0.getThrowable());
	     }else if(result ==3){
	      prop.put(testcase1, "SKIP"); 
	      test.log(LogStatus.SKIP, testcase+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png")+test1.description(),arg0.getThrowable());
	     }
		report.endTest(test);
		report.flush();	
		}
	
	
	// ######################### Headerwidget Test cases #####################################
	
	@Parameters({"startdate","enddate"})
	@Test(description= "Sending the strat and end dates to get the dashbaord related data")
	  public void sentDates(String startdate,String enddate) throws Exception {
		Reporter.log("======================set the dates to get the details======================",true);
		hd.sentDates(startdate, enddate);
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Revenue text is present in header widget or not")
	  public void isHeaderRevenueTextPresent(String text) throws Exception {
		Reporter.log("======================get the revnue text======================",true);
		String revenuevalue = hd.getRevenueText();
		test.log(LogStatus.INFO,"","Actual value : " + revenuevalue);
		test.log(LogStatus.INFO,"","Expected value : " + text);
		Assert.assertEquals(revenuevalue, text);
	    }
	@Test(description= "Verifying the Revenue value from Header Widget across Revenue value which is comming from Backend")
	  public void getRevenueValueInDollars() throws Exception {
		Reporter.log("======================get the revnue value======================",true);
		String revenuevalue = hd.getRevenueValueInDollars();
		test.log(LogStatus.INFO,"", "Actual value : " + revenuevalue);
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","REVENUE");
		String resrevenue1=gl.truncate(respop/1000000);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1 +"M");
		Assert.assertEquals(revenuevalue, "$"+resrevenue1 +"M");
	    }
	@Test(description= "Verifying the Revenue YOY value from Header Widget across Revenue YOY value which is comming from Backend")
	  public void getRevenuePercentYoY() throws Exception {
		Reporter.log("======================get the revnue yoy value======================",true);
		String yoy = hd.getRevenuePercentYoY();
		gl.getYOYandPVPvalues(headerwidgetfile,yoy,"YOY","YOY_REVENUE");
	    }
	@Test(description= "Verifying the Revenue PVP value from Header Widget across Revenue PVP value which is comming from Backend")
	  public void getRevenuePercentPvP() throws Exception {
		Reporter.log("======================get the revnue pvp value======================",true);
		String pvp=hd.getRevenuePercentPvP();
		gl.getYOYandPVPvalues(headerwidgetfile,pvp,"PVP","PVP_REVENUE");
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Margin text is present in header widget or not")
	  public void isHeaderMarginTextPresent(String text) throws Exception {
		Reporter.log("======================get the margin text======================",true);
		String revenuevalue = hd.getMaginText();
		test.log(LogStatus.INFO,"", "Actual value : " + revenuevalue);
		test.log(LogStatus.INFO,"", "Expected value : " + text);
		Assert.assertEquals(revenuevalue, text);
	    }
	@Test(description= "Verifying the Margin value from Header Widget across Margin value which is comming from Backend")
	  public void getMarginValueInDollars() throws Exception {
		Reporter.log("======================get the margin value======================",true);
		String marginvalue = hd.getMarginValueInDollars();
		test.log(LogStatus.INFO,"", "Actual value : " + marginvalue);
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.truncate(respop/1000000);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1 +"M");
		Assert.assertEquals(marginvalue, "$"+resrevenue1 +"M");
	    }

	@Test(description= "Verifying the Margin YOY value from Header Widget across Margin YOY value which is comming from Backend")
	  public void getMarginPercentYoY() throws Exception {
		Reporter.log("======================get the margin yoy value======================",true);
		String yoy = hd.getMarginPercentYoY();
		gl.getYOYandPVPvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	    }
	@Test(description= "Verifying the Margin PVP value from Header Widget across Margin PVP value which is comming from Backend")
	  public void getMarginPercentPvP() throws Exception {
		Reporter.log("======================get the margin pvp value======================",true);
		String pvp = hd.getMarginPercentPvP();
		gl.getYOYandPVPvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
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

