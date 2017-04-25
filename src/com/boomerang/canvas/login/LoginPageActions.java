package com.boomerang.canvas.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Address;
import javax.mail.Message;
import org.testng.Assert;
import org.testng.Reporter;
import com.boomerang.canvas.genericlibrary.Genericlib;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;


public class LoginPageActions extends Testbase{
	String parentwindow;
	String childwindow;
	String url=null;
	static FileInputStream fs;
	FileOutputStream fos;
	static Properties prop1=new Properties();
	File f1;
	public static String oldpassword;
	
	
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
	public void updateforgetpassword(String mail) throws Exception {
		String password="Boomerang";
		getobject("forget_Password_link").click();
		getobject("forget_password_username").sendKeys(mail);
		getobject("forget_password_recoverybutton").click();
		implicitwait();
		threadwait();
		fs = new FileInputStream(workspace + "/src/com/boomerang/canvas/login/forgetpassword.properties");
		prop1.load(fs);
		oldpassword=prop1.getProperty("password");
	    fos = new FileOutputStream(workspace + "/src/com/boomerang/canvas/login/forgetpassword.properties");
	     
	   
		Genericlib email = new Genericlib();
		// String url=null;
	     List<Message> messages =
	     email.readMails("testingfp@boomerangcommerce.com", "Boomerang123$", 1, "Inbox");
	   System.out.println("messages :" + messages);
	     for (Message message : messages) {
	     Address[] from = message.getFrom();
	     System.out.println("-------------------------------");
	     System.out.println("Date : " + message.getSentDate());
	     System.out.println("From : " + from[0]);
	     System.out.println("Subject: " + message.getSubject());
	     //System.out.println("Content :" +(String) message.getContent());
	     System.out.println("--------------------------------");
	    System.out.println(email.processContent(message));
	    	String[] a=email.processContent(message).split("<a");
	    	for(int i=0;i<a.length;i++){
	    	Pattern p = Pattern.compile("href=\"(.*?)\"");
	    	System.out.println("p :" +p);
	    	Matcher m = p.matcher(a[2]);
	    	System.out.println("m :" +m);
	    	if (m.find()) {
	    	    url = m.group(1); // this variable should contain the link URL
	    	}
	    }
	    	System.out.println("url :" + url);
	     }
	     prop1.setProperty("url", url);
	     driver.navigate().to(url);
	     Date d=new Date();
	     int minutes=d.getMinutes();
	     String password1=password+"-"+minutes;
	     
	     System.out.println("oldpassword :" + oldpassword);
	     prop1.setProperty("password", password1);
	     
	     prop1.store(fos, "new password");
	     fos.flush();
	      fs.close();
	      
	      System.out.println("newpassword :" + password1);
	     getobject("forget_password_password").sendKeys(password1);
	     getobject("forget_password_confirmpassword").sendKeys(password1);
	     getobject("for_password_submissionbutton").click();
	     Testbase.test.log(LogStatus.INFO,"", "Password changed sucussfully");
	     System.out.println("newpassword :" + password1);
	     email.deleteMessages("testingfp@boomerangcommerce.com", "Boomerang123$", "Reset Account | Boomerang Commerce Platform");
	  }
	public void validateloginwitholdpassword(String mail,String actualmessage) throws Exception {
		driver.quit();
		Testbase tb=new Testbase();
		tb.getBrowser(System.getProperty("Browser"),System.getProperty("os.name"));
		driver.get(System.getProperty("ClientURL"));
		 System.out.println("newpassword :" + oldpassword);
		String password1=oldpassword;
		login(mail, password1, actualmessage);
	  }
	public void validateloginwithnewpassword(String mail) throws Exception {
		fs = new FileInputStream(workspace + "/src/com/boomerang/canvas/login/forgetpassword.properties");
		prop1.load(fs);
		driver.get(System.getProperty("ClientURL"));
		String newpassword=prop1.getProperty("password");
		System.out.println("newpassword" + newpassword );
		login(mail,newpassword, "");
	  }
	public void changepasswordusingalreadyusedresetpasswordlink(String mail,String errormessage) throws Exception {
		fs = new FileInputStream(workspace + "/src/com/boomerang/canvas/login/forgetpassword.properties");
		prop1.load(fs);
		driver.navigate().to(prop1.getProperty("url"));
		String actaulerror=getobject("forget_password_link_error").getText();
		Assert.assertEquals(actaulerror, errormessage);
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
	
/*public void Favoritesbutton() {
		try{
			WebElement element= getobject("landingpage_favorites_button");
			if (element.isDisplayed() && element.isEnabled()) {
			    element.click();
			    Assert.fail("Favorites button is in enabled state");
			}
			else{
				Assert.assertTrue(true,"Favorites button is in disabled state");
			}
		}
		catch(Exception e){
			e.getMessage();
		}
	}
public void Alertsbutton() {
			try{
				WebElement element=getobject("landingpage_alerts_button");
				if (element.isDisplayed() && element.isEnabled()) {
				    element.click();
				    Assert.fail("Alerts button is in enabled state");
				}
				else{
					Assert.assertTrue(true,"Alerts button is in disabled state");
				}
			}
			catch(Exception e){
				e.getMessage();
			}
			
}*/
}