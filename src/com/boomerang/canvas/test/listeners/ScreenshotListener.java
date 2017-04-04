package com.boomerang.canvas.test.listeners;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.boomerang.canvas.suite.Testsuite;
import com.boomerang.canvas.testbase.Testbase;

public class ScreenshotListener extends Testbase implements ITestListener {

	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
	static String screenload ;
	static String screenget = "";

	@Override
	public void onTestStart(ITestResult result) {
	}

	@Override
	public void onTestSuccess(ITestResult result) {
	}

	@Override
	public void onTestFailure(ITestResult result) {
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
	}

	public String takeScreenshot(ITestResult result,String screenshot ) throws IOException {
		String image = "";
			loadfile();
			screenload= System.getProperty("user.dir") + "/test-output"+Testsuite.timeStamp+"/html/";
			if (driver != null) {
				String bucket = prop.getProperty("screenshotBucketName");
				String folder = prop.getProperty("screenshotFolderName");
				String fileName = folder + "/" + format.format(new Date()) + "_" + prop.getProperty("client") + "_" + prop.getProperty("browser") + "_"
						+ result.getTestClass().getRealClass().getSimpleName() + "_" + result.getName() + ".jpg";

				try {
					File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile, new File(screenload + screenshot + ".png"));
					System.setProperty("org.uncommons.reportng.escape-output", "false");
					 image = screenget + screenshot + ".png";
					Reporter.log("<a title= \"title\" href=\"" + image + "\">" + "<img width=\"700\" height=\"550\" src=\"" + image
							+ "\"></a>");
		/*			AmazonS3 s3 = new AmazonS3Client(new BasicAWSCredentials(prop.getProperty("awsAccessKey"),
							prop.getProperty("awsSecretKey")));
					PutObjectRequest req = new PutObjectRequest(bucket, fileName, scrFile);

					Object[] params = result.getParameters();

					if (params != null && params.length > 0) {
						ObjectMetadata metadata = new ObjectMetadata();
						for (int i = 0; i < params.length; i++) {
							metadata.addUserMetadata(String.valueOf(i + 1), String.valueOf(params[i]));
						}
						req.setMetadata(metadata);
					}
					req.setCannedAcl(CannedAccessControlList.PublicRead);
					s3.putObject(req);*/
				} catch (Exception e) {
				}
			}
			return image;
		}
	
	}
