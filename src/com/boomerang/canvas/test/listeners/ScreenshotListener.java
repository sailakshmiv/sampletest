package com.boomerang.canvas.test.listeners;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.boomerang.canvas.suite.Testsuite;
import com.boomerang.canvas.testbase.Testbase;

public class ScreenshotListener extends Testbase implements ITestListener {

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

	public String takeScreenshot(ITestResult result,String screenshot ) throws IOException, InterruptedException {
		String image = "";
		Thread.sleep(6000);
			loadfile();
			screenload = workspace + "/"+System.getProperty("Suitename")+"-Suite" + Testsuite.timeStamp + "-"
					+ System.getProperty("Client") + "/html/";
			if (driver != null) {
				try {
					File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
					FileUtils.copyFile(scrFile, new File(screenload + screenshot + ".png"));
					System.setProperty("org.uncommons.reportng.escape-output", "false");
					 image = screenget + screenshot + ".png";
					Reporter.log("<a title= \"title\" href=\"" + image + "\">" + "<img width=\"700\" height=\"550\" src=\"" + image
							+ "\"></a>");
				} catch (Exception e) {
				}
			}
			return image;
		}
	public void upLoaadReport() throws IOException {
		AmazonS3 s3 = new AmazonS3Client();
		String bucketName = "boomerang-qa-report";
		String folderName = System.getProperty("Suitename")+"-Suite" + Testsuite.timeStamp + "-" + System.getProperty("Client");//"test-output2017.04.05-internal3";
		
		File outputfolder = new File(
				workspace +"/"+ folderName);
	//	deleteFolder(bucketName, folderName, s3);
		File[] listOfFiles = outputfolder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
		createFolder(bucketName, folderName, s3);
		
		String fileName = folderName + "/" + listOfFiles[i].getName();
		s3.putObject(new PutObjectRequest("boomerang-qa-report", fileName, 
				new File(workspace+"/"+folderName+"/"+listOfFiles[i].getName()))
				.withCannedAcl(CannedAccessControlList.PublicRead));
			}
			else if (listOfFiles[i].isDirectory()) {
			System.out.println("Directory " + listOfFiles[i].getName());
			File[] listOfFiles1 = listOfFiles[i].listFiles();
			for (int j = 0; j < listOfFiles1.length; j++) {
				if (listOfFiles1[j].isFile()) {
					System.out.println("File " + listOfFiles1[j].getName());
					String foldername=folderName+"/"+listOfFiles[i].getName();
					createFolder(bucketName,foldername , s3);
					String fileName = folderName + "/" + listOfFiles[i].getName()+"/"+listOfFiles1[j].getName();
					s3.putObject(new PutObjectRequest(bucketName, fileName, 
							new File(workspace+"/"+folderName+"/"+listOfFiles[i].getName()+"/"+listOfFiles1[j].getName()))
							.withCannedAcl(CannedAccessControlList.PublicRead));
				
				}
		
			else if (listOfFiles1[j].isDirectory()) {
				System.out.println("Directory " + listOfFiles1[j].getName());
				File[] listOfFiles2 = listOfFiles1[j].listFiles();
				for (int k = 0; k < listOfFiles2.length; k++) {
					if (listOfFiles2[k].isFile()) {
						System.out.println("File " + listOfFiles2[k].getName());
						String foldername=folderName+"/"+listOfFiles[i].getName()+"/"+listOfFiles1[j].getName();
						createFolder(bucketName,foldername , s3);
						String fileName = folderName + "/" + listOfFiles[i].getName()+"/"+listOfFiles1[j].getName()+"/"+listOfFiles2[k].getName();
						s3.putObject(new PutObjectRequest(bucketName, fileName, 
								new File(workspace+"/"+folderName+"/"+listOfFiles[i].getName()+"/"+listOfFiles1[j].getName()+"/"+listOfFiles2[k].getName()))
								.withCannedAcl(CannedAccessControlList.PublicRead));
					} else if (listOfFiles2[k].isDirectory()) {
						System.out.println("Directory " + listOfFiles2[k].getName());
					}
				}
			}
		}
		}
		}
	}
	public static void deleteFolder(String bucketName, String folderName, AmazonS3 client) {
		List<S3ObjectSummary> fileList = 
				client.listObjects(bucketName, folderName).getObjectSummaries();
		for (S3ObjectSummary file : fileList) {
			client.deleteObject(bucketName, file.getKey());
		}
		client.deleteObject(bucketName, folderName);
	}

	public static void createFolder(String bucketName, String folderName, AmazonS3 client) {
		// create meta-data for your folder and set content-length to 0
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		// create empty content
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
		// create a PutObjectRequest passing the folder name suffixed by /
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
				folderName + "/", emptyContent, metadata);
		// send request to S3 to create folder
		client.putObject(putObjectRequest);
	}

	
	}
