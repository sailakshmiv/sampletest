package com.boomerang.canvas.scoreboard;

import org.json.JSONException;
import org.testng.Assert;

import com.boomerang.canvas.genericlibrary.Genericlib;
import com.boomerang.canvas.headerwidget.Headerwidgetapi;
import com.boomerang.canvas.testbase.Testbase;
import com.relevantcodes.extentreports.LogStatus;

public class Scoreboardwidgetapi extends Testbase{
	Genericlib gl=new Genericlib();
	Headerwidgetapi hapi=new Headerwidgetapi();
	
	public void getHeaderdata_from_api(String jsonfile,String filename,String inputfilename) throws JSONException {
		hapi.getHeaderdata_from_api(jsonfile,filename,inputfilename);
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
			resrevenue1="▼" + " "+resrevenue1+"%" +" "+ metrix1;
		
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
		 
			resrevenue1= "▲" + " "+resrevenue1+"%" +" "+ metrix1;
		return resrevenue1;
}	
}
