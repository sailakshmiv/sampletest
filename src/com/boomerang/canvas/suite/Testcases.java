package com.boomerang.canvas.suite;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.boomerang.canvas.genericlibrary.Genericlib;
import com.boomerang.canvas.pageactions.Bigmovers;
import com.boomerang.canvas.pageactions.Headerwidget;
import com.boomerang.canvas.pageactions.LoginPage;
import com.boomerang.canvas.pageactions.ScoreboardWidget;
import com.boomerang.canvas.test.listeners.ScreenshotListener;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

//@Listeners(org.uncommons.reportng.HTMLReporter.class)
public class Testcases extends Testbase implements ITestListener{
	LoginPage loginPage=new LoginPage();
	Genericlib gl=new Genericlib();
	Headerwidget hd=new Headerwidget();
	ExtentReports report=Testsuite.report;
	ExtentTest test=Testsuite.test;
	ScoreboardWidget sb=new ScoreboardWidget();
	ScreenshotListener sc=new ScreenshotListener();
	 DecimalFormat format = new DecimalFormat();
	 Bigmovers bm=new Bigmovers();
	 String headerwidgetfile = "/src/org/boomerang/canvas/testoutput/HeaderandScoreboard.json";
	 String aggregateppifile = "/src/org/boomerang/canvas/testoutput/AggregatePPI.json";
	// String bigmoversppi = "/src/org/boomerang/canvas/testoutput/BigMoversAll.json";
	 String bigmoverscategoriesppi = "/src/org/boomerang/canvas/testoutput/BigMoversCategories.json";
	 String bigmoversbrandsppi = "/src/org/boomerang/canvas/testoutput/BigMoversBrands.json";

	@Parameters({"suiteName"})
	@BeforeMethod
	 public void createreport(ITestContext arg0,String suiteName) throws IOException{
		try{
		test = report.startTest(arg0.getName());
	    test.assignCategory(suiteName);
		}
		catch(Exception e){
		e.printStackTrace();
		}
	 }
	@Parameters({"suiteName"})
	@AfterMethod
		public void screenshot(ITestResult arg0,String suiteName,Method method) throws InterruptedException, IOException {
		String screenshotname =suiteName+arg0.getName().toString(); 
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
        	System.out.println(test1);
            return;
        }
        System.out.println(test1.description());
		int result=arg0.getStatus();
	      String testcase =suiteName+arg0.getName().toString(); 
	     if(result ==1){
	      prop.put(testcase, "PASS");
	      test.log(LogStatus.PASS, arg0.getName()+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png"),test1.description());
	     }else if(result ==2){
	      prop.put(testcase, "FAIL"); 
	      test.log(LogStatus.FAIL, arg0.getName()+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png")+test1.description(),arg0.getThrowable());
	     }else if(result ==3){
	      prop.put(testcase, "SKIP"); 
	      test.log(LogStatus.SKIP, arg0.getName()+"Screencast below:" + test.addScreenCapture("./html/"+screenshotname+".png")+test1.description(),arg0.getThrowable());
	     }
		report.endTest(test);
		report.flush();	
		}
	// ######################### Login Test cases #####################################
	@Parameters({"username","password","actualerrormessage"})
	@Test(description = "Valid login verification with valid credentials")
	public void validLogin(String username,String password,String actualerrormessage) throws TimeoutException, Exception {
		//skip(suiteName+depends);	
		Reporter.log("======================Performing Login Test with valid credentials======================", true);
		loginPage.login(username,password, "");
		Reporter.log("======================Login Test with valid credentials completed successfully======================",true);
		loginPage.logout();
	}
	@Parameters({"username","password","actualerrormessage"})
	@Test(description = "Invalid login verification with valid username and empty password")
	public void invalidLoginWithvalidUsernameEmptyPassword(String username,String password,String actualerrormessage) throws Exception {
		Reporter.log("======================Performing Login Test with invalid credentials======================",true);
		loginPage.login(username,password, actualerrormessage);
		Reporter.log("======================Login Test with invalid credentials completed successfully======================",true);
	}
	@Parameters({"username","password","actualerrormessage"})
	@Test(description = "Invalid login verification with empty username and empty password")
	public void invalidLoginWithEmptyUsernameEmptyPassword(String username,String password,String actualerrormessage) throws Exception {
		Reporter.log("======================Performing Login Test with invalid credentials======================",true);
		loginPage.login(username,password, actualerrormessage);
		Reporter.log("======================Login Test with invalid credentials completed successfully======================",true);
	}
	@Parameters({"username","password","actualerrormessage"})
	@Test(description = "Invalid login verification with invalid username and invalid password")
	public void invalidLoginWithInvalidUsernameinvalidPassword(String username,String password,String actualerrormessage) throws Exception {
		Reporter.log("======================Performing Login Test with invalid credentials======================",true);
		loginPage.login(username,password, actualerrormessage);
		Reporter.log("======================Login Test with invalid credentials completed successfully======================",true);
	}
	@Parameters({"username","password","actualerrormessage"})
	@Test(description = "Login veification for Account Lock")
	public void invalidLoginWithAccountLock(String username,String password,String actualerrormessage) throws Exception {
		Reporter.log("======================Performing Login Test with invalid credentials======================",true);
		loginPage.invalidLoginWithAccountLock(username,password, actualerrormessage);
		Reporter.log("======================Login Test with invalid credentials completed successfully======================",true);
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
		Reporter.log("Actual value : " + revenuevalue,true);
		Reporter.log("Expected value : " + text,true);
		Assert.assertEquals(revenuevalue, text);
	    }
	@Test(description= "Verifying the Revenue value from Header Widget across Revenue value which is comming from Backend")
	  public void getRevenueValueInDollars() throws Exception {
		Reporter.log("======================get the revnue value======================",true);
		String revenuevalue = hd.getRevenueValueInDollars();
		Reporter.log("Actual value : " + revenuevalue,true);
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","REVENUE");
		String resrevenue1=gl.truncate(respop/1000000);
		Reporter.log("Expected value : " + "$"+resrevenue1 +"M",true);
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
		Reporter.log("Actual value : " + revenuevalue,true);
		Reporter.log("Expected value : " + text,true);
		Assert.assertEquals(revenuevalue, text);
	    }
	@Test(description= "Verifying the Margin value from Header Widget across Margin value which is comming from Backend")
	  public void getMarginValueInDollars() throws Exception {
		Reporter.log("======================get the margin value======================",true);
		String marginvalue = hd.getMarginValueInDollars();
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.truncate(respop/1000000);
		Reporter.log("Expected value : " + "$"+resrevenue1 +"M",true);
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
	
	// ######################### Scoreboard Test cases #####################################
	//####################### Margin Test cases #########################################
	@Parameters({"text"})
	@Test(description= "Verifying the Margin text is present in Scoreboard Widget or not")
	  public void isMarginTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard margin text ======================",true);
		String margintext = sb.MarginTextPresent();
		Reporter.log("Actual data :" + margintext,true);
		Reporter.log("Expected data :" + text,true);
		Assert.assertEquals(margintext, text);
	    }
	@Test(description= "Verifying the Margin value from Scoreboard Widget across Margin value which is comming from Backend")
	  public void getMarginValue() throws Exception {
		Reporter.log("======================get the scoreboard margin value======================",true);
		String marginvalue = sb.getMarginValue();
		Double respop= gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.truncate(respop/1000000);
		Reporter.log("Expected value : " + "$"+resrevenue1 +"M",true);
		Assert.assertEquals(marginvalue, "$"+resrevenue1 +"M");
	    }
	@Test(description= "Verifying the Margin YOY value from Scoreboard Widget across Margin YOY value which is comming from Backend")
	  public void getMarginYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard margin yoy value======================",true);
		String yoy = sb.getMarginYoYPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	    }
	@Test(description= "Verifying the Margin PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getMarginPVPPercent() throws Exception {
		Reporter.log("======================get the scoreboard margin pvp value======================",true);
		String pvp = sb.getMargingPvPPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
	    }
	
	//####################### Revenue Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Revenue text is present in Scoreboard Widget or not")
	  public void isRevenueTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard revnue text======================",true);
		String revenuetext = sb.isRevenueTextPresent();
		Reporter.log("Actual data : " +revenuetext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(revenuetext, text);
	    }
	@Test(description= "Verifying the Revenue value from Scoreboard Widget across Revenue value which is comming from Backend")
	  public void getRevenueValue() throws Exception {
		Reporter.log("======================get the scoreboard revnue value======================",true);
		String revenue = sb.getRevenueValue();
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","REVENUE");
		String resrevenue1=gl.truncate(respop/1000000);
		Reporter.log("Expected value : " + "$"+resrevenue1 +"M",true);
		Assert.assertEquals(revenue, "$"+resrevenue1 +"M");
	    }
	@Test(description= "Verifying the Revenue YOY value from Scoreboard Widget across Revenue YOY value which is comming from Backend")
	  public void getRevenueYoYPercent() throws Exception {
		Reporter.log("======================get the scoeboard revnue yoy value======================",true);
		String yoy = sb.getRevenueYoYPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,yoy,"YOY","YOY_REVENUE");
	    }
	@Test(description= "Verifying the Revenue PVP value from Scoreboard Widget across Revenue PVP value which is comming from Backend")
	  public void getRevenuePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard revnue pvp value======================",true);
		String pvp = sb.getRevenuePvPPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,pvp,"PVP","PVP_REVENUE");
	    }
	
	//####################### Unit Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Unit text is present in Scoreboard Widget or not")
	  public void isUnitsTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard unit text======================",true);
		String unittext = sb.isUnitsTextPresent();
		Reporter.log("Actual data : " +unittext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(unittext, text);
	    }
	@Test(description= "Verifying the Units value from Scoreboard Widget across Units value which is comming from Backend")
	  public void getUnitsValue() throws Exception {
		Reporter.log("======================get the scoreboard unit value======================",true);
		String unit = sb.getUnitsValue();
		Reporter.log("Actaul value : " + unit,true);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","UNITS");
		String formattedvalue=gl.truncate(respop/1000000);
		Reporter.log("Expected value :" + formattedvalue+"M",true);
		Assert.assertEquals(unit, formattedvalue+"M");
	    }
	
	@Test(description= "Verifying the Units YOY value from Scoreboard Widget across Unitss YOY value which is comming from Backend")
	  public void getUnitsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard unit yoy value======================",true);
		String yoy = sb.getUnitsYoYPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,yoy,"YOY","YOY_UNITS");
	    }
	@Test(description= "Verifying the Units PVP value from Scoreboard Widget across Units PVP value which is comming from Backend")
	  public void getUnitsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard unit pvp value======================",true);
		String pvp = sb.getUnitsPvPPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,pvp,"PVP","PVP_UNITS");
	    }
	
	//####################### Avg sale price Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Avg sale price text is present in Scoreboard Widget or not")
	  public void isAvgSalePriceTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Avg saale price text======================",true);
		String avgsaletext = sb.isAvgSalePriceTextPresent();
		Reporter.log("Actual data : " +avgsaletext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(avgsaletext, text);
	    }
	@Test(description= "Verifying the Avg sale price value from Scoreboard Widget across Avg sale price value which is comming from Backend")
	  public void getAvgSalePriceValue() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price value======================",true);
		String avgsale = sb.getAvgSalePriceValue();
		Reporter.log("Actual value : " + avgsale,true);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","AVG_SALE_PRICE");
		String formattedvalue=gl.truncate(respop);
	    Reporter.log("Expected value : " + "$"+ formattedvalue,true);
		Assert.assertEquals(avgsale, "$"+ formattedvalue);
	    }
	@Test(description= "Verifying the Avg sale price YOY value from Scoreboard Widget across Avg sale price YOY value which is comming from Backend")
	  public void getAvgSalePriceYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price yoy value======================",true);
		String avgsaleyoy = sb.getAvgSalePriceYoYPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,avgsaleyoy,"YOY","YOY_AVG_SALE_PRICE");
	    }
	@Test(description= "Verifying the Avg sale price PVP value from Scoreboard Widget across Avg sale price PVP value which is comming from Backend")
	  public void getAvgSalePricePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price pvp value======================",true);
		String avgsalepvp = sb.getAvgSalePricePvPPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,avgsalepvp,"PVP","PVP_AVG_SALE_PRICE");
	    }
	
	//####################### Conversion Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Conversion text is present in Scoreboard Widget or not")
	  public void isConversionPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard conversion text======================",true);
		String conversiontext = sb.isConversionPresent();
		Reporter.log("Actual data : " +conversiontext,true);
		Reporter.log("Expected data : " +text,true);
        Assert.assertEquals(conversiontext, text);
	    }
	@Test(description= "Verifying the Conversion value from Scoreboard Widget across Conversion value which is comming from Backend")
	  public void getConversionValue() throws Exception {
		Reporter.log("======================get the scoreboard conversion value======================",true);
		String conversionvalue = sb.getConversionValue();
		Reporter.log("Actual value :"+conversionvalue,true);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","CONVERSION");
		String formattedvalue=gl.truncate(respop);
		Reporter.log("Expected value :" +formattedvalue+"%",true);
		Assert.assertEquals(conversionvalue, formattedvalue+"%");
	    }
	@Test(description= "Verifying the Conversion YOY value from Scoreboard Widget across Conversion YOY value which is comming from Backend")
	  public void getConversionYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion yoy value======================",true);
		String conversionyoy = sb.getConversionYoYPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,conversionyoy,"YOY","YOY_CONVERSION");
	    }
	@Test(description= "Verifying the Conversion PVP value from Scoreboard Widget across Conversion PVP value which is comming from Backend")
	  public void getConversionPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion pvp value======================",true);
		String conversionpvp = sb.getConversionPvPPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,conversionpvp,"PVP","PVP_CONVERSION");
	    }

	//####################### PageView Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Page view text is present in Scoreboard Widget or not")
	  public void isPageViewsPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard pageview text======================",true);
		String pageviewtext = sb.isPageViewsPresent();
		Reporter.log("Actual data : " +pageviewtext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(pageviewtext, text);
	    }
	@Test(description= "Verifying the Page views value from Scoreboard Widget across page views value which is comming from Backend")
	  public void getPageViewsValue() throws Exception {
		Reporter.log("======================get the scoreboard pageview value======================",true);
		String pageviewvalue = sb.getPageViewsValue();
		Reporter.log("Actual value :" + pageviewvalue,true);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","PAGEVIEWS");
	    String resrevenue1=gl.truncate(respop/1000000);
	    Reporter.log("Expected value :" + resrevenue1+"M",true);
		Assert.assertEquals(pageviewvalue, resrevenue1+"M");
	    }
	@Test(description= "Verifying the Page views YOY value from Scoreboard Widget across Page views YOY value which is comming from Backend")
	  public void getPageViewsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion yoy value======================",true);
		String pageviewyoy = sb.getPageViewsYoYPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,pageviewyoy,"YOY","YOY_PAGEVIEWS");
	    }
	@Test(description= "Verifying the Page views PVP value from Scoreboard Widget across Page views PVP value which is comming from Backend")
	  public void getPageViewsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion pvp value======================",true);
		String pageviewpvp = sb.getPageViewsPvPPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,pageviewpvp,"PVP","PVP_PAGEVIEWS");
	    }
	
	//####################### Units per order Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Units per order text is present in Scoreboard Widget or not")
	  public void isUnitsPerOrder(String text) throws Exception {
		Reporter.log("======================get the scoreboard units per order text======================",true);
		String unitsperorder = sb.isUnitsPerOrder();
		Reporter.log("Actual data : " +unitsperorder,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(unitsperorder, text);
	    }
	@Test(description= "Verifying the Units per order value from Scoreboard Widget across Units per order value which is comming from Backend")
	  public void getUnitsPerOrderValue() throws Exception {
		Reporter.log("======================get the scoreboard units per order value======================",true);
		String unitsperordervalue = sb.getUnitsPerOrderValue();
	    Reporter.log("Actual value : "+unitsperordervalue,true);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","UNITS_PER_ORDER");
		String resrevenue1=gl.truncate(respop);
		Reporter.log("Expected value : "+resrevenue1+"X",true);
		Assert.assertEquals(unitsperordervalue, resrevenue1+"X");
	    }
	@Test(description= "Verifying the Units per order YOY value from Scoreboard Widget across Units per order YOY value which is comming from Backend")
	  public void getUnitsPerOrderYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard units per order yoy value======================",true);
		String unitsperoderyoy = sb.getUnitsPerOrderYoYPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,unitsperoderyoy,"YOY","YOY_UNITS_PER_ORDER");
	    }
	@Test(description= "Verifying the Units per order PVP value from Scoreboard Widget across Units per order PVP value which is comming from Backend")
	  public void getUnitsPerOrderPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard units per order pvp value======================",true);
		String unitsperoderpvp = sb.getUnitsPerOrderPvPPercent();
		gl.getYOYandPVPvalues(headerwidgetfile,unitsperoderpvp,"PVP","PVP_UNITS_PER_ORDER");
	    }
	
	
	//####################### Bigmovers Test cases #########################################
	
	//###################### get the Big movers text ########################################
	@Parameters({"text"})
	@Test(description= "Verifying the Big Movers text is present in Big Movers Widget or not")
	  public void isBigmoversTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers text======================",true);
		String bigmoverstext = bm.getBigmoversText();
		String a[]=bigmoverstext.split("\n");
		Reporter.log("Actual data : " +a[0],true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(a[0], text);
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the All text is present in Big Movers Widget or not")
	  public void isBigmoversAllTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers all text======================",true);
		String bigmoversalltext = bm.getBigmoversAllText();
		Reporter.log("Actual data : " +bigmoversalltext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(bigmoversalltext, text);
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Winners text is present in Big Movers Widget or not")
	  public void isBigmoversWinnnersTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers winners text======================",true);
		String bigmoverswinnertext = bm.getBigmoversWinnnersText();
		Reporter.log("Actual data : " +bigmoverswinnertext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(bigmoverswinnertext, text);
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Losers text is present in Big Movers Widget or not")
	  public void isBigmoversLosersTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers loosers text======================",true);
		String bigmoversloosertext = bm.getBigmoversLoosersText();
		Reporter.log("Actual data : " +bigmoversloosertext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(bigmoversloosertext, text);
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Categories text is present in BIg Movers Widget or not")
	  public void isBigmoversCategoriesTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers categories text======================",true);
		String bigmoversloosertext = bm.getBigmoversCategoriesText();
		Reporter.log("Actual data : " +bigmoversloosertext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(bigmoversloosertext, text);
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Brands text is present in Big Movers Widget or not")
	  public void isBigmoversBrandsTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers brand text======================",true);
		String bigmoversloosertext = bm.getBigmoversBrandsText();
		Reporter.log("Actual data : " +bigmoversloosertext,true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(bigmoversloosertext, text);
	    }
	//#################### get the Losers data for all,categories,brands ##########################
	@Test(description= "Verifying the Losers list which are under All section from Big Movers Widget across Dimesion values which is comming from Backend")
	  public void getbigmoversallLosersdata() throws Exception {
		Reporter.log("======================get the bigmovers all losers list======================",true);
		bm.BigmoversAllButton();
		List<WebElement> bigmoversloserlist = bm.getBigmoversLoserslist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getdata(bigmoversloserlist, bigmoversbrandsppi,"DIMENSION","brand");
			gl.getdata(bigmoversloserlist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
	    }
	
	@Test(description= "Verifying the Losers list which are under Categories section from Big Movers Widget across Dimesion Merchant values which is comming from Backend")
	  public void getbigmoversCategoriesLosersdata() throws Exception {
		Reporter.log("======================get the bigmovers losers categories list======================",true);
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversloserlist = bm.getBigmoversLoserslist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getdata(bigmoversloserlist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
	    }
	@Test(description= "Verifying the losers list which are under Brands section from Big Movers Widget across Dimesion brands values which is comming from Backend")
	  public void getbigmoversBrandsLosersdata() throws Exception {
		Reporter.log("======================get the bigmovers losers brands list======================",true);
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversloserlist = bm.getBigmoversLoserslist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getdata(bigmoversloserlist, bigmoversbrandsppi,"DIMENSION","brand");
		}
	    }
	//#################### get the Winners data for all,categories,brands ##########################
	@Test(description= "Verifying the Winners list which are under All section from Big Movers Widget across Dimesion values which is comming from Backend")
	  public void getbigmoversallWinnersdata() throws Exception {
		Reporter.log("======================get the bigmovers all winners list======================",true);
		bm.BigmoversAllButton();
		List<WebElement> bigmoverswinnerslist = bm.getBigmoversWinnerslist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getdata(bigmoverswinnerslist, bigmoversbrandsppi,"DIMENSION","brand");
			gl.getdata(bigmoverswinnerslist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
		
 }
	@Test(description= "Verifying the Winners which are under Categories section from Big Movers Widget across Dimesion Merchant values which is comming from Backend")
	  public void getbigmoversCategoriesWinnersdata() throws Exception {
		Reporter.log("======================get the bigmovers winners categories list======================",true);
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoverswinnerslist = bm.getBigmoversWinnerslist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getdata(bigmoverswinnerslist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
}
	@Test(description= "Verifying the Winners which are under Brands section from Big Movers Widget across Dimesion brand values which is comming from Backend")
	  public void getbigmoversBrandsWinnersdata() throws Exception {
		Reporter.log("======================get the bigmovers winners brands list======================",true);
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoverswinnerslist = bm.getBigmoversWinnerslist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getdata(bigmoverswinnerslist, bigmoversbrandsppi,"DIMENSION","brand");
		}
}

//############################### get the Losers YOY value #################################
	@Test(description= "Verifying the Losers YOY value which are under All section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversLosersallYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers Losers all yoy list======================",true);
		bm.BigmoversAllButton();
		List<WebElement> bigmoversLosersyoylist = bm.getBigmoversLosersyoylist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversLosersyoylist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversLosersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
		
	}
	@Test(description= "Verifying the Losers YOY value which are under Categories section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversLoserscategoriesYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers categories yoy list======================",true);
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversLosersyoylist = bm.getBigmoversLosersyoylist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversLosersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
}
	@Test(description= "Verifying the Losers YOY value which are under Brands section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversLosersbrandsYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers brands yoy list======================",true);
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversLosersyoylist = bm.getBigmoversLosersyoylist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversLosersyoylist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			}
}

//############################# get the Winners YOY value #######################
	
	@Test(description= "Verifying the Winnerers YOY value which are under All section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversWinnersallYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners all yoy list======================",true);
		bm.BigmoversAllButton();
		List<WebElement> bigmoversWinnersyoylist = bm.getBigmoversWinnersyoylist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversWinnersyoylist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversWinnersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
		
	}
	@Test(description= "Verifying the Winners YOY value which are under Categories section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversWinnerscategoriesYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners categories yoy list======================",true);
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversWinnersyoylist = bm.getBigmoversWinnersyoylist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversWinnersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
}
	@Test(description= "Verifying the Winners YOY value which are under Brands section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversWinnersbrandsYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners brands yoy list======================",true);
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversWinnersyoylist = bm.getBigmoversWinnersyoylist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversWinnersyoylist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			}
}	
//################################### get the Losers PVP values ###########################	
	@Test(description= "Verifying the Losers PVP value which are under All section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversLosersallPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers all pvp list======================",true);
		bm.BigmoversAllButton();
		List<WebElement> bigmoversLoserspvplist = bm.getBigmoversLoserspvplist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversLoserspvplist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversLoserspvplist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
	}
	@Test(description= "Verifying the Losers PVP value which are under Categories section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversLoserscategoriesPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers categories pvp list======================",true);
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversLoserspvplist = bm.getBigmoversLoserspvplist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversLoserspvplist,bigmoverscategoriesppi,"PVP","DIFFERENCE_REVENUE");
			}
}
	@Test(description= "Verifying the Losers PVP value which are under Brands section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversLosersbrandsPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers brands pvp list======================",true);
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversLoserspvplist = bm.getBigmoversLoserspvplist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversLoserspvplist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			}
}
	
//############################### get the Winners PVP value #############################	
	@Test(description= "Verifying the Winners PVP value which are under All section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversWinnersallPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners all pvp list======================",true);
		bm.BigmoversAllButton();
		List<WebElement> bigmoversWinnerspvplist = bm.getBigmoversWinnerspvplist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversWinnerspvplist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversWinnerspvplist,bigmoverscategoriesppi,"PVP","DIFFERENCE_REVENUE");
			}
		
	}
	@Test(description= "Verifying the Winners PVP value which are under Categories section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversWinnerscategoriesPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners categories pvp list======================",true);
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversWinnerspvplist = bm.getBigmoversWinnerspvplist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversWinnerspvplist,bigmoverscategoriesppi,"PVP","DIFFERENCE_REVENUE");
			}
}
	@Test(description= "Verifying the Winners YOY value which are under Brands section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversWinnersbrandsPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners brands pvp list======================",true);
		//test.log(LogStatus.INFO, "it will validate the PVP value which is shown as like API response");
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversWinnerspvplist = bm.getBigmoversWinnerspvplist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			Reporter.log("No items present in this list",true);
		}
		else{
			gl.getlist(bigmoversWinnerspvplist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			}
}		
	
//######################### get the Triangle graph details #################################
	@Parameters({"text"})
	@Test()
	  public void isBigmoversTrianglegraphTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers Normal text======================",true);
		String bigmoverstext = bm.getBigmoversTrianglegraphNormaltext();
		String a[]=bigmoverstext.split("(");
		Reporter.log("Actual data : " +a[0].trim(),true);
		Reporter.log("Expected data : " +text,true);
		Assert.assertEquals(a[0], text);
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

