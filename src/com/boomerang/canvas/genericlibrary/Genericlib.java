package com.boomerang.canvas.genericlibrary;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.boomerang.canvas.suite.Testsuite;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;

public class Genericlib extends Testbase{
	ReadTestCase RTC = new ReadTestCase();
	 DecimalFormat format = new DecimalFormat();
		
	//################################ Generic methods ####################################
		public double getresponsesintdata(String filename,String metrix,String value){
			double REVENUE = 0;
			String jsonFile = Readjson.readFileAsString(workspace+filename);
			RTC.dataparser(jsonFile);
			JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
			JSONArray repo = temp1.getJSONArray("data");
			for(int n = 0; n < repo.length(); n++)
			{
			    JSONObject r = repo.getJSONObject(n);
			    JSONObject repo1 = r.getJSONObject(metrix);
				REVENUE=repo1.getDouble(value);
			}
			return REVENUE;
		}
		public String getresponsesStringdata(String filename,String metrix,String value){
			String REVENUE = "";
			String jsonFile = Readjson.readFileAsString(workspace+filename);
			RTC.dataparser(jsonFile);
			JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
			JSONArray repo = temp1.getJSONArray("data");
			for(int n = 0; n < repo.length(); n++)
			{
			    JSONObject r = repo.getJSONObject(n);
			    JSONObject repo1 = r.getJSONObject(metrix);
				REVENUE=repo1.getString(value);
			}
			return REVENUE;
		}
		public void getdata(List<WebElement> listvalues,String filename,String metrix,String metrixvalue){
			String actuallist="";
			String expectedlist="";
			outer :
			for(WebElement list:listvalues){	
			List<String> categories=getresponsesdata(filename,metrix,metrixvalue);
			for(String l:categories){
				if(list.getText().toLowerCase().contains(l.toLowerCase())){
					actuallist = list.getText().toLowerCase();
					expectedlist = l.toLowerCase();
					Testbase.test.log(LogStatus.INFO,"","Actual categories data : " +actuallist);
					Testbase.test.log(LogStatus.INFO,"","Expected categories data : " +expectedlist);
					Assert.assertEquals(actuallist, expectedlist);
					break outer;
			}
			}
			
				}	
		}
		public void getlist(List<WebElement> listvalues,String filename,String metrix,String metrixvalue) throws Exception{
			String d="";
			String value1="";
			outer :
				for(WebElement list:listvalues)
				{
					String c="";	
				String a=list.getText();
				String []b=a.split(" ");
				if(b[1].contains("K"))
				{
					c=b[1].replace("K", "");
				}
				else if(b[1].contains("M")){
					c=b[1].replace("M", "");
				}
				else{
					c=b[1];
				}
				d=c.trim();
					List<Double> value=getresponseslistintdata(filename,metrix,metrixvalue);
					for(Double l:value){
						value1=getBigmoversYOYandPVPvalues(l);
						if(c.trim().contains(value1)){
							Testbase.test.log(LogStatus.INFO,"","Actual data : " +d);
							Testbase.test.log(LogStatus.INFO,"","Expected data : " +value1);
							
							break outer;
						}
					}
					Assert.assertEquals(d, value1);
				}
		}
		public List<String> getresponsesdata(String filename,String metrix,String value){
			String	jsonFile = Readjson.readFileAsString(workspace+filename);
			List<String> list=new ArrayList<>();
			
			RTC.dataparser(jsonFile);
			JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
			JSONArray repo = temp1.getJSONArray("data");
			for(int n = 0; n < repo.length(); n++)
			{
			    JSONObject r = repo.getJSONObject(n);
			    JSONObject repo1 = r.getJSONObject(metrix);
				String REVENUE=repo1.getString(value);
				list.add(REVENUE);
			}
			return list;
		}
		public List<Double> getresponseslistintdata(String filename,String metrix,String value){
			String	jsonFile = Readjson.readFileAsString(workspace+filename);
			List<Double> list=new ArrayList<>();
			double REVENUE;
			RTC.dataparser(jsonFile);
			JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
			JSONArray repo = temp1.getJSONArray("data");
			for(int n = 0; n < repo.length(); n++)
			{
			    JSONObject r = repo.getJSONObject(n);
			    JSONObject repo1 = r.getJSONObject(metrix);
			    REVENUE=repo1.getDouble(value);
				list.add(REVENUE);
			}
			return list;
		}
		public void getYOYandPVPvalues(String filename,String metrix,String value1,String value2) throws Exception{
			String b="";
			if(metrix.contains("YOY") || metrix.contains("PVP")){
			String []a=metrix.split(" ");
			if(a[0].contains("▼")){
			b=a[0].replace("▼", "");
			}
			else{
				b=a[0].replace("▲", "");
			}
			}
			else{
				String a=metrix;
				if(a.contains("▼")){
				b=a.replace("▼", "");
				}
				else{
					b=a.replace("▲", "");
				}
			}
			System.out.println("test : " + Testsuite.test);
			Testbase.test.log(LogStatus.INFO,"", "Actual value : " + b.trim());
			String resyoy=getresponsesStringdata(filename,value1,value2);
			double value = Double.parseDouble(resyoy.replace("-", "").trim());
			String resrevenue1=truncate(value);
			Testbase.test.log(LogStatus.INFO,"", "Expected value : " + resrevenue1+"%");
			Assert.assertEquals(b.trim(), resrevenue1+"%");
		}
		public String getBigmoversYOYandPVPvalues(Double metrix) throws Exception{
			double value = Double.parseDouble(metrix.toString().replace("-", "").trim());
			String resrevenue1;
			if(value>=1000 && value<=99999){
			resrevenue1=truncate(value/1000);
			}
			else if(value>=99999){
				resrevenue1=truncate(value/1000000);
			}
			else{
				resrevenue1=truncate(value);
			}
			return resrevenue1;
			}
			
		public String truncate(Double responsedata) throws Exception{
			Double toBeTruncated = new Double(responsedata);
		    Double resrevenue1=new BigDecimal(toBeTruncated ).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	        String formattedvalue = format.format(resrevenue1);
	        return formattedvalue;
		}
}
