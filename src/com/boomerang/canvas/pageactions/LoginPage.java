package com.boomerang.canvas.pageactions;

import org.testng.Assert;
import org.testng.Reporter;
import com.boomerang.canvas.testbase.Testbase;

public class LoginPage extends Testbase{

	public void login(String username,String password,String actualErrMsg) throws Exception {
		getobject("txtbox_username").sendKeys(username);
		getobject("txtbox_password").sendKeys(password);
		getobject("btn_login").click();
	//	Thread.sleep(2000);
		if(actualErrMsg ==""){
			Assert.assertEquals("glyphicon glyphicon-off", getobject("link_logout").getAttribute("class"));
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