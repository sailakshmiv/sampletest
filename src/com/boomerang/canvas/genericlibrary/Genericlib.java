package com.boomerang.canvas.genericlibrary;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import com.boomerang.canvas.testbase.Testbase;

public class Genericlib extends Testbase{
	ReadOutputdata RTC = new ReadOutputdata();
	 DecimalFormat format = new DecimalFormat();
		
	//################################ Generic methods ####################################
		public double getresponsesintdata(String filename,String metrix,String value){
			double REVENUE = 0;
			JSONObject repo2 = null;
			String jsonFile = Readjson.readFileAsString(workspace+filename);
			RTC.dataparser(jsonFile);
			JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
			JSONArray repo = temp1.getJSONArray("data");
			for(int n = 0; n < repo.length(); n++)
			{
			    JSONObject r = repo.getJSONObject(n);
			    
			    try{
				    boolean repo1 = r.isNull(metrix);
				    if(!repo1){
			    repo2 = r.getJSONObject(metrix);
			    }
				    else{
				    	repo2 = null;
				    }
			    boolean b=repo2.isNull(value);
			    if(!b){
				REVENUE=repo2.getDouble(value);
			    }
			    else{
			    	REVENUE=(Double) null;
			    }
				    }
			    catch(Exception e){
			    	e.getMessage();
			    }
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
				    }
				    else{
				    	REVENUE=null;
				    }
				    }catch(Exception e){
				    	e.getMessage();
				    }
			}
			return REVENUE;
		}
	

		public String truncate(Double l) throws Exception{
			
			Double toBeTruncated = new Double(l);
		    Double resrevenue1=new BigDecimal(toBeTruncated ).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	        String formattedvalue = format.format(resrevenue1);
	        return formattedvalue;
		}
		
		public String conversion(Double value1) throws Exception{
			String resrevenue1;
			if(value1>=0 && value1<1000){
				resrevenue1=truncate(value1);
			}
			else if(value1>=1000 && value1<1000000){
				resrevenue1=truncate(value1/1000) + "K";
				}
				else if(value1>=1000000 && value1<1000000000){
					resrevenue1=truncate(value1/1000000) + "M";
				}
				else{
					resrevenue1=truncate(value1/1000000000) + "B";
				}
			return resrevenue1;
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
