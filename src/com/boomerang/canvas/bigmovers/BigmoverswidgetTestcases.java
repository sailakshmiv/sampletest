package com.boomerang.canvas.scoreboard;

import java.io.IOException;
import java.lang.reflect.Method;
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

public class ScoreboardwidgetTestcases extends Testbase implements ITestListener{
	
	LoginPageActions loginPage=new LoginPageActions();
	Genericlib gl=new Genericlib();
	HeaderwidgetActions hd=new HeaderwidgetActions();
	ScoreboardWidgetActions sb=new ScoreboardWidgetActions();
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
	
	// ######################### Scoreboard Test cases #####################################
	//####################### Margin Test cases #########################################
	@Parameters({"startdate","enddate"})
	@Test(description= "Sending the strat and end dates to get the dashbaord related data")
		  public void sentDates(String startdate,String enddate) throws Exception {
			Reporter.log("======================set the dates to get the details======================",true);
			hd.sentDates(startdate, enddate);
		    }	
		
	@Parameters({"text"})
	@Test(description= "Verifying the Margin text is present in Scoreboard Widget or not")
	  public void isMarginTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard margin text ======================",true);
		String margintext = sb.MarginTextPresent();
		test.log(LogStatus.INFO,"","Actual data :" + margintext);
		test.log(LogStatus.INFO,"","Expected data :" + text);
		Assert.assertEquals(margintext, text);
	    }
	@Test(description= "Verifying the Margin value from Scoreboard Widget across Margin value which is comming from Backend")
	  public void getMarginValue() throws Exception {
		Reporter.log("======================get the scoreboard margin value======================",true);
		String marginvalue = sb.getMarginValue();
		test.log(LogStatus.INFO,"", "Actual value : " + marginvalue);
		Double respop= gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.truncate(respop/1000000);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1 +"M");
		Assert.assertEquals(marginvalue, "$"+resrevenue1 +"M");
	    }
	@Test(description= "Verifying the Margin YOY value from Scoreboard Widget across Margin YOY value which is comming from Backend")
	  public void getMarginYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard margin yoy value======================",true);
		String yoy = sb.getMarginYoYPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	    }
	@Test(description= "Verifying the Margin PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getMarginPVPPercent() throws Exception {
		Reporter.log("======================get the scoreboard margin pvp value======================",true);
		String pvp = sb.getMargingPvPPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
	    }
	
	//####################### Revenue Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Revenue text is present in Scoreboard Widget or not")
	  public void isRevenueTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard revnue text======================",true);
		String revenuetext = sb.isRevenueTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +revenuetext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(revenuetext, text);
	    }
	@Test(description= "Verifying the Revenue value from Scoreboard Widget across Revenue value which is comming from Backend")
	  public void getRevenueValue() throws Exception {
		Reporter.log("======================get the scoreboard revnue value======================",true);
		String revenue = sb.getRevenueValue();
		test.log(LogStatus.INFO,"","Actual data : " + revenue);
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","REVENUE");
		String resrevenue1=gl.truncate(respop/1000000);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1 +"M");
		Assert.assertEquals(revenue, "$"+resrevenue1 +"M");
	    }
	@Test(description= "Verifying the Revenue YOY value from Scoreboard Widget across Revenue YOY value which is comming from Backend")
	  public void getRevenueYoYPercent() throws Exception {
		Reporter.log("======================get the scoeboard revnue yoy value======================",true);
		String yoy = sb.getRevenueYoYPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,yoy,"YOY","YOY_REVENUE");
	    }
	@Test(description= "Verifying the Revenue PVP value from Scoreboard Widget across Revenue PVP value which is comming from Backend")
	  public void getRevenuePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard revnue pvp value======================",true);
		String pvp = sb.getRevenuePvPPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,pvp,"PVP","PVP_REVENUE");
	    }
	
	//####################### Unit Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Unit text is present in Scoreboard Widget or not")
	  public void isUnitsTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard unit text======================",true);
		String unittext = sb.isUnitsTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +unittext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(unittext, text);
	    }
	@Test(description= "Verifying the Units value from Scoreboard Widget across Units value which is comming from Backend")
	  public void getUnitsValue() throws Exception {
		Reporter.log("======================get the scoreboard unit value======================",true);
		String unit = sb.getUnitsValue();
		test.log(LogStatus.INFO,"","Actaul value : " + unit);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","UNITS");
		String formattedvalue=gl.truncate(respop/1000000);
		test.log(LogStatus.INFO,"", "Expected value :" + formattedvalue+"M");
		Assert.assertEquals(unit, formattedvalue+"M");
	    }
	
	@Test(description= "Verifying the Units YOY value from Scoreboard Widget across Unitss YOY value which is comming from Backend")
	  public void getUnitsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard unit yoy value======================",true);
		String yoy = sb.getUnitsYoYPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,yoy,"YOY","YOY_UNITS");
	    }
	@Test(description= "Verifying the Units PVP value from Scoreboard Widget across Units PVP value which is comming from Backend")
	  public void getUnitsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard unit pvp value======================",true);
		String pvp = sb.getUnitsPvPPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,pvp,"PVP","PVP_UNITS");
	    }
	
	//####################### Avg sale price Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Avg sale price text is present in Scoreboard Widget or not")
	  public void isAvgSalePriceTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Avg saale price text======================",true);
		String avgsaletext = sb.isAvgSalePriceTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +avgsaletext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(avgsaletext, text);
	    }
	@Test(description= "Verifying the Avg sale price value from Scoreboard Widget across Avg sale price value which is comming from Backend")
	  public void getAvgSalePriceValue() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price value======================",true);
		String avgsale = sb.getAvgSalePriceValue();
		test.log(LogStatus.INFO,"", "Actual value : " + avgsale);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","AVG_SALE_PRICE");
		String formattedvalue=gl.truncate(respop);
	    test.log(LogStatus.INFO,"", "Expected value : " + "$"+ formattedvalue);
		Assert.assertEquals(avgsale, "$"+ formattedvalue);
	    }
	@Test(description= "Verifying the Avg sale price YOY value from Scoreboard Widget across Avg sale price YOY value which is comming from Backend")
	  public void getAvgSalePriceYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price yoy value======================",true);
		String avgsaleyoy = sb.getAvgSalePriceYoYPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,avgsaleyoy,"YOY","YOY_AVG_SALE_PRICE");
	    }
	@Test(description= "Verifying the Avg sale price PVP value from Scoreboard Widget across Avg sale price PVP value which is comming from Backend")
	  public void getAvgSalePricePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price pvp value======================",true);
		String avgsalepvp = sb.getAvgSalePricePvPPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,avgsalepvp,"PVP","PVP_AVG_SALE_PRICE");
	    }
	
	//####################### Conversion Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Conversion text is present in Scoreboard Widget or not")
	  public void isConversionPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard conversion text======================",true);
		String conversiontext = sb.isConversionPresent();
		test.log(LogStatus.INFO,"","Actual data : " +conversiontext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
        Assert.assertEquals(conversiontext, text);
	    }
	@Test(description= "Verifying the Conversion value from Scoreboard Widget across Conversion value which is comming from Backend")
	  public void getConversionValue() throws Exception {
		Reporter.log("======================get the scoreboard conversion value======================",true);
		String conversionvalue = sb.getConversionValue();
		test.log(LogStatus.INFO,"", "Actual value :"+conversionvalue);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","CONVERSION");
		String formattedvalue=gl.truncate(respop);
		test.log(LogStatus.INFO,"", "Expected value :" +formattedvalue+"%");
		Assert.assertEquals(conversionvalue, formattedvalue+"%");
	    }
	@Test(description= "Verifying the Conversion YOY value from Scoreboard Widget across Conversion YOY value which is comming from Backend")
	  public void getConversionYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion yoy value======================",true);
		String conversionyoy = sb.getConversionYoYPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,conversionyoy,"YOY","YOY_CONVERSION");
	    }
	@Test(description= "Verifying the Conversion PVP value from Scoreboard Widget across Conversion PVP value which is comming from Backend")
	  public void getConversionPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion pvp value======================",true);
		String conversionpvp = sb.getConversionPvPPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,conversionpvp,"PVP","PVP_CONVERSION");
	    }

	//####################### PageView Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Page view text is present in Scoreboard Widget or not")
	  public void isPageViewsPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard pageview text======================",true);
		String pageviewtext = sb.isPageViewsPresent();
		test.log(LogStatus.INFO,"","Actual data : " +pageviewtext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(pageviewtext, text);
	    }
	@Test(description= "Verifying the Page views value from Scoreboard Widget across page views value which is comming from Backend")
	  public void getPageViewsValue() throws Exception {
		Reporter.log("======================get the scoreboard pageview value======================",true);
		String pageviewvalue = sb.getPageViewsValue();
		test.log(LogStatus.INFO,"", "Actual value :" + pageviewvalue);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","PAGEVIEWS");
	    String resrevenue1=gl.truncate(respop/1000000);
	    test.log(LogStatus.INFO,"", "Expected value :" + resrevenue1+"M");
		Assert.assertEquals(pageviewvalue, resrevenue1+"M");
	    }
	@Test(description= "Verifying the Page views YOY value from Scoreboard Widget across Page views YOY value which is comming from Backend")
	  public void getPageViewsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion yoy value======================",true);
		String pageviewyoy = sb.getPageViewsYoYPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,pageviewyoy,"YOY","YOY_PAGEVIEWS");
	    }
	@Test(description= "Verifying the Page views PVP value from Scoreboard Widget across Page views PVP value which is comming from Backend")
	  public void getPageViewsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion pvp value======================",true);
		String pageviewpvp = sb.getPageViewsPvPPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,pageviewpvp,"PVP","PVP_PAGEVIEWS");
	    }
	
	//####################### Units per order Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Units per order text is present in Scoreboard Widget or not")
	  public void isUnitsPerOrder(String text) throws Exception {
		Reporter.log("======================get the scoreboard units per order text======================",true);
		String unitsperorder = sb.isUnitsPerOrder();
		test.log(LogStatus.INFO,"","Actual data : " +unitsperorder);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(unitsperorder, text);
	    }
	@Test(description= "Verifying the Units per order value from Scoreboard Widget across Units per order value which is comming from Backend")
	  public void getUnitsPerOrderValue() throws Exception {
		Reporter.log("======================get the scoreboard units per order value======================",true);
		String unitsperordervalue = sb.getUnitsPerOrderValue();
	    test.log(LogStatus.INFO,"", "Actual value : "+unitsperordervalue);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","UNITS_PER_ORDER");
		String resrevenue1=gl.truncate(respop);
		test.log(LogStatus.INFO,"", "Expected value : "+resrevenue1+"X");
		Assert.assertEquals(unitsperordervalue, resrevenue1+"X");
	    }
	@Test(description= "Verifying the Units per order YOY value from Scoreboard Widget across Units per order YOY value which is comming from Backend")
	  public void getUnitsPerOrderYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard units per order yoy value======================",true);
		String unitsperoderyoy = sb.getUnitsPerOrderYoYPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,unitsperoderyoy,"YOY","YOY_UNITS_PER_ORDER");
	    }
	@Test(description= "Verifying the Units per order PVP value from Scoreboard Widget across Units per order PVP value which is comming from Backend")
	  public void getUnitsPerOrderPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard units per order pvp value======================",true);
		String unitsperoderpvp = sb.getUnitsPerOrderPvPPercent();
		gl.getYOYandPVPforscoreboardvalues(headerwidgetfile,unitsperoderpvp,"PVP","PVP_UNITS_PER_ORDER");
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

