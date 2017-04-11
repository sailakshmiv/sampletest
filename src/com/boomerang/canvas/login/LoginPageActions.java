package com.boomerang.canvas.login;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import com.boomerang.canvas.testbase.Testbase;

public class LoginPageActions extends Testbase{
	String parentwindow;
	String childwindow;
	public void login(String username,String password,String actualErrMsg) throws Exception {
		getobject("txtbox_username").sendKeys(username);
		getobject("txtbox_password").sendKeys(password);
		getobject("btn_login").click();
		
		if(actualErrMsg ==""){
			Assert.assertEquals("glyphicon glyphicon-off ng-scope", getobject("link_logout").getAttribute("class"));
		}
		else{
			Assert.assertEquals(actualErrMsg, getobject("txt_login_err_msg").getText());
		}
	}

	public void logout() {
		try{
		getobject("link_logout").click();
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	
	public void switchfromPPMtoPO(String text) {
		try{
		getobject("switch_link_PPM").click();
		threadwait();
		Set<String> allWindowHandles= driver.getWindowHandles();
		Iterator<String> iterator=allWindowHandles.iterator();
		parentwindow=(String) iterator.next();
		childwindow=(String) iterator.next();
		threadwait();
		driver.switchTo().window(childwindow);
		threadwait();
		Assert.assertEquals(text, getobject("po_strategy_dashboard_title").getText());
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	public void switchfromPOtoPPM(String text) {
		try{
			getobject("switch_link_PO").click();
			getobject("switch_link_PO_child").click();
			Set<String> allWindowHandles= driver.getWindowHandles();
			Iterator<String> iterator=allWindowHandles.iterator();
			parentwindow=(String) iterator.next();
			childwindow=(String) iterator.next();
			String childwindow1=(String) iterator.next();
			threadwait();
			driver.switchTo().window(childwindow1);
			threadwait();
			Assert.assertEquals(text, getobject("login_margin_text").getText());
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	public void rememberMe(String text) {
		try{
			getobject("switch_link_PO").click();
			getobject("switch_link_PO_child").click();
			Assert.assertEquals(text, getobject("login_margin_text").getText());
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	public void forgetPasswordlink() {
		try{
			getobject("forget_Password_link").click();
			Assert.assertEquals("loginButton", getobject("forget_password_recoverybutton").getAttribute("class"));
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	public void forgetPasswordUserNameWithEmptyValue(String text,String errormessage) {
		try{
			getobject("forget_password_username").sendKeys(text);
			getobject("forget_password_recoverybutton").click();
			Assert.assertEquals(errormessage, getobject("forget_password_error").getText());
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	public void forgetPasswordUserNameWithValidUserName(String text,String errormessage) {
		try{
			getobject("forget_Password_link").click();
			getobject("forget_password_username").sendKeys(text);
			getobject("forget_password_recoverybutton").click();
			Assert.assertEquals(errormessage, getobject("forget_password_successfulMessage").getText());
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	public void forgetPasswordPageLoginbutton() {
		try{
			getobject("forget_password_homebutton").click();
			Assert.assertEquals("loginButton", getobject("forget_Password_link").getAttribute("class"));
		}
		catch(Exception e){
			e.getMessage();
		}
	}
	/**
	 * invalidLoginWithAccou : Performs 5 invalid login attempts and verifies
	 * the account lock error message
	 * 
	 * @param username
	 * @param password
	 * @param wait
	 * @param expectedErrMsg
	 * @throws Exception 
	 */
	public void invalidLoginWithAccountLock(String username, String password, String actualErrMsg)
			throws Exception {

		int invalidLoginRetries = 1;

		while (invalidLoginRetries <= 5) {
			Reporter.log("Performing login to application with invalid credentials [" + invalidLoginRetries + "]",
					true);

			login(username, password, actualErrMsg);
			invalidLoginRetries++;
		}

	Assert.assertEquals(actualErrMsg, getobject("txt_login_err_msg").getText());
	}
	

}