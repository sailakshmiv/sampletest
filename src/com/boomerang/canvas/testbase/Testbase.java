package com.boomerang.canvas.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Testbase {
    public static WebDriver driver;	
	public static Properties prop = new Properties();
	public static File f;
	public static FileInputStream fr;
	public static FileOutputStream fos;
	public static String workspace =System.getProperty("user.dir");
	public static ExtentReports report;
	public static ExtentTest test;
	
public WebDriver getBrowser(String browser,String os) throws IOException{
	
		if(browser.toLowerCase().equals("chrome") && os.toLowerCase().equals("mac")){
			ChromeOptions chromeOptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", workspace+"/src/com/boomerang/canvas/drivers/chromedriver");
			driver= new ChromeDriver(chromeOptions);
			return driver;
		}
		else if(browser.toLowerCase().equals("chrome") && os.toLowerCase().equals("windows")){
			System.out.println("hello.................");
			ChromeOptions chromeOptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", workspace+"/src/com/boomerang/canvas/drivers/chromedriver.exe");
			driver= new ChromeDriver(chromeOptions);
			return driver;
		}
		else{
		DesiredCapabilities desiredCapabilities = DesiredCapabilities.firefox();
		driver = new FirefoxDriver(desiredCapabilities);
		return driver;
		}
		
	}
	 
public static void loadfile() throws IOException{
	f=new File(workspace+"/src/com/boomerang/canvas/config/config.properties");
	fr=new FileInputStream(f);
	prop.load(fr);
	f=new File(workspace+"/src/com/boomerang/canvas/pagelocators/loginpage.properties");
	fr=new FileInputStream(f);
	prop.load(fr);
	f=new File(workspace+"/src/com/boomerang/canvas/pagelocators/headerwidget.properties");
	fr=new FileInputStream(f);
	prop.load(fr);
	f=new File(workspace+"/src/com/boomerang/canvas/pagelocators/scoreboardwidget.properties");
	fr=new FileInputStream(f);
	prop.load(fr);
	f=new File(workspace+"/src/com/boomerang/canvas/pagelocators/bigmovers.properties");
	fr=new FileInputStream(f);
	prop.load(fr);
}
public static List<WebElement> getLocators(String locator) throws Exception{
	String locatortype=locator.split("##")[0];
	String locatorvalue=locator.split("##")[1];
	if(locatortype.toLowerCase().equals("id")){
		return  driver.findElements(By.id(locatorvalue));
	}
	else if(locatortype.toLowerCase().equals("classname") || locatortype.toLowerCase().equals("class")){
		return driver.findElements(By.className(locatorvalue));
	}
	else if(locatortype.toLowerCase().equals("name")){
		return driver.findElements(By.name(locatorvalue));
	}
	else if(locatortype.toLowerCase().equals("tagname") || locatortype.toLowerCase().equals("tag")){
		return driver.findElements(By.tagName(locatorvalue));
	}
	else if(locatortype.toLowerCase().equals("css")){
		return driver.findElements(By.cssSelector(locatorvalue));
	}
	else if(locatortype.toLowerCase().equals("linktext")){
		return driver.findElements(By.linkText(locatorvalue));
	}
	else if(locatortype.toLowerCase().equals("partiallinktext")){
		return driver.findElements(By.partialLinkText(locatorvalue));
	}
	else if(locatortype.toLowerCase().equals("xpath")){
		return driver.findElements(By.xpath(locatorvalue));
	}
	else
	{
		throw new Exception("Unknown locator type '" + locatortype +'"');
	}
	
}
public static WebElement getLocator(String locator) throws Exception{
	WebDriverWait wait=new WebDriverWait(driver,1000);
	String locatortype=locator.split("##")[0];
	String locatorvalue=locator.split("##")[1];
	if(locatortype.toLowerCase().equals("id")){
		return  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorvalue)));
	}
	else if(locatortype.toLowerCase().equals("classname") || locatortype.toLowerCase().equals("class")){
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(locatorvalue)));
	}
	else if(locatortype.toLowerCase().equals("name")){
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorvalue)));
	}
	else if(locatortype.toLowerCase().equals("tagname") || locatortype.toLowerCase().equals("tag")){
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(locatorvalue)));
	}
	else if(locatortype.toLowerCase().equals("css")){
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locatorvalue)));
	}
	else if(locatortype.toLowerCase().equals("linktext")){
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locatorvalue)));
	}
	else if(locatortype.toLowerCase().equals("partiallinktext")){
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(locatorvalue)));
	}
	else if(locatortype.toLowerCase().equals("xpath")){
		return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorvalue)));
	}
	else
	{
		throw new Exception("Unknown locator type '" + locatortype +'"');
	}
	
}
public static WebElement getobject(String locator) throws Exception{
	return getLocator(prop.getProperty(locator));
}
public static List<WebElement> getobjects(String locator) throws Exception{
	return getLocators(prop.getProperty(locator));
}
public void threadwait() throws InterruptedException{
	Thread.sleep(1000);
}

public void implicitwait(){
	driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
}
}
