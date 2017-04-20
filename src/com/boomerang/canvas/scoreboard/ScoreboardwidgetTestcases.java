package com.boomerang.canvas.scoreboard;

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

import com.boomerang.canvas.PPIscoreboarddata.PPIscoreboardwidgetapi;
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
	Scoreboardwidgetapi sapi=new Scoreboardwidgetapi();
	PPIscoreboardwidgetapi papi=new PPIscoreboardwidgetapi();
	 
	 String headerwidgetfile = "/src/com/boomerang/canvas/testoutput/ScoreboardWidget.json";
	 String PPIwidgetfile = "/src/com/boomerang/canvas/testoutput/PPIScoreboardWidget.json";

	 
	@BeforeSuite
	public void login() throws Exception{
			 if(driver !=null){
				 driver.quit();
				 }
					Testbase tb=new Testbase();
					tb.getBrowser(System.getProperty("Browser"),System.getProperty("os.name"));
					driver.get(System.getProperty("ClientURL"));
					loginPage.login(System.getProperty("Username"), System.getProperty("Password"), "");			
					sapi.getHeaderdata_from_api(headerwidgetfile,"getHeaderData","Headerwidget");
					papi.getHeaderdata_from_api(PPIwidgetfile,"getPPIScorecardDataForDimension","PPIScoreboard");
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
		sb.Scoreboardbutton();
		String margintext = sb.MarginTextPresent();
		test.log(LogStatus.INFO,"","Actual data :" + margintext);
		test.log(LogStatus.INFO,"","Expected data :" + text);
		Assert.assertEquals(margintext, text);
	    }
	@Test(description= "Verifying the Margin value from Scoreboard Widget across Margin value which is comming from Backend")
	  public void getMarginValue() throws Exception {
		Reporter.log("======================get the scoreboard margin value======================",true);
		sb.Scoreboardbutton();
		String marginvalue = sb.getMarginValue();
		test.log(LogStatus.INFO,"", "Actual value : " + marginvalue);
		Double respop= gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1);
		Assert.assertEquals(marginvalue, "$"+resrevenue1);
	    }
	@Test(description= "Verifying the Margin YOY value from Scoreboard Widget across Margin YOY value which is comming from Backend")
	  public void getMarginYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard margin yoy value======================",true);
		sb.Scoreboardbutton();
		String yoy = sb.getMarginYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	    }
	@Test(description= "Verifying the Margin PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getMarginPVPPercent() throws Exception {
		Reporter.log("======================get the scoreboard margin pvp value======================",true);
		sb.Scoreboardbutton();
		String pvp = sb.getMargingPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
	    }
	
	//####################### Revenue Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Revenue text is present in Scoreboard Widget or not")
	  public void isRevenueTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard revnue text======================",true);
		sb.Scoreboardbutton();
		String revenuetext = sb.isRevenueTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +revenuetext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(revenuetext, text);
	    }
	@Test(description= "Verifying the Revenue value from Scoreboard Widget across Revenue value which is comming from Backend")
	  public void getRevenueValue() throws Exception {
		Reporter.log("======================get the scoreboard revnue value======================",true);
		sb.Scoreboardbutton();
		String revenue = sb.getRevenueValue();
		test.log(LogStatus.INFO,"","Actual data : " + revenue);
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","REVENUE");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1);
		Assert.assertEquals(revenue, "$"+resrevenue1);
	    }
	@Test(description= "Verifying the Revenue YOY value from Scoreboard Widget across Revenue YOY value which is comming from Backend")
	  public void getRevenueYoYPercent() throws Exception {
		Reporter.log("======================get the scoeboard revnue yoy value======================",true);
		sb.Scoreboardbutton();
		String yoy = sb.getRevenueYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_REVENUE");
	    }
	@Test(description= "Verifying the Revenue PVP value from Scoreboard Widget across Revenue PVP value which is comming from Backend")
	  public void getRevenuePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard revnue pvp value======================",true);
		sb.Scoreboardbutton();
		String pvp = sb.getRevenuePvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_REVENUE");
	    }
	
	//####################### Unit Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Unit text is present in Scoreboard Widget or not")
	  public void isUnitsTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard unit text======================",true);
		sb.Scoreboardbutton();
		String unittext = sb.isUnitsTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +unittext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(unittext, text);
	    }
	@Test(description= "Verifying the Units value from Scoreboard Widget across Units value which is comming from Backend")
	  public void getUnitsValue() throws Exception {
		Reporter.log("======================get the scoreboard unit value======================",true);
		sb.Scoreboardbutton();
		String unit = sb.getUnitsValue();
		test.log(LogStatus.INFO,"","Actaul value : " + unit);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","UNITS");
		/*String formattedvalue=gl.truncate(respop/1000000);
		test.log(LogStatus.INFO,"", "Expected value :" + formattedvalue+"M");
		Assert.assertEquals(unit, formattedvalue+"M");*/
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + resrevenue1);
		Assert.assertEquals(unit, resrevenue1);
	    }
	
	@Test(description= "Verifying the Units YOY value from Scoreboard Widget across Unitss YOY value which is comming from Backend")
	  public void getUnitsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard unit yoy value======================",true);
		sb.Scoreboardbutton();
		String yoy = sb.getUnitsYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_UNITS");
	    }
	@Test(description= "Verifying the Units PVP value from Scoreboard Widget across Units PVP value which is comming from Backend")
	  public void getUnitsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard unit pvp value======================",true);
		sb.Scoreboardbutton();
		String pvp = sb.getUnitsPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_UNITS");
	    }
	
	//####################### Avg sale price Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Avg sale price text is present in Scoreboard Widget or not")
	  public void isAvgSalePriceTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Avg saale price text======================",true);
		sb.Scoreboardbutton();
		String avgsaletext = sb.isAvgSalePriceTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +avgsaletext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(avgsaletext, text);
	    }
	@Test(description= "Verifying the Avg sale price value from Scoreboard Widget across Avg sale price value which is comming from Backend")
	  public void getAvgSalePriceValue() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price value======================",true);
		sb.Scoreboardbutton();
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
		sb.Scoreboardbutton();
		String avgsaleyoy = sb.getAvgSalePriceYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,avgsaleyoy,"YOY","YOY_AVG_SALE_PRICE");
	    }
	@Test(description= "Verifying the Avg sale price PVP value from Scoreboard Widget across Avg sale price PVP value which is comming from Backend")
	  public void getAvgSalePricePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Avg sale price pvp value======================",true);
		sb.Scoreboardbutton();
		String avgsalepvp = sb.getAvgSalePricePvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,avgsalepvp,"PVP","PVP_AVG_SALE_PRICE");
	    }
	
	//####################### Conversion Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Conversion text is present in Scoreboard Widget or not")
	  public void isConversionPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard conversion text======================",true);
		sb.Scoreboardbutton();
		String conversiontext = sb.isConversionPresent();
		test.log(LogStatus.INFO,"","Actual data : " +conversiontext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
        Assert.assertEquals(conversiontext, text);
	    }
	@Test(description= "Verifying the Conversion value from Scoreboard Widget across Conversion value which is comming from Backend")
	  public void getConversionValue() throws Exception {
		Reporter.log("======================get the scoreboard conversion value======================",true);
		sb.Scoreboardbutton();
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
		sb.Scoreboardbutton();
		String conversionyoy = sb.getConversionYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,conversionyoy,"YOY","YOY_CONVERSION");
	    }
	@Test(description= "Verifying the Conversion PVP value from Scoreboard Widget across Conversion PVP value which is comming from Backend")
	  public void getConversionPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion pvp value======================",true);
		sb.Scoreboardbutton();
		String conversionpvp = sb.getConversionPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,conversionpvp,"PVP","PVP_CONVERSION");
	    }

	//####################### PageView Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Page view text is present in Scoreboard Widget or not")
	  public void isPageViewsPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard pageview text======================",true);
		sb.Scoreboardbutton();
		String pageviewtext = sb.isPageViewsPresent();
		test.log(LogStatus.INFO,"","Actual data : " +pageviewtext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(pageviewtext, text);
	    }
	@Test(description= "Verifying the Page views value from Scoreboard Widget across page views value which is comming from Backend")
	  public void getPageViewsValue() throws Exception {
		Reporter.log("======================get the scoreboard pageview value======================",true);
		sb.Scoreboardbutton();
		String pageviewvalue = sb.getPageViewsValue();
		test.log(LogStatus.INFO,"", "Actual value :" + pageviewvalue);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","PAGEVIEWS");
	  /*  String resrevenue1=gl.truncate(respop/1000000);
	    test.log(LogStatus.INFO,"", "Expected value :" + resrevenue1+"M");
		Assert.assertEquals(pageviewvalue, resrevenue1+"M");*/
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + resrevenue1);
		Assert.assertEquals(pageviewvalue, resrevenue1);
	    }
	@Test(description= "Verifying the Page views YOY value from Scoreboard Widget across Page views YOY value which is comming from Backend")
	  public void getPageViewsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion yoy value======================",true);
		sb.Scoreboardbutton();
		String pageviewyoy = sb.getPageViewsYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pageviewyoy,"YOY","YOY_PAGEVIEWS");
	    }
	@Test(description= "Verifying the Page views PVP value from Scoreboard Widget across Page views PVP value which is comming from Backend")
	  public void getPageViewsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard conversion pvp value======================",true);
		sb.Scoreboardbutton();
		String pageviewpvp = sb.getPageViewsPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pageviewpvp,"PVP","PVP_PAGEVIEWS");
	    }
	
	//####################### Units per order Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Units per order text is present in Scoreboard Widget or not")
	  public void isUnitsPerOrder(String text) throws Exception {
		Reporter.log("======================get the scoreboard units per order text======================",true);
		sb.Scoreboardbutton();
		String unitsperorder = sb.isUnitsPerOrder();
		test.log(LogStatus.INFO,"","Actual data : " +unitsperorder);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(unitsperorder, text);
	    }
	@Test(description= "Verifying the Units per order value from Scoreboard Widget across Units per order value which is comming from Backend")
	  public void getUnitsPerOrderValue() throws Exception {
		Reporter.log("======================get the scoreboard units per order value======================",true);
		sb.Scoreboardbutton();
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
		sb.Scoreboardbutton();
		String unitsperoderyoy = sb.getUnitsPerOrderYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,unitsperoderyoy,"YOY","YOY_UNITS_PER_ORDER");
	    }
	@Test(description= "Verifying the Units per order PVP value from Scoreboard Widget across Units per order PVP value which is comming from Backend")
	  public void getUnitsPerOrderPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard units per order pvp value======================",true);
		sb.Scoreboardbutton();
		String unitsperoderpvp = sb.getUnitsPerOrderPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,unitsperoderpvp,"PVP","PVP_UNITS_PER_ORDER");
	    }
	
	//############################ margin graph link actions #############################
	@Test(description= "Verifying margin graph link is clicking in scoreboard widget from landing page")
	  public void checkgraphlink() throws Exception {
		Reporter.log("======================Validating the margin graph link is click action======================",true);
		sb.Scoreboardbutton();
		sb.checkgraphlink();
		sb.PPIwidgetbutton();
	    }
	@Test(description= "Verifying margin graph link navigating to landing page")
	  public void checkgraphnaivateback() throws Exception {
		Reporter.log("======================Validating the margin graph link navigate back to landing page or not======================",true);
		sb.checkgraphnaivateback();
	    }
	//###########################graph margin values####################################
	@Test(description= "Verifying the margin value inside margin graph page")
	  public void graphinsidemarginvalue() throws Exception {
		Reporter.log("======================Validating the margin value inside margin graph page======================",true);
		sb.Scoreboardbutton();
		sb.checkgraphlink();
		String marginvalue = sb.getMarginValue();
		test.log(LogStatus.INFO,"", "Actual value : " + marginvalue);
		Double respop= gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1);
		Assert.assertEquals(marginvalue, "$"+resrevenue1);
	}
	@Parameters("text")
	@Test(description= "Verifying the margin text inside margin graph page")
	  public void graphinsidemargintext(String text) throws Exception {
		Reporter.log("======================Validating the margin TEX inside margin graph page======================",true);
		String margintext = sb.MarginTextPresent();
		test.log(LogStatus.INFO,"","Actual data :" + margintext);
		test.log(LogStatus.INFO,"","Expected data :" + text);
		Assert.assertEquals(margintext, text);
	}
	@Parameters("text")
	@Test(description= "Verifying the margin YOY value inside margin graph page")
	  public void graphinsidemarginyoy() throws Exception {
		Reporter.log("======================Validating the margin YOY value inside margin graph page======================",true);
		String yoy = sb.getMarginYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	}
	@Parameters("text")
	@Test(description= "Verifying the margin PVP value inside margin graph page")
	  public void graphinsidemarginpvp() throws Exception {
		Reporter.log("======================Validating the margin PVP value inside margin graph page======================",true);
		String pvp = sb.getMargingPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
	}
	//#################################Graph ppi values #########################
	//################### Graph margin values #########################
	@Parameters({"text"})
	@Test(description= "Verifying the Graph Margin text is present in Scoreboard Widget or not")
	  public void isGraphMarginTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Graph margin text ======================",true);
		sb.PPIwidgetbutton();
		String margintext = sb.GraphMarginTextPresent();
		test.log(LogStatus.INFO,"","Actual data :" + margintext);
		test.log(LogStatus.INFO,"","Expected data :" + text);
		Assert.assertEquals(margintext, text);
	    }
	@Test(description= "Verifying the Graph Margin value from Scoreboard Widget across Margin value which is comming from Backend")
	  public void getGraphMarginValue() throws Exception {
		Reporter.log("======================get the scoreboard Graph margin value======================",true);
		String marginvalue = sb.getGraphMarginValue();
		test.log(LogStatus.INFO,"", "Actual value : " + marginvalue);
		Double respop= gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1);
		Assert.assertEquals(marginvalue, "$"+resrevenue1);
	    }
	@Test(description= "Verifying the Margin YOY value from Scoreboard Widget across Margin YOY value which is comming from Backend")
	  public void getGraphMarginYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph margin yoy value======================",true);
		String yoy = sb.getGraphMarginYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	    }
	@Test(description= "Verifying the Margin PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getGraphMarginPVPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph margin pvp value======================",true);
		String pvp = sb.getGraphMargingPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
	    }
	
	//####################### Revenue Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Graph Revenue text is present in Scoreboard Widget or not")
	  public void isGraphRevenueTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Graph revnue text======================",true);
		String revenuetext = sb.isGraphRevenueTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +revenuetext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(revenuetext, text);
	    }
	@Test(description= "Verifying the Graph Revenue value from Scoreboard Widget across Revenue value which is comming from Backend")
	  public void getGraphRevenueValue() throws Exception {
		Reporter.log("======================get the scoreboard Graph revnue value======================",true);
		String revenue = sb.getGraphRevenueValue();
		test.log(LogStatus.INFO,"","Actual data : " + revenue);
		Double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","REVENUE");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1);
		Assert.assertEquals(revenue, "$"+resrevenue1);
	    }
	@Test(description= "Verifying the Graph Revenue YOY value from Scoreboard Widget across Revenue YOY value which is comming from Backend")
	  public void getGraphRevenueYoYPercent() throws Exception {
		Reporter.log("======================get the scoeboard Graph revnue yoy value======================",true);
		String yoy = sb.getGraphRevenueYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_REVENUE");
	    }
	@Test(description= "Verifying the Graph Revenue PVP value from Scoreboard Widget across Revenue PVP value which is comming from Backend")
	  public void getGraphRevenuePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph revnue pvp value======================",true);
		String pvp = sb.getGraphRevenuePvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_REVENUE");
	    }
	
	//####################### Unit Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Graph Unit text is present in Scoreboard Widget or not")
	  public void isGraphUnitsTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Graph unit text======================",true);
		String unittext = sb.isGraphUnitsTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +unittext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(unittext, text);
	    }
	@Test(description= "Verifying the Graph Units value from Scoreboard Widget across Units value which is comming from Backend")
	  public void getGraphUnitsValue() throws Exception {
		Reporter.log("======================get the scoreboard Graph unit value======================",true);
		String unit = sb.getGraphUnitsValue();
		test.log(LogStatus.INFO,"","Actaul value : " + unit);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","UNITS");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + resrevenue1);
		Assert.assertEquals(unit, resrevenue1);
	    }
	
	@Test(description= "Verifying the Graph Units YOY value from Scoreboard Widget across Unitss YOY value which is comming from Backend")
	  public void getGraphUnitsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph unit yoy value======================",true);
		String yoy = sb.getGraphUnitsYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_UNITS");
	    }
	@Test(description= "Verifying the Graph Units PVP value from Scoreboard Widget across Units PVP value which is comming from Backend")
	  public void getGraphUnitsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph unit pvp value======================",true);
		String pvp = sb.getGraphUnitsPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_UNITS");
	    }
	
	//####################### Avg sale price Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Graph Avg sale price text is present in Scoreboard Widget or not")
	  public void isGraphAvgSalePriceTextPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Graph Avg saale price text======================",true);
		String avgsaletext = sb.isGraphAvgSalePriceTextPresent();
		test.log(LogStatus.INFO,"","Actual data : " +avgsaletext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(avgsaletext, text);
	    }
	@Test(description= "Verifying the Graph Avg sale price value from Scoreboard Widget across Avg sale price value which is comming from Backend")
	  public void getGraphAvgSalePriceValue() throws Exception {
		Reporter.log("======================get the scoreboard Graph Avg sale price value======================",true);
		String avgsale = sb.getGraphAvgSalePriceValue();
		test.log(LogStatus.INFO,"", "Actual value : " + avgsale);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","AVG_SALE_PRICE");
		String formattedvalue=gl.truncate(respop);
	    test.log(LogStatus.INFO,"", "Expected value : " + "$"+ formattedvalue);
		Assert.assertEquals(avgsale, "$"+ formattedvalue);
	    }
	@Test(description= "Verifying the Graph Avg sale price YOY value from Scoreboard Widget across Avg sale price YOY value which is comming from Backend")
	  public void getGraphAvgSalePriceYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph Avg sale price yoy value======================",true);
		String avgsaleyoy = sb.getGraphAvgSalePriceYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,avgsaleyoy,"YOY","YOY_AVG_SALE_PRICE");
	    }
	@Test(description= "Verifying the Graph Avg sale price PVP value from Scoreboard Widget across Avg sale price PVP value which is comming from Backend")
	  public void getGraphAvgSalePricePvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph Avg sale price pvp value======================",true);
		String avgsalepvp = sb.getGraphAvgSalePricePvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,avgsalepvp,"PVP","PVP_AVG_SALE_PRICE");
	    }
	
	//####################### Conversion Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Graph Conversion text is present in Scoreboard Widget or not")
	  public void isGraphConversionPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Graph conversion text======================",true);
		String conversiontext = sb.isGraphConversionPresent();
		test.log(LogStatus.INFO,"","Actual data : " +conversiontext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
        Assert.assertEquals(conversiontext, text);
	    }
	@Test(description= "Verifying the Graph Conversion value from Scoreboard Widget across Conversion value which is comming from Backend")
	  public void getGraphConversionValue() throws Exception {
		Reporter.log("======================get the scoreboard Graph conversion value======================",true);
		String conversionvalue = sb.getGraphConversionValue();
		test.log(LogStatus.INFO,"", "Actual value :"+conversionvalue);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","CONVERSION");
		String formattedvalue=gl.truncate(respop);
		test.log(LogStatus.INFO,"", "Expected value :" +formattedvalue+"%");
		Assert.assertEquals(conversionvalue, formattedvalue+"%");
	    }
	@Test(description= "Verifying the Graph Conversion YOY value from Scoreboard Widget across Conversion YOY value which is comming from Backend")
	  public void getGraphConversionYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph conversion yoy value======================",true);
		String conversionyoy = sb.getGraphConversionYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,conversionyoy,"YOY","YOY_CONVERSION");
	    }
	@Test(description= "Verifying the Graph Conversion PVP value from Scoreboard Widget across Conversion PVP value which is comming from Backend")
	  public void getGraphConversionPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph conversion pvp value======================",true);
		String conversionpvp = sb.getGraphConversionPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,conversionpvp,"PVP","PVP_CONVERSION");
	    }

	//####################### PageView Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Graph Page view text is present in Scoreboard Widget or not")
	  public void isGraphPageViewsPresent(String text) throws Exception {
		Reporter.log("======================get the scoreboard Graph pageview text======================",true);
		String pageviewtext = sb.isGraphPageViewsPresent();
		test.log(LogStatus.INFO,"","Actual data : " +pageviewtext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(pageviewtext, text);
	    }
	@Test(description= "Verifying the Graph Page views value from Scoreboard Widget across page views value which is comming from Backend")
	  public void getGraphPageViewsValue() throws Exception {
		Reporter.log("======================get the scoreboard Graphpageview value======================",true);
		String pageviewvalue = sb.getGraphPageViewsValue();
		test.log(LogStatus.INFO,"", "Actual value :" + pageviewvalue);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","PAGEVIEWS");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + resrevenue1);
		Assert.assertEquals(pageviewvalue, resrevenue1);
	    }
	@Test(description= "Verifying the Graph Page views YOY value from Scoreboard Widget across Page views YOY value which is comming from Backend")
	  public void getGraphPageViewsYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph conversion yoy value======================",true);
		String pageviewyoy = sb.getPageViewsYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pageviewyoy,"YOY","YOY_PAGEVIEWS");
	    }
	@Test(description= "Verifying the Graph Page views PVP value from Scoreboard Widget across Page views PVP value which is comming from Backend")
	  public void getGraphPageViewsPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph conversion pvp value======================",true);
		String pageviewpvp = sb.getGraphPageViewsPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pageviewpvp,"PVP","PVP_PAGEVIEWS");
	    }
	
	//####################### Units per order Test cases #########################################
	
	@Parameters({"text"})
	@Test(description= "Verifying the Graph Units per order text is present in Scoreboard Widget or not")
	  public void isGraphUnitsPerOrder(String text) throws Exception {
		Reporter.log("======================get the scoreboard Graph units per order text======================",true);
		String unitsperorder = sb.isGraphUnitsPerOrder();
		test.log(LogStatus.INFO,"","Actual data : " +unitsperorder);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(unitsperorder, text);
	    }
	@Test(description= "Verifying the Graph Units per order value from Scoreboard Widget across Units per order value which is comming from Backend")
	  public void getGraphUnitsPerOrderValue() throws Exception {
		Reporter.log("======================get the scoreboard Graph units per order value======================",true);
		String unitsperordervalue = sb.getGraphUnitsPerOrderValue();
	    test.log(LogStatus.INFO,"", "Actual value : "+unitsperordervalue);
		double respop=gl.getresponsesintdata(headerwidgetfile,"RESULT","UNITS_PER_ORDER");
		String resrevenue1=gl.truncate(respop);
		test.log(LogStatus.INFO,"", "Expected value : "+resrevenue1+"X");
		Assert.assertEquals(unitsperordervalue, resrevenue1+"X");
	    }
	@Test(description= "Verifying the Graph Units per order YOY value from Scoreboard Widget across Units per order YOY value which is comming from Backend")
	  public void getGraphUnitsPerOrderYoYPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph units per order yoy value======================",true);
		String unitsperoderyoy = sb.getGraphUnitsPerOrderYoYPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,unitsperoderyoy,"YOY","YOY_UNITS_PER_ORDER");
	    }
	@Test(description= "Verifying the Graph Units per order PVP value from Scoreboard Widget across Units per order PVP value which is comming from Backend")
	  public void getGraphUnitsPerOrderPvPPercent() throws Exception {
		Reporter.log("======================get the scoreboard Graph units per order pvp value======================",true);
		String unitsperoderpvp = sb.getGraphUnitsPerOrderPvPPercent();
		sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,unitsperoderpvp,"PVP","PVP_UNITS_PER_ORDER");
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
		/*Double respop= gl.getresponsesintdata(headerwidgetfile,"RESULT","MARGIN");
		String resrevenue1=gl.conversion(respop);
		test.log(LogStatus.INFO,"", "Expected value : " + "$"+resrevenue1);
		Assert.assertEquals(marginvalue, "$"+resrevenue1);*/
	    }
	@Test(description= "Verifying the getavgpvpvalue YOY value from Scoreboard Widget across Margin YOY value which is comming from Backend")
	  public void getavgyoyvalue() throws Exception {
		Reporter.log("======================get the scoreboard margin yoy value======================",true);
		String yoy = sb.getavgyoyvalue();
		//sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,yoy,"YOY","YOY_MARGIN");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getavgpvpvalue() throws Exception {
		Reporter.log("======================get the scoreboard margin pvp value======================",true);
		String pvp = sb.getavgpvpvalue();
		//sapi.getYOYandPVPforScoreboardwidgetvalues(headerwidgetfile,pvp,"PVP","PVP_MARGIN");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatornames() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitatornames ======================",true);
		List<WebElement> pvp = sb.getallcompitatornames();
		papi.getdata(pvp,PPIwidgetfile,"DIMENSION","competitor");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatorppivalues() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitator ppi values ======================",true);
		List<WebElement> pvp = sb.getallcompitatorppivalues();
		papi.getIntdata(pvp,PPIwidgetfile,"RESULT","PPI");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatoryoyvalues() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitator yoy values ======================",true);
		List<WebElement> pvp = sb.getallcompitatoryoyvalues();
		papi.getYOYandPVPforPPIScoreboardwidgetvalues(pvp,PPIwidgetfile,"YOY","YOY_PPI");
	    }
	@Test(description= "Verifying the getavgpvpvalue PVP value from Scoreboard Widget across Margin PVP value which is comming from Backend")
	  public void getallcompitatorpvpvalues() throws Exception {
		Reporter.log("======================get the scoreboard getallcompitator pvp values ======================",true);
		List<WebElement> pvp = sb.getallcompitatorpvpvalues();
		papi.getYOYandPVPforPPIScoreboardwidgetvalues(pvp,PPIwidgetfile,"PVP","PVP_PPI");
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

