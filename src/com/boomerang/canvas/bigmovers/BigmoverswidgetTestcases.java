package com.boomerang.canvas.bigmovers;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;
import org.openqa.selenium.WebElement;
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
import com.boomerang.canvas.headerwidget.HeaderwidgetActions;
import com.boomerang.canvas.login.LoginPageActions;
import com.boomerang.canvas.test.listeners.ScreenshotListener;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;

public class BigmoverswidgetTestcases extends Testbase implements ITestListener{
	
	LoginPageActions loginPage=new LoginPageActions();
	Genericlib gl=new Genericlib();
	ScreenshotListener sc=new ScreenshotListener();
	 DecimalFormat format = new DecimalFormat();
	 BigmoverswidgetActions bm=new BigmoverswidgetActions();
	 HeaderwidgetActions hd=new HeaderwidgetActions();
	 
	 String bigmoverscategoriesppi = "/src/com/boomerang/canvas/testoutput/BigMoversCategories.json";
	 String bigmoversbrandsppi = "/src/com/boomerang/canvas/testoutput/BigMoversBrands.json";

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
	
	
	//####################### Bigmovers Test cases #########################################
	
	//###################### get the Big movers text ########################################
	 @Parameters({"startdate","enddate"})
		@Test(description= "Sending the strat and end dates to get the dashbaord related data")
		  public void sentDates(String startdate,String enddate) throws Exception {
			Reporter.log("======================set the dates to get the details======================",true);
			hd.sentDates(startdate, enddate);
		    }
	 
	 @Parameters({"text"})
	@Test(description= "Verifying the Big Movers text is present in Big Movers Widget or not")
	  public void isBigmoversTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers text======================",true);
		String bigmoverstext = bm.getBigmoversText();
		String a[]=bigmoverstext.split("\n");
		test.log(LogStatus.INFO,"","Actual data : " +a[0]);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(a[0], text);
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the All text is present in Big Movers Widget or not")
	  public void isBigmoversAllTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers all text======================",true);
		if(bm.getBigmoverdatanotavailable()){
		Assert.fail("No data available");
		}else{
		String bigmoversalltext = bm.getBigmoversAllText();
		test.log(LogStatus.INFO,"","Actual data : " +bigmoversalltext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(bigmoversalltext, text);
		}
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Winners text is present in Big Movers Widget or not")
	  public void isBigmoversWinnnersTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers winners text======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}
		else{
		String bigmoverswinnertext = bm.getBigmoversWinnnersText();
		test.log(LogStatus.INFO,"","Actual data : " +bigmoverswinnertext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(bigmoverswinnertext, text);
			}
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Losers text is present in Big Movers Widget or not")
	  public void isBigmoversLosersTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers loosers text======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		String bigmoversloosertext = bm.getBigmoversLoosersText();
		test.log(LogStatus.INFO,"","Actual data : " +bigmoversloosertext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(bigmoversloosertext, text);
			}
	    }
	@Parameters({"text"})
	@Test(description= "Verifying the Categories text is present in BIg Movers Widget or not")
	  public void isBigmoversCategoriesTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers categories text======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		String bigmoversloosertext = bm.getBigmoversCategoriesText();
		test.log(LogStatus.INFO,"","Actual data : " +bigmoversloosertext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(bigmoversloosertext, text);
	    }}
	@Parameters({"text"})
	@Test(description= "Verifying the Brands text is present in Big Movers Widget or not")
	  public void isBigmoversBrandsTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers brand text======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		String bigmoversloosertext = bm.getBigmoversBrandsText();
		test.log(LogStatus.INFO,"","Actual data : " +bigmoversloosertext);
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(bigmoversloosertext, text);
			}
	    }
	//#################### get the Losers data for all,categories,brands ##########################
	@Test(description= "Verifying the Losers list which are under All section from Big Movers Widget across Dimesion values which is comming from Backend")
	  public void getbigmoversallLosersdata() throws Exception {
		Reporter.log("======================get the bigmovers all losers list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversAllButton();
		List<WebElement> bigmoversloserlist = bm.getBigmoversLoserslist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getdata(bigmoversloserlist, bigmoversbrandsppi,"DIMENSION","brand");
			gl.getdata(bigmoversloserlist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
			}
	    }
	
	@Test(description= "Verifying the Losers list which are under Categories section from Big Movers Widget across Dimesion Merchant values which is comming from Backend")
	  public void getbigmoversCategoriesLosersdata() throws Exception {
		Reporter.log("======================get the bigmovers losers categories list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversloserlist = bm.getBigmoversLoserslist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getdata(bigmoversloserlist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
			}
	    }
	@Test(description= "Verifying the losers list which are under Brands section from Big Movers Widget across Dimesion brands values which is comming from Backend")
	  public void getbigmoversBrandsLosersdata() throws Exception {
		Reporter.log("======================get the bigmovers losers brands list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversloserlist = bm.getBigmoversLoserslist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getdata(bigmoversloserlist, bigmoversbrandsppi,"DIMENSION","brand");
		}
			}
	    }
	//#################### get the Winners data for all,categories,brands ##########################
	@Test(description= "Verifying the Winners list which are under All section from Big Movers Widget across Dimesion values which is comming from Backend")
	  public void getbigmoversallWinnersdata() throws Exception {
		Reporter.log("======================get the bigmovers all winners list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversAllButton();
		List<WebElement> bigmoverswinnerslist = bm.getBigmoversWinnerslist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getdata(bigmoverswinnerslist, bigmoversbrandsppi,"DIMENSION","brand");
			gl.getdata(bigmoverswinnerslist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
			}
 }
	@Test(description= "Verifying the Winners which are under Categories section from Big Movers Widget across Dimesion Merchant values which is comming from Backend")
	  public void getbigmoversCategoriesWinnersdata() throws Exception {
		Reporter.log("======================get the bigmovers winners categories list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoverswinnerslist = bm.getBigmoversWinnerslist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getdata(bigmoverswinnerslist, bigmoverscategoriesppi,"DIMENSION","merch_l1_name");
		}
			}
}
	@Test(description= "Verifying the Winners which are under Brands section from Big Movers Widget across Dimesion brand values which is comming from Backend")
	  public void getbigmoversBrandsWinnersdata() throws Exception {
		Reporter.log("======================get the bigmovers winners brands list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoverswinnerslist = bm.getBigmoversWinnerslist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getdata(bigmoverswinnerslist, bigmoversbrandsppi,"DIMENSION","brand");
		}
			}
}

//############################### get the Losers YOY value #################################
	@Test(description= "Verifying the Losers YOY value which are under All section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversLosersallYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers Losers all yoy list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversAllButton();
		List<WebElement> bigmoversLosersyoylist = bm.getBigmoversLosersyoylist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversLosersyoylist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversLosersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
			}
	}
	@Test(description= "Verifying the Losers YOY value which are under Categories section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversLoserscategoriesYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers categories yoy list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversLosersyoylist = bm.getBigmoversLosersyoylist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversLosersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
			}
}
	@Test(description= "Verifying the Losers YOY value which are under Brands section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversLosersbrandsYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers brands yoy list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversLosersyoylist = bm.getBigmoversLosersyoylist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversLosersyoylist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			}
			}
}

//############################# get the Winners YOY value #######################
	
	@Test(description= "Verifying the Winnerers YOY value which are under All section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversWinnersallYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners all yoy list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversAllButton();
		List<WebElement> bigmoversWinnersyoylist = bm.getBigmoversWinnersyoylist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversWinnersyoylist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversWinnersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
			}
	}
	@Test(description= "Verifying the Winners YOY value which are under Categories section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversWinnerscategoriesYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners categories yoy list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversWinnersyoylist = bm.getBigmoversWinnersyoylist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversWinnersyoylist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
			}
}
	@Test(description= "Verifying the Winners YOY value which are under Brands section from Big Movers Widget across Dimesion YOY values which is comming from Backend")
	  public void getBigmoversWinnersbrandsYOYvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners brands yoy list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversWinnersyoylist = bm.getBigmoversWinnersyoylist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversWinnersyoylist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			}
			}
}	
//################################### get the Losers PVP values ###########################	
	@Test(description= "Verifying the Losers PVP value which are under All section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversLosersallPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers all pvp list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversAllButton();
		List<WebElement> bigmoversLoserspvplist = bm.getBigmoversLoserspvplist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversLoserspvplist,bigmoversbrandsppi,"YOY","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversLoserspvplist,bigmoverscategoriesppi,"YOY","DIFFERENCE_REVENUE");
			}
			}
	}
	@Test(description= "Verifying the Losers PVP value which are under Categories section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversLoserscategoriesPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers categories pvp list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversLoserspvplist = bm.getBigmoversLoserspvplist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversLoserspvplist,bigmoverscategoriesppi,"PVP","DIFFERENCE_REVENUE");
			}
			}
}
	@Test(description= "Verifying the Losers PVP value which are under Brands section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversLosersbrandsPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers losers brands pvp list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversLoserspvplist = bm.getBigmoversLoserspvplist();
		if(bm.getBigmoversLossersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversLoserspvplist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			}
			}
}
	
//############################### get the Winners PVP value #############################	
	@Test(description= "Verifying the Winners PVP value which are under All section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversWinnersallPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners all pvp list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversAllButton();
		List<WebElement> bigmoversWinnerspvplist = bm.getBigmoversWinnerspvplist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversWinnerspvplist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			gl.getlist(bigmoversWinnerspvplist,bigmoverscategoriesppi,"PVP","DIFFERENCE_REVENUE");
			}
			}
	}
	@Test(description= "Verifying the Winners PVP value which are under Categories section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversWinnerscategoriesPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners categories pvp list======================",true);
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversCategoriesButton();
		List<WebElement> bigmoversWinnerspvplist = bm.getBigmoversWinnerspvplist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversWinnerspvplist,bigmoverscategoriesppi,"PVP","DIFFERENCE_REVENUE");
			}
			}
}
	@Test(description= "Verifying the Winners YOY value which are under Brands section from Big Movers Widget across Dimesion PVP values which is comming from Backend")
	  public void getBigmoversWinnersbrandsPVPvalue() throws Exception {
		Reporter.log("======================get the bigmovers winners brands pvp list======================",true);
		//test.log(LogStatus.INFO,"", "it will validate the PVP value which is shown as like API response");
		if(bm.getBigmoverdatanotavailable()){
			Assert.fail("No data available");
			}else{
		bm.BigmoversBrandsButton();
		List<WebElement> bigmoversWinnerspvplist = bm.getBigmoversWinnerspvplist();
		if(bm.getBigmoversWinnersEmptyitemText())
		{
			test.log(LogStatus.INFO,"","No items present in this list");
		}
		else{
			gl.getlist(bigmoversWinnerspvplist,bigmoversbrandsppi,"PVP","DIFFERENCE_REVENUE");
			}
			}
}		
	
//######################### get the Triangle graph details #################################
	@Parameters({"text"})
	@Test()
	  public void isBigmoversTrianglegraphTextpresent(String text) throws Exception {
		Reporter.log("======================get the bigmovers Normal text======================",true);
		{
		String bigmoverstext = bm.getBigmoversTrianglegraphNormaltext();
		String a[]=bigmoverstext.split("(");
		test.log(LogStatus.INFO,"","Actual data : " +a[0].trim());
		test.log(LogStatus.INFO,"","Expected data : " +text);
		Assert.assertEquals(a[0], text);
		}
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

