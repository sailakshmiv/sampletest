package com.boomerang.canvas.login;

import java.io.IOException;
import java.lang.reflect.Method;
import org.openqa.selenium.TimeoutException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.boomerang.canvas.bigmovers.BigmoverswidgetActions;
import com.boomerang.canvas.test.listeners.ScreenshotListener;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;

public class LoginTestcases extends Testbase implements ITestListener{
	
	LoginPageActions loginPage=new LoginPageActions();
	ScreenshotListener sc=new ScreenshotListener();
	 BigmoverswidgetActions bm=new BigmoverswidgetActions();
	 
	 
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
	
	@Parameters({"actualerrormessage"})
	@Test(description = "Valid login verification with valid credentials")
	public void validLogin(String actualerrormessage) throws TimeoutException, Exception {
		//skip(suiteName+depends);	
		Reporter.log("======================Performing Login Test with valid credentials======================", true);
		loginPage.login(System.getProperty("Username"), System.getProperty("Password"), "");
		Reporter.log("======================Login Test with valid credentials completed successfully======================",true);
	}
	@Parameters({"password","actualerrormessage"})
	@Test(description = "Invalid login verification with valid username and empty password")
	public void invalidLoginWithvalidUsernameEmptyPassword(String password,String actualerrormessage) throws Exception {
		Reporter.log("======================Performing Login Test with invalid credentials======================",true);
		loginPage.login(System.getProperty("Username"),password, actualerrormessage);
		Reporter.log("======================Login Test with invalid credentials completed successfully======================",true);
	}
	@Parameters({"username","password","actualerrormessage"})
	@Test(description = "Invalid login verification with empty username and empty password")
	public void invalidLoginWithEmptyUsernameEmptyPassword(String username,String password,String actualerrormessage) throws Exception {
		Reporter.log("======================Performing Login Test with invalid credentials======================",true);
		loginPage.login(username,password, actualerrormessage);
		Reporter.log("======================Login Test with invalid credentials completed successfully======================",true);
	}
	/*@Test(description = "Validating favorites button is in disabled state")
	public void favoritesbutton() throws Exception {
		Reporter.log("======================Validating favorites button disabled======================",true);
		loginPage.Favoritesbutton();
		Reporter.log("======================favorites button disabled validation action completed successfully======================",true);
	}
	@Test(description = "Validating alerts button is in disabled state")
	public void alertsbutton() throws Exception {
		Reporter.log("======================Validating alerts button disabled======================",true);
		loginPage.Alertsbutton();
		Reporter.log("======================alerts button disabled validation action completed successfully======================",true);
	}*/
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
	@Parameters({"text"})
	@Test(description = "Validating switching from PPM to PO")
	public void switchfromPPMtoPO(String text) throws Exception {
		Reporter.log("======================Performing switching PPM action======================",true);
		loginPage.switchfromPPMtoPO(text);
		Reporter.log("======================Swithcing PPM action completed successfully======================",true);
	}
	@Parameters({"text"})
	@Test(description = "Validating switching from PPM to PO")
	public void switchfromPOtoPPM(String text) throws Exception {
		Reporter.log("======================Performing switching PO action======================",true);
		loginPage.switchfromPOtoPPM(text);
		Reporter.log("======================Swithcing PO action completed successfully======================",true);
	}
	@Test(description = "Validating Logout action")
	public void LogOut() throws Exception {
		Reporter.log("======================Performing logout action======================",true);
		loginPage.logout();
		Reporter.log("======================logout completed successfully======================",true);
	}
	//################################ ForgetPassword Testcases ################################
	
	@Test(description = "validating the forget password link")
	public void forgetPasswordlink() throws Exception {
		Reporter.log("======================Clicking hte forgetPassword link======================",true);
		loginPage.forgetPasswordlink();
		Reporter.log("======================forget password link action completed successfully======================",true);
	}
	@Parameters({"username","actualerrormessage"})
	@Test(description = "Validating UserName with Empty value")
	public void forgetPasswordUserNameWithEmptyValue(String username,String actualerrormessage) throws Exception {
		Reporter.log("======================Username Name with empty value======================",true);
		loginPage.forgetPasswordUserNameWithEmptyValue(username,actualerrormessage);
		Reporter.log("======================UserName text box validation completed successfully======================",true);
	}
	@Parameters({"username","actualerrormessage"})
	@Test(description = "Validating UserName with Invalid value")
	public void forgetPasswordUserNameWithInvalidValue(String username,String actualerrormessage) throws Exception {
		Reporter.log("======================Username Name with Invalid value======================",true);
		loginPage.forgetPasswordUserNameWithEmptyValue(username,actualerrormessage);
		Reporter.log("======================UserName text box validation completed successfully======================",true);
	}
	@Test(description = "validating the Login link in forget password page")
	public void forgetPasswordPageLoginbutton() throws Exception {
		Reporter.log("======================Clicking the Login link======================",true);
		loginPage.forgetPasswordPageLoginbutton();
		Reporter.log("======================Login link action completed successfully======================",true);
	}
	@Parameters({"username","actualerrormessage"})
	@Test(description = "Validating UserName with valid value")
	public void forgetPasswordUserNameWithvalidUnregisteredValue(String username,String actualerrormessage) throws Exception {
		Reporter.log("======================Username Name with Invalid value======================",true);
		loginPage.forgetPasswordUserNameWithEmptyValue(username,actualerrormessage);
		Reporter.log("======================UserName text box validation completed successfully======================",true);
	}
	@Parameters({"username"})
	@Test(description = "Updating the password")
	public void updateforgetpassword(String username) throws Exception {
		Reporter.log("======================updating the username======================",true);
		loginPage.updateforgetpassword(username);
		Reporter.log("======================updated username successfully======================",true);
	}
	@Parameters({"username","actualerrormessage"})
	@Test(description = "validating the login with old password after changed the password")
	public void validateloginwitholdpassword(String username,String actualerrormessage) throws Exception {
		Reporter.log("======================validating the login with old password ======================",true);
		loginPage.validateloginwitholdpassword(username,actualerrormessage);
		Reporter.log("======================validated the login with old password successfully======================",true);
	}
	@Parameters({"username"})
	@Test(description = "validating the login with new password after changed the password")
	public void validateloginwithnewpassword(String username) throws Exception {
		Reporter.log("======================validating the login with new password======================",true);
		loginPage.validateloginwithnewpassword(username);
		Reporter.log("======================validated the login with new password successfully======================",true);
	}
	@Parameters({"username","actualerrormessage"})
	@Test(description = "validating the already used reset password link its allowing to change password")
	public void changepasswordusingalreadyusedresetpasswordlink(String username,String actualerrormessage) throws Exception {
		Reporter.log("======================validating the already used reset password link its allowing to change password======================",true);
		loginPage.changepasswordusingalreadyusedresetpasswordlink(username,actualerrormessage);
		Reporter.log("======================change password using the used reset password link action completed successfully======================",true);
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

