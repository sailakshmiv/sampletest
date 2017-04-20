package com.boomerang.canvas.headerwidget;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.json.JSONException;
import org.testng.Assert;
import org.testng.Reporter;

import com.boomerang.canvas.genericlibrary.Genericlib;
import com.boomerang.canvas.genericlibrary.ReadInputdata;
import com.boomerang.canvas.genericlibrary.Readjson;
import com.boomerang.canvas.genericlibrary.ResponseBody;
import com.boomerang.canvas.genericlibrary.RestCaller;
import com.boomerang.canvas.testbase.Testbase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.relevantcodes.extentreports.LogStatus;

public class Headerwidgetapi extends Testbase{
	Genericlib gl=new Genericlib();
	DecimalFormat format = new DecimalFormat();
	public void getHeaderdata_from_api(String jsonfile,String filename,String inputfilename) throws JSONException {
		
	String endPoint=System.getProperty("Client");
	ReadInputdata RTC = new ReadInputdata();
	RestCaller rc = new RestCaller();
	ResponseBody responseBody;
	String jsonFile;
	
		String apiUrl = "http://po-"+endPoint + ":8080/po-client/dashboard/"+filename;
		jsonFile = Readjson.readFileAsString(workspace + "/src/com/boomerang/canvas/payload/"
				+ inputfilename+".json");
		
		try {
			RTC.dataparser(jsonFile);
			responseBody = rc.httpPost(apiUrl, RTC.getInput(), "JSON");
			int statusCompare = responseBody.getResponseCode() == 200
					|| responseBody.getResponseCode() == 202 ? 0 : 1;
			if (statusCompare == 0) {
			      //convert java object to JSON format
				  String json = responseBody.getResponseBody();
				JsonParser parser = new JsonParser();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonElement el = parser.parse(json);
				json = gson.toJson(el);
				File file = new File(workspace + jsonfile);
				if (file.delete()) {
				} else {
				}
				file.createNewFile();
			      FileOutputStream fos = new FileOutputStream(workspace + jsonfile);
			      String startString="{\n";
			      String startString1="\"output\":";
			      String endString="\n}";
			      byte[] contentInBytes = json.getBytes();
			      byte[] contentInBytes1 = startString.getBytes();
			      byte[] contentInBytes2 = startString1.getBytes();
			      byte[] contentInBytes3 = endString.getBytes();
			      fos.write(contentInBytes1);
			      fos.write(contentInBytes2);
					fos.write(contentInBytes);
					fos.write(contentInBytes3);
			      fos.flush();
			      fos.close();

			}
			Reporter.log("Resource URI : "+apiUrl);
			Reporter.log("Expected Status =  200");
			Reporter.log("Response Status = " + responseBody.getResponseCode());
			Reporter.log("Response body: = " + responseBody.getResponseBody());
			}catch(Exception e)
		{
			e.printStackTrace();
			Assert.fail(e.getLocalizedMessage(), e.getCause());
		}
	}
	public void getYOYandPVPforheaderwidgetvalues(String filename,String metrix,String value1,String value2) throws Exception{
		Testbase.test.log(LogStatus.INFO,"", "Actual value : " + metrix);
		String resyoy=gl.getresponsesStringdata(filename,value1,value2);
		double resyoy1 = Double.parseDouble(resyoy);
		String value;
		if(resyoy.contains("-")){
		value=getHeaderLosersYOYandPVPvalues(resyoy1,value1);
		}
		else if(resyoy.equals("0")){
			value="▼" + "\n"+"0"+"%" +" "+ value1;
		}
		
		else{
		value=getHeaderWinnersYOYandPVPvalues(resyoy1,value1);
		}
		Testbase.test.log(LogStatus.INFO,"", "Expected value : " + value);
		Assert.assertEquals(metrix, value);
	}
	public String getHeaderLosersYOYandPVPvalues(Double metrix,String metrix1) throws Exception{
		Double value;
		String resrevenue1;
		Double value1 = null;
		if(metrix.toString().contains("E8")){
		value=Double.parseDouble(metrix.toString().replace("-", "").replaceAll(",", "").trim());
		value1=(double)value.longValue();
		}
		else if(metrix.toString().contains("E7")){
			value=Double.parseDouble(metrix.toString().replace("-", "").replaceAll(",", "").trim());
			value1=(double)value.longValue();
			}
		else{
		value1=Double.parseDouble(metrix.toString().replace("-", "").replaceAll(",", "").trim());
		
		}
		 resrevenue1 =conversioninpercent(value1);
			resrevenue1="▼" + "\n"+resrevenue1+"%" +" "+ metrix1;
		
		return resrevenue1;
}
	public String getHeaderWinnersYOYandPVPvalues(Double metrix,String metrix1) throws Exception{
		Double value;
		String resrevenue1;
		Double value1 = null;
		if(metrix.toString().contains("E8")){
		value=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
		value1=(double)value.longValue();
		}
		else if(metrix.toString().contains("E7")){
			value=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
			value1=(double)value.longValue();
			}
		else{
		value1=Double.parseDouble(metrix.toString().replace("-", "").replaceAll(",", "").trim());
		
		}
		 resrevenue1 =conversioninpercent(value1);
		 
			resrevenue1= "▲" + "\n"+resrevenue1+"%" +" "+ metrix1;
		return resrevenue1;
}
	public String truncate(Double l) throws Exception{
		
		Double toBeTruncated = new Double(l);
	    Double resrevenue1=new BigDecimal(toBeTruncated ).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        String formattedvalue = format.format(resrevenue1);
        double value = Double.parseDouble(formattedvalue);
		String number = String.valueOf(value);
		String formattedvalue1 = number.substring(number.indexOf(".")).substring(1);
		int x=formattedvalue1.length();
		if(x==1){
			formattedvalue=formattedvalue+"0";
		}
        return formattedvalue;
	}
	public String conversioninpercent(Double value1) throws Exception{
		String resrevenue1;
		if(value1>=0 && value1<1000){
			resrevenue1=truncate(value1);
		}
		else if(value1>=1000 && value1<1000000){
			resrevenue1=truncate(value1/1000) + "%";
			}
			else if(value1>=1000000 && value1<1000000000){
				resrevenue1=truncate(value1/1000000) + "%";
			}
			else{
				resrevenue1=truncate(value1/1000000000) + "%";
			}
		return resrevenue1;
	}
}
