package com.boomerang.canvas.PPIscoreboarddata;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.boomerang.canvas.genericlibrary.Genericlib;
import com.boomerang.canvas.headerwidget.HeaderwidgetActions;
import com.boomerang.canvas.login.LoginPageActions;
import com.boomerang.canvas.test.listeners.ScreenshotListener;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;

public class PPIscoreboardwidgetTestcases extends Testbase implements ITestListener{
	
	LoginPageActions loginPage=new LoginPageActions();
	Genericlib gl=new Genericlib();
	HeaderwidgetActions hd=new HeaderwidgetActions();
	PPIscoreboardWidgetActions sb=new PPIscoreboardWidgetActions();
	ScreenshotListener sc=new ScreenshotListener();
	PPIscoreboardwidgetapi sapi=new PPIscoreboardwidgetapi();
	 
	 String headerwidgetfile = "/src/com/boomerang/canvas/testoutput/PPIScoreboardWidget.json";

	 
	@BeforeSuite
	public void login() throws Exception{
			 if(driver !=null){
				 driver.quit();
				 }
					Testbase tb=new Testbase();
					tb.getBrowser(System.getProperty("Browser"),System.getProperty("os.name"));
					driver.get(System.getProperty("ClientURL"));
					loginPage.login(System.getProperty("Username"), System.getProperty("Password"), "");			
					sapi.getHeaderdata_from_api(headerwidgetfile,"getPPIScorecardDataForDimension","PPIScoreboard");
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
	
	// ######################### Scoreboard Test cases #####################################
	//####################### Margin Test cases #########################################
	@Parameters({"startdate","enddate"})
	@Test(description= "Sending the strat and end dates to get the dashbaord related data")
		  public void sentDates(String startdate,String enddate) throws Exception {
			Reporter.log("======================set the dates to get the details======================",true);
			hd.sentDates(startdate, enddate);
		    }	
		
	@Parameters({"text"})
	@Test(description= "Verifying the PPIScore text is present in Scoreboard Widget or not")
	  public void PPITextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard PPITextPresent text ======================",true);
		String margintext = sb.PPITextPresent();
		test.log(LogStatus.INFO,"","Actual data :" + margintext);
		test.log(LogStatus.INFO,"","Expected data :" + text);
		Assert.assertEquals(margintext, text);
	    }
	@Test(description= "Verifying the PPIAvgvalue value from Scoreboard Widget across Margin value which is comming from Backend")
	  public void PPIAvgvalue() throws Exception {
		Reporter.log("======================get the scoreboard margin value======================",true);
		String marginvalue = sb.PPIAvgvalue();
		test.log(LogStatus.INFO,"", "Actual value : " + marginvalue);
		Double respop= gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1);
		Assert.assertEquals(marginvalue, "$"+resrevenue1);
	    }
	@Test(description= "Verifying the getavgpvpvalue YOY value from Scoreboard Widget across Margin YOY value which is comming from Backend")
	  public void getavgyoyvalue() throws Exception {
		Reporter.log("======================get the scoreboard margin yoy value======================",true);
		String yoy = sb.getavgyoyvalue();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getavgpvpvalue() throws Exception {
		Reporter.log("======================get the scoreboard margin pvp value======================",true);
		String pvp = sb.getavgpvpvalue();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatornames() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitatornames ======================",true);
		List<WebElement> pvp = sb.getallcompitatornames();
		sapi.getdata(pvp,headerwidgetfile,"DIMENSION","competitor");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatorppivalues() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitator ppi values ======================",true);
		List<WebElement> pvp = sb.getallcompitatorppivalues();
		sapi.getdata(pvp,headerwidgetfile,"DIMENSION","competitor");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatoryoyvalues() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitator yoy values ======================",true);
		List<WebElement> pvp = sb.getallcompitatoryoyvalues();
		sapi.getIntdata(pvp,headerwidgetfile,"YOY","YOY_PPI");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatorpvpvalues() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitator pvp values ======================",true);
		List<WebElement> pvp = sb.getallcompitatorpvpvalues();
		sapi.getIntdata(pvp,headerwidgetfile,"PVP","PVP_PPI");
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

