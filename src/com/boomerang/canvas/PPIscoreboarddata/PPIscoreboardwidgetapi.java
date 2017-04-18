package com.boomerang.canvas.PPIscoreboarddata;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.boomerang.canvas.genericlibrary.Genericlib;
import com.boomerang.canvas.genericlibrary.ReadOutputdata;
import com.boomerang.canvas.genericlibrary.Readjson;
import com.boomerang.canvas.headerwidget.Headerwidgetapi;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;

public class PPIscoreboardwidgetapi extends Testbase{
	Genericlib gl=new Genericlib();
	Headerwidgetapi hapi=new Headerwidgetapi();
	ReadOutputdata RTC = new ReadOutputdata();
	
	public void getHeaderdata_from_api(String jsonfile,String filename,String inputfilename) throws JSONException {
		hapi.getHeaderdata_from_api(jsonfile,filename,inputfilename);
	}
	public List<String> getresponsesListStringdata(String filename,String metrix,String value){
		List<String> listval=new ArrayList<String>();
		String REVENUE = "";
		String jsonFile = Readjson.readFileAsString(workspace+filename);
		RTC.dataparser(jsonFile);
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    JSONObject repo2 = null;
		    try{
			    boolean repo1 = r.isNull(metrix);
			    if(!repo1){
			    	repo2 = r.getJSONObject(metrix);
			    }
			    else{
			    	repo2=null;
			    }
		    boolean b=repo2.isNull(value);
			    if(!b){
				REVENUE=repo2.getString(value);
				listval.add(REVENUE);
			    }
			    else{
			    	REVENUE=null;
			    	listval.add(REVENUE);
			    }
			    }catch(Exception e){
			    	e.getMessage();
			    }
		}
		return listval;
	}
public void getdata(List<WebElement> listvalues,String filename,String metrix,String metrixvalue){
		String actuallist="";
		String expectedlist="";
		
		List<String> categories=getresponsesListStringdata(filename,metrix,metrixvalue);
		
		for(String l:categories){
			outer:
			for(WebElement list:listvalues){
			actuallist = list.getText().toLowerCase();
			expectedlist = l.toLowerCase();
			if(list.getText().toLowerCase().contains(l.toLowerCase())){
				actuallist = list.getText().toLowerCase();
				expectedlist = l.toLowerCase();
				Testbase.test.log(LogStatus.INFO,"","Actual data : " +actuallist);
				Testbase.test.log(LogStatus.INFO,"","Expected data : " +expectedlist);
				break outer;
				}
					else{
						continue;
					}
				}
		Assert.assertEquals(actuallist, expectedlist);
			}	
	}
public List<Double> getresponsesListIntdata(String filename,String metrix,String value){
	List<Double> listval=new ArrayList<Double>();
	Double REVENUE;
	String jsonFile = Readjson.readFileAsString(workspace+filename);
	RTC.dataparser(jsonFile);
	JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
	JSONArray repo = temp1.getJSONArray("data");
	for(int n = 0; n < repo.length(); n++)
	{
	    JSONObject r = repo.getJSONObject(n);
	    JSONObject repo2 = null;
	    try{
		    boolean repo1 = r.isNull(metrix);
		    if(!repo1){
		    	repo2 = r.getJSONObject(metrix);
		    }
		    else{
		    	repo2=null;
		    }
	    boolean b=repo2.isNull(value);
		    if(!b){
			REVENUE=repo2.getDouble(value);
			listval.add(REVENUE);
		    }
		    else{
		    	REVENUE=null;
		    	listval.add(REVENUE);
		    }
		    }catch(Exception e){
		    	e.getMessage();
		    }
	}
	return listval;
}
public void getIntdata(List<WebElement> listvalues,String filename,String metrix,String metrixvalue) throws Exception{
	String actuallist="";
	String expectedlist="";
	String value;
	String value1;
	List<Double> categories=getresponsesListIntdata(filename,metrix,metrixvalue);
	for(Double l:categories){
		value1=gl.truncate(l);
		value=value1+"%";
		outer:
		for(WebElement list:listvalues){
			
		actuallist = list.getText();
		expectedlist = value;
		if(list.getText().contains(value)){
			actuallist = list.getText();
			expectedlist = value;
			Testbase.test.log(LogStatus.INFO,"","Actual data : " +actuallist);
			Testbase.test.log(LogStatus.INFO,"","Expected data : " +expectedlist);
			break outer;
			}
				else{
					continue;
				}
			}
	Assert.assertEquals(actuallist, expectedlist);
		}	
	if(actuallist=="" & expectedlist==""){
		Assert.assertTrue(true, "No data avialble from backend");
	}
}

public void getYOYandPVPforScoreboardwidgetvalues(String filename,String metrix,String value1,String value2) throws Exception{
	Testbase.test.log(LogStatus.INFO,"", "Actual value : " + metrix);
	String resyoy=gl.getresponsesStringdata(filename,value1,value2);
	double resyoy1 = Double.parseDouble(resyoy);
	String value;
	if(resyoy.contains("-")){
	value=getHeaderLosersYOYandPVPvalues(resyoy1,value1);
	}
	else{
	value=getHeaderWinnersYOYandPVPvalues(resyoy1,value1);
	}
	Testbase.test.log(LogStatus.INFO,"", "Expected value : " + value);
	Assert.assertEquals(metrix, value);
}
	public void getYOYandPVPforPPIScoreboardwidgetvalues(List<WebElement> listvalues,String filename,String metrix,String value1) throws Exception{
		String actuallist="";
		String expectedlist="";
		List<Double> categories=getresponsesListIntdata(filename,metrix,value1);
		for(Double l:categories){
		
		String value;
		if(l.toString().contains("-")){
		value=getHeaderLosersYOYandPVPvalues(l,metrix);
		}
		else{
		value=getHeaderWinnersYOYandPVPvalues(l,metrix);
		}
		
		outer:
			for(WebElement list:listvalues){
				
			actuallist = list.getText();
			expectedlist = value;
			if(list.getText().contains(value.trim())){
				actuallist = list.getText();
				expectedlist = value.trim();
				Testbase.test.log(LogStatus.INFO,"","Actual data : " +actuallist);
				Testbase.test.log(LogStatus.INFO,"","Expected data : " +expectedlist);
				break outer;
				}
					else{
						continue;
					}
				}
		Assert.assertEquals(actuallist, expectedlist);
			}
		
	
		if(actuallist.trim()=="" & expectedlist.trim()==""){
			Testbase.test.log(LogStatus.INFO,"", "No data avialble from backend");
			Assert.assertTrue(true, "No data avialble from backend");
		}
		
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
		 resrevenue1 =gl.conversioninpercent(value1);
			resrevenue1="▼" + resrevenue1+"%" +" "+ metrix1;
		
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
		 resrevenue1 =gl.conversioninpercent(value1);
		 
			resrevenue1= "▲" +resrevenue1+"%" +" "+ metrix1;
		return resrevenue1;
}	
}
