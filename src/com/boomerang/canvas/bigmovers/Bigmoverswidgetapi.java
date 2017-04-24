package com.boomerang.canvas.bigmovers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;

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

public class Bigmoverswidgetapi extends Testbase{
	Genericlib gl=new Genericlib();
	Headerwidgetapi hapi=new Headerwidgetapi();
	ReadOutputdata RTC = new ReadOutputdata();
	 DecimalFormat format = new DecimalFormat();
	
	public void getBigmoversdata_from_api(String jsonfile,String filename,String inputfilename) throws JSONException {
		hapi.getHeaderdata_from_api(jsonfile,filename,inputfilename);
	}
	public void getYOYandPVPforScoreboardwidgetvalues(String filename,String metrix,String value1,String value2) throws Exception{
		hapi.getYOYandPVPforheaderwidgetvalues(filename, metrix, value1, value2);
	}
	
	@SuppressWarnings("null")
	public Map<String,Double> getwinnersresponseslistintdata(String filename,String yoy,String yoyrevenue,String dimension,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+filename);
		Map<String,Double> map=new HashMap<>();
		double PVP = 0,YOY=0;
		String category = null;
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject repo4=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
			boolean b=false,b1=false,b2=false;
			 
		    JSONObject r = repo.getJSONObject(n);
		    try{
		    boolean hasyoy=r.has(yoy);
		    boolean hasdimension=r.has(dimension);
		    boolean haspvp=r.has(pvp);
	 if(hasyoy && hasdimension && haspvp){
		    boolean yoy1 = r.isNull(yoy); //YOY
		    boolean dimension5 = r.isNull(dimension); //DIM
		    boolean pvp6 = r.isNull(pvp); //PVP
		    
		    if(!yoy1 && !dimension5 && !pvp6){
		    	repo2 = r.getJSONObject(yoy);
		    	repo3 = r.getJSONObject(dimension);
		    	repo4 = r.getJSONObject(pvp);
		    b=repo2.isNull(yoyrevenue);
		     b1=repo3.isNull(catandbrand);
		     b2=repo4.isNull(pvprevenue);
				    if(!b && !b1 && !b2){
				    YOY=repo2.getDouble(yoyrevenue);
				    category =repo3.getString(catandbrand);
				    PVP=repo4.getDouble(pvprevenue);
				    
				    if(!(String.valueOf(YOY).contains("-")) && !(String.valueOf(PVP).contains("-"))){
				    	String yoy12=getBigmoversWinnersYOYandPVPvalues(YOY);
						String pvp1=getBigmoversWinnersYOYandPVPvalues(PVP);
						    double pvp2 = Double.parseDouble(pvp1);
						    double yoy2 = Double.parseDouble(yoy12);
						    double sum=yoy2+pvp2;
						    map.put(category, sum);
				    	}
				    else{
				    	category =null;
				    	YOY=(Double) null;
				    	PVP=(Double) null;
				    	 double sum=YOY+PVP;
						    map.put(category, sum);
				    }
				    }
				    else{
				    	if(b){
				    		YOY=(Double) null;
				    		category =repo3.getString(catandbrand);
						    PVP=repo4.getDouble(pvprevenue);
						    if(!(String.valueOf(YOY).contains("-")) && !(String.valueOf(PVP).contains("-"))){
						    	String yoy12=getBigmoversWinnersYOYandPVPvalues(YOY);
								String pvp1=getBigmoversWinnersYOYandPVPvalues(PVP);
								    double pvp2 = Double.parseDouble(pvp1);
								    double yoy2 = Double.parseDouble(yoy12);
								    double sum=yoy2+pvp2;
								    map.put(category, sum);
						    }
				    		else{
						    	category =null;
						    	YOY=(Double) null;
						    	PVP=(Double) null;
						    	 double sum=YOY+PVP;
								    map.put(category, sum);
						    }
				    	}
				    	else if(b2){
				    	PVP=(Double) null;
				    	 YOY=repo2.getDouble(yoyrevenue);
						  category =repo3.getString(catandbrand);
						  if(!(String.valueOf(YOY).contains("-")) && !(String.valueOf(PVP).contains("-"))){
							  String yoy12=getBigmoversWinnersYOYandPVPvalues(YOY);
							String pvp2=getBigmoversWinnersYOYandPVPvalues(PVP);
							    double pvp1 = Double.parseDouble(pvp2);
							    double yoy2 = Double.parseDouble(yoy12);
							    double sum=yoy2+pvp1;
							    map.put(category, sum);
					    
				    	}
				    	else{
					    	category =null;
					    	YOY=(Double) null;
					    	PVP=(Double) null;
					    	 double sum=YOY+PVP;
							    map.put(category, sum);
					    }
				    	}
				    	else{
				    	category=null;
				    	YOY=repo2.getDouble(yoyrevenue);
					    PVP=repo4.getDouble(pvprevenue);
					    if(!(String.valueOf(YOY).contains("-")) && !(String.valueOf(PVP).contains("-"))){
					    	String yoy12=getBigmoversWinnersYOYandPVPvalues(YOY);
							String pvp2=getBigmoversWinnersYOYandPVPvalues(PVP);
							    double pvp1 = Double.parseDouble(pvp2);
							    double yoy2 = Double.parseDouble(yoy12);
							    double sum=yoy2+pvp1;
							    map.put(category, sum);
					    }
				    	else{
					    	category =null;
					    	YOY=(Double) null;
					    	PVP=(Double) null;
					    	 double sum=YOY+PVP;
							    map.put(category, sum);
					    }
				    }
				    	String pvp2=getBigmoversWinnersYOYandPVPvalues(YOY);
					    String yoy2=getBigmoversWinnersYOYandPVPvalues(PVP);
					    double pvp1 = Double.parseDouble(pvp2);
					    double yoy12 = Double.parseDouble(yoy2);
					    double sum=yoy12+pvp1;
					    map.put(category, sum);
				    }
		    }
		    else{
		    	if(yoy1){
		    		YOY=(Double) null;
		    		category =repo3.getString(catandbrand);
				    PVP=repo4.getDouble(pvprevenue);
				    if(!(String.valueOf(YOY).contains("-")) && !(String.valueOf(PVP).contains("-"))){
				    	String pvp12=getBigmoversWinnersYOYandPVPvalues(YOY);
						String yoy2=getBigmoversWinnersYOYandPVPvalues(PVP);
						    double pvp1 = Double.parseDouble(pvp12);
						    double yoy12 = Double.parseDouble(yoy2);
						    double sum=yoy12+pvp1;
						    map.put(category, sum);
				    }
		    		else{
				    	category =null;
				    	YOY=(Double) null;
				    	PVP=(Double) null;
				    	 double sum=YOY+PVP;
						    map.put(category, sum);
				    }
		    	}
		    	else if(pvp6){
		    	PVP=(Double) null;
		    	 YOY=repo2.getDouble(yoyrevenue);
				  category =repo3.getString(catandbrand);
				  if(!(String.valueOf(YOY).contains("-")) && !(String.valueOf(PVP).contains("-"))){
					  String pvp2=getBigmoversWinnersYOYandPVPvalues(YOY);
					String yoy2=getBigmoversWinnersYOYandPVPvalues(PVP);
					    double pvp1 = Double.parseDouble(pvp2);
					    double yoy12 = Double.parseDouble(yoy2);
					    double sum=yoy12+pvp1;
					    map.put(category, sum);
			    
		    	}
		    	else{
			    	category =null;
			    	YOY=(Double) null;
			    	PVP=(Double) null;
			    	 double sum=YOY+PVP;
					    map.put(category, sum);
			    }
		    	}
		    	else{
		    	category=null;
		    	YOY=repo2.getDouble(yoyrevenue);
			    PVP=repo4.getDouble(pvprevenue);
		    	if(!(String.valueOf(YOY).contains("-")) && !(String.valueOf(PVP).contains("-"))){
			    	String pvp2=getBigmoversWinnersYOYandPVPvalues(YOY);
					String yoy2=getBigmoversWinnersYOYandPVPvalues(PVP);
					    double pvp1 = Double.parseDouble(pvp2);
					    double yoy12 = Double.parseDouble(yoy2);
					    double sum=yoy12+pvp1;
					    map.put(category, sum);
			    }
		    	else{
			    	category =null;
			    	YOY=(Double) null;
			    	PVP=(Double) null;
			    	 double sum=YOY+PVP;
					    map.put(category, sum);
			    }
		    }
		    	
		    }
	 }
	 else {
		    boolean dimension5 = r.isNull(dimension); //DIM
		    boolean pvp6 = r.isNull(pvp); //PVP
		    
		    if(!dimension5 && !pvp6){
		    	repo3 = r.getJSONObject(dimension);
		    	repo4 = r.getJSONObject(pvp);
		     b1=repo3.isNull(catandbrand);
		     b2=repo4.isNull(pvprevenue);
				    if(!b1 && !b2){
				    YOY=0;
				    category =repo3.getString(catandbrand);
				    PVP=repo4.getDouble(pvprevenue);
				    if(!(String.valueOf(PVP).contains("-"))){
				    	String yoy12="0";
						String pvp1=getBigmoversWinnersYOYandPVPvalues(PVP);
						    double pvp2 = Double.parseDouble(pvp1);
						    double yoy2 = Double.parseDouble(yoy12);
						    double sum=yoy2+pvp2;
						    map.put(category, sum);
				    	}
				    else{
				    	category =null;
				    	YOY=(Double) null;
				    	PVP=(Double) null;
				    	 double sum=YOY+PVP;
						    map.put(category, sum);
				    }
			
				    }}
	 }
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		System.out.println("map :" + map);
		return map;

	}
	@SuppressWarnings("null")
	public Map<String,Double> getLosersresponseslistintdata(String filename,String yoy,String yoyrevenue,String dimension,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+filename);
		Map<String,Double> map=new HashMap<>();
		double PVP = 0,YOY=0;
		String category = null;
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject repo4=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
			boolean b=false,b1=false,b2=false;
			 
		    JSONObject r = repo.getJSONObject(n);
		    try{
		    	   boolean hasyoy=r.has(yoy);
				    boolean hasdimension=r.has(dimension);
				    boolean haspvp=r.has(pvp);
	 if(hasyoy && hasdimension && haspvp){  
		    boolean yoy1 = r.isNull(yoy); //YOY
		    boolean dimension5 = r.isNull(dimension); //DIM
		    boolean pvp6 = r.isNull(pvp); //PVP
		    
		    if(!yoy1 && !dimension5 && !pvp6){
		    	repo2 = r.getJSONObject(yoy);
		    	repo3 = r.getJSONObject(dimension);
		    	repo4 = r.getJSONObject(pvp);
		    b=repo2.isNull(yoyrevenue);
		     b1=repo3.isNull(catandbrand);
		     b2=repo4.isNull(pvprevenue);
				    if(!b && !b1 && !b2){
				    YOY=repo2.getDouble(yoyrevenue);
				    category =repo3.getString(catandbrand);
				    PVP=repo4.getDouble(pvprevenue);
				    
				    if(String.valueOf(YOY).contains("-") && String.valueOf(PVP).contains("-")){
				    	String yoy12=getBigmoversLosersYOYandPVPvalues(YOY);
						String pvp1=getBigmoversLosersYOYandPVPvalues(PVP);
						    double pvp2 = Double.parseDouble(pvp1);
						    double yoy2 = Double.parseDouble(yoy12);
						    double sum=yoy2+pvp2;
						    map.put(category, sum);
				    	}
				    else{
				    	category =null;
				    	YOY=(Double) null;
				    	PVP=(Double) null;
				    	 double sum=YOY+PVP;
						    map.put(category, sum);
				    }
				    }
				    else{
				    	if(b){
				    		YOY=(Double) null;
				    		category =repo3.getString(catandbrand);
						    PVP=repo4.getDouble(pvprevenue);
				    		if(String.valueOf(YOY).contains("-") && String.valueOf(PVP).contains("-")){
						    	String yoy12=getBigmoversLosersYOYandPVPvalues(YOY);
								String pvp1=getBigmoversLosersYOYandPVPvalues(PVP);
								    double pvp2 = Double.parseDouble(pvp1);
								    double yoy2 = Double.parseDouble(yoy12);
								    double sum=yoy2+pvp2;
								    map.put(category, sum);
						    }
				    		else{
						    	category =null;
						    	YOY=(Double) null;
						    	PVP=(Double) null;
						    	 double sum=YOY+PVP;
								    map.put(category, sum);
						    }
				    	}
				    	else if(b2){
				    	PVP=(Double) null;
				    	 YOY=repo2.getDouble(yoyrevenue);
						  category =repo3.getString(catandbrand);
				    	if(String.valueOf(YOY).contains("-") && String.valueOf(PVP).contains("-")){
					    	String yoy12=getBigmoversLosersYOYandPVPvalues(YOY);
							String pvp2=getBigmoversLosersYOYandPVPvalues(PVP);
							    double pvp1 = Double.parseDouble(pvp2);
							    double yoy2 = Double.parseDouble(yoy12);
							    double sum=yoy2+pvp1;
							    map.put(category, sum);
					    
				    	}
				    	else{
					    	category =null;
					    	YOY=(Double) null;
					    	PVP=(Double) null;
					    	 double sum=YOY+PVP;
							    map.put(category, sum);
					    }
				    	}
				    	else{
				    	category=null;
				    	YOY=repo2.getDouble(yoyrevenue);
					    PVP=repo4.getDouble(pvprevenue);
				    	if(String.valueOf(YOY).contains("-") && String.valueOf(PVP).contains("-")){
					    	String yoy12=getBigmoversLosersYOYandPVPvalues(YOY);
							String pvp2=getBigmoversLosersYOYandPVPvalues(PVP);
							    double pvp1 = Double.parseDouble(pvp2);
							    double yoy2 = Double.parseDouble(yoy12);
							    double sum=yoy2+pvp1;
							    map.put(category, sum);
					    }
				    	else{
					    	category =null;
					    	YOY=(Double) null;
					    	PVP=(Double) null;
					    	 double sum=YOY+PVP;
							    map.put(category, sum);
					    }
				    }
				    	String pvp2=getBigmoversLosersYOYandPVPvalues(YOY);
					    String yoy2=getBigmoversLosersYOYandPVPvalues(PVP);
					    double pvp1 = Double.parseDouble(pvp2);
					    double yoy12 = Double.parseDouble(yoy2);
					    double sum=yoy12+pvp1;
					    map.put(category, sum);
				    }
		    }
		    else{
		    	if(yoy1){
		    		YOY=(Double) null;
		    		category =repo3.getString(catandbrand);
				    PVP=repo4.getDouble(pvprevenue);
		    		if(String.valueOf(YOY).contains("-") && String.valueOf(PVP).contains("-")){
				    	String pvp12=getBigmoversLosersYOYandPVPvalues(YOY);
						String yoy2=getBigmoversLosersYOYandPVPvalues(PVP);
						    double pvp1 = Double.parseDouble(pvp12);
						    double yoy12 = Double.parseDouble(yoy2);
						    double sum=yoy12+pvp1;
						    map.put(category, sum);
				    }
		    		else{
				    	category =null;
				    	YOY=(Double) null;
				    	PVP=(Double) null;
				    	 double sum=YOY+PVP;
						    map.put(category, sum);
				    }
		    	}
		    	else if(pvp6){
		    	PVP=(Double) null;
		    	 YOY=repo2.getDouble(yoyrevenue);
				  category =repo3.getString(catandbrand);
		    	if(String.valueOf(YOY).contains("-") && String.valueOf(PVP).contains("-")){
			    	String pvp2=getBigmoversLosersYOYandPVPvalues(YOY);
					String yoy2=getBigmoversLosersYOYandPVPvalues(PVP);
					    double pvp1 = Double.parseDouble(pvp2);
					    double yoy12 = Double.parseDouble(yoy2);
					    double sum=yoy12+pvp1;
					    map.put(category, sum);
			    
		    	}
		    	else{
			    	category =null;
			    	YOY=(Double) null;
			    	PVP=(Double) null;
			    	 double sum=YOY+PVP;
					    map.put(category, sum);
			    }
		    	}
		    	else{
		    	category=null;
		    	YOY=repo2.getDouble(yoyrevenue);
			    PVP=repo4.getDouble(pvprevenue);
		    	if(String.valueOf(YOY).contains("-") && String.valueOf(PVP).contains("-")){
			    	String pvp2=getBigmoversLosersYOYandPVPvalues(YOY);
					String yoy2=getBigmoversLosersYOYandPVPvalues(PVP);
					    double pvp1 = Double.parseDouble(pvp2);
					    double yoy12 = Double.parseDouble(yoy2);
					    double sum=yoy12+pvp1;
					    map.put(category, sum);
			    }
		    	else{
			    	category =null;
			    	YOY=(Double) null;
			    	PVP=(Double) null;
			    	 double sum=YOY+PVP;
					    map.put(category, sum);
			    }
		    	}
		    }
		    	
	 }
	 
	    else{
		    boolean dimension5 = r.isNull(dimension); //DIM
		    boolean pvp6 = r.isNull(pvp); //PVP
		    
		    if(!dimension5 && !pvp6){
		    	repo3 = r.getJSONObject(dimension);
		    	repo4 = r.getJSONObject(pvp);
		     b1=repo3.isNull(catandbrand);
		     b2=repo4.isNull(pvprevenue);
				    if(!b1 && !b2){
				    YOY=0;
				    category =repo3.getString(catandbrand);
				    PVP=repo4.getDouble(pvprevenue);
				    if(String.valueOf(PVP).contains("-")){
				    	String yoy12="0";
						String pvp1=getBigmoversLosersYOYandPVPvalues(PVP);
						    double pvp2 = Double.parseDouble(pvp1);
						    double yoy2 = Double.parseDouble(yoy12);
						    double sum=yoy2+pvp2;
						    map.put(category, sum);
				    	}
				    else{
				    	category =null;
				    	YOY=(Double) null;
				    	PVP=(Double) null;
				    	 double sum=YOY+PVP;
						    map.put(category, sum);
				    }
			
				    }}
	    }
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		System.out.println("map :" + map);
		return map;
	}
	
	public List<String> getWinnerspvpdata(String filename,String yoy,String yoyrevenue,String dimension,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+filename);
		Map<String,String> map=new HashMap<>();
		List<String> pvpvalues=new ArrayList<String>();
		double PVP = 0;
		String category = "";
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    try{
		    	repo2 = r.getJSONObject(pvp);
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=getwinnersdata(filename,yoy,yoyrevenue,dimension,catandbrand,pvp,pvprevenue);
				    	
					    	for(String list:categories){
					    		boolean b= repo3.isNull(catandbrand);
					    		if(list==null & b){
					    			category=null;
					    			PVP=repo2.getDouble(pvprevenue);
									String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP);
									    map.put(category, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(catandbrand);
							    	if(category.equals(list)){
							    		PVP=repo2.getDouble(pvprevenue);
										String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP);
										    map.put(category, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	pvpvalues.add(entry.getValue());
        }
		return pvpvalues;

	}
	public List<String> getLosersallpvpdata(String catfilename,String brandfile,String yoy,String yoyrevenue,String dimension,String brandandcat,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+brandfile);
		Map<String,String> map=new HashMap<>();
		List<String> pvpvalues=new ArrayList<String>();
		double PVP = 0;
		String category = "";
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    try{
		    	repo2 = r.getJSONObject(pvp);
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=Alllosersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo3.isNull(brandandcat);
					    		
					    		if(list==null & b){
					    			category=null;
					    			PVP=repo2.getDouble(pvprevenue);
									String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP);
									    map.put(category, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(brandandcat);
							    	if(category.equals(list)){
							    		PVP=repo2.getDouble(pvprevenue);
										String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP);
										    map.put(category, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		String	jsonFile1 = Readjson.readFileAsString(workspace+catfilename);
		double PVP1 = 0;
		String category1 = "";
		RTC.dataparser(jsonFile1);
		JSONObject repo5=null;
		JSONObject repo6=null;
		JSONObject temp2 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo7 = temp2.getJSONArray("data");
		for(int n = 0; n < repo7.length(); n++)
		{
		    JSONObject r = repo7.getJSONObject(n);
		    try{
		    	repo5 = r.getJSONObject(pvp);
		    	repo6 = r.getJSONObject(dimension);
				    	List<String> categories=Alllosersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo6.isNull(catandbrand);
					    		
					    		if(list==null & b){
					    			category1=null;
					    			PVP1=repo5.getDouble(pvprevenue);
									String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP1);
									    map.put(category1, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category1 =repo6.getString(catandbrand);
							    	if(category1.equals(list)){
							    		PVP1=repo5.getDouble(pvprevenue);
										String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP1);
										    map.put(category1, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	pvpvalues.add(entry.getValue());
        }
		return pvpvalues;

	}
	public void losersallpvpvalidation(List<WebElement> listvalues,String categoryfilename,String brandfilename,String metrix,String value,String value2,String value3,String value4,String value5,String value6){
		String l="";
		String values1;
		List<String> categories=getLosersallpvpdata(categoryfilename,brandfilename,metrix,value,value2,value3,value4,value5,value6);
		String actualtext="";
		for(String values:categories){
			values1= "▼" +"$" +values +" "+ value5;
			outer :
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				Assert.assertEquals(actualtext, l);
		
	}
		
	}
	
	public List<String> getLosersallyoydata(String catfilename,String brandfile,String yoy,String yoyrevenue,String dimension,String brandandcat,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+brandfile);
		Map<String,String> map=new HashMap<>();
		List<String> pvpvalues=new ArrayList<String>();
		double PVP = 0;
		String category = "";
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
			
		    JSONObject r = repo.getJSONObject(n);
		    try{
		    	boolean hasyoy=r.has(yoy);
			    boolean hasdimension=r.has(dimension);
			    boolean haspvp=r.has(pvp);
		 if(hasyoy && hasdimension && haspvp){
		    	repo2 = r.getJSONObject(yoy);
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=Alllosersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo3.isNull(brandandcat);
					    		boolean b1= repo2.isNull(yoyrevenue);
					    		if(list==null & b){
					    			category=null;
					    			PVP=repo2.getDouble(yoyrevenue);
									String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP);
									    map.put(category, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(brandandcat);
					    			
							    	if(category.equals(list)){
							    		PVP=repo2.getDouble(yoyrevenue);
										String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP);
										    map.put(category, pvp1);
					    		}
					    		}
					    	}
		 }
		 else{
			 //repo2 = "0";
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=Alllosersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo3.isNull(brandandcat);
					    		if(list==null & b){
					    			category=null;
					    			PVP=0;
									String pvp1="0";
									    map.put(category, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(brandandcat);
					    			
							    	if(category.equals(list)){
							    		PVP=0;
										String pvp1="0";
										    map.put(category, pvp1);
					    		}
					    		}
					    	}
		 }
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		String	jsonFile1 = Readjson.readFileAsString(workspace+catfilename);
		double PVP1 = 0;
		String category1 = "";
		RTC.dataparser(jsonFile1);
		JSONObject repo5=null;
		JSONObject repo6=null;
		JSONObject temp2 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo7 = temp2.getJSONArray("data");
		for(int n = 0; n < repo7.length(); n++)
		{
			
		    JSONObject r = repo7.getJSONObject(n);
			boolean hasyoy=r.has(yoy);
		    boolean hasdimension=r.has(dimension);
		    boolean haspvp=r.has(pvp);
	 if(hasyoy && hasdimension && haspvp){
		    try{
		    	repo5 = r.getJSONObject(yoy);
		    	repo6 = r.getJSONObject(dimension);
				    	List<String> categories=Alllosersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo6.isNull(catandbrand);
					    		if(list==null & b){
					    			category1=null;
					    			PVP1=repo5.getDouble(yoyrevenue);
									String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP1);
									    map.put(category1, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category1 =repo6.getString(catandbrand);
					    			
							    	if(category1.equals(list)){
							    		PVP1=repo5.getDouble(yoyrevenue);
										String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP1);
										    map.put(category1, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
	 }
	 else{
		 try{
		    	repo6 = r.getJSONObject(dimension);
				    	List<String> categories=Alllosersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo6.isNull(catandbrand);
					    		if(list==null & b){
					    			category1=null;
					    			PVP1=0;
									String pvp1="0";
									    map.put(category1, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category1 =repo6.getString(catandbrand);
					    			
							    	if(category1.equals(list)){
							    		PVP1=0;
										String pvp1="0";
										    map.put(category1, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
	 }
		}
		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	pvpvalues.add(entry.getValue());
        }
		return pvpvalues;

	}
	public void losersallyoyvalidation(List<WebElement> listvalues,String categoryfilename,String brandfilename,String metrix,String value,String value2,String value3,String value4,String value5,String value6){
		String l="";
		String values1 = null;
		List<String> categories=getLosersallyoydata(categoryfilename,brandfilename,metrix,value,value2,value3,value4,value5,value6);
		String actualtext="";
		for(String values:categories){
			if(values=="0"){
				values1="$"+"---"+" "+metrix;
			}
			else{
			values1= "▼" +"$" +values +" "+ metrix;
			}
			outer:
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				Assert.assertEquals(actualtext, l);
		
	}
		
	}
	public List<String> getWinnersallpvpdata(String catfilename,String brandfile,String yoy,String yoyrevenue,String dimension,String brandandcat,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+brandfile);
		Map<String,String> map=new HashMap<>();
		List<String> pvpvalues=new ArrayList<String>();
		double PVP = 0;
		String category = "";
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    try{
		    	repo2 = r.getJSONObject(pvp);
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=Allwinnersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo3.isNull(brandandcat);
					    		
					    		if(list==null & b){
					    			category=null;
					    			PVP=repo2.getDouble(pvprevenue);
									String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP);
									    map.put(category, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(brandandcat);
							    	if(category.equals(list)){
							    		PVP=repo2.getDouble(pvprevenue);
										String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP);
										    map.put(category, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		String	jsonFile1 = Readjson.readFileAsString(workspace+catfilename);
		double PVP1 = 0;
		String category1 = "";
		RTC.dataparser(jsonFile1);
		JSONObject repo5=null;
		JSONObject repo6=null;
		JSONObject temp2 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo7 = temp2.getJSONArray("data");
		for(int n = 0; n < repo7.length(); n++)
		{
		    JSONObject r = repo7.getJSONObject(n);
		    try{
		    	repo5 = r.getJSONObject(pvp);
		    	repo6 = r.getJSONObject(dimension);
				    	List<String> categories=Allwinnersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo6.isNull(catandbrand);
					    		
					    		if(list==null & b){
					    			category1=null;
					    			PVP1=repo5.getDouble(pvprevenue);
									String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP1);
									    map.put(category1, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category1 =repo6.getString(catandbrand);
							    	if(category1.equals(list)){
							    		PVP1=repo5.getDouble(pvprevenue);
										String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP1);
										    map.put(category1, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}

		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size1=newMap.size();
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	pvpvalues.add(entry.getValue());
        }
		return pvpvalues;

	}
	public void winnersallpvpvalidation(List<WebElement> listvalues,String categoryfilename,String brandfilename,String metrix,String value,String value2,String value3,String value4,String value5,String value6){
		String l="";
		String values1;
		List<String> categories=getWinnersallpvpdata(categoryfilename,brandfilename,metrix,value,value2,value3,value4,value5,value6);
		String actualtext="";
		for(String values:categories){
			values1= "▲" +"$" +values +" "+ value5;
			outer :
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				Assert.assertEquals(actualtext, l);
		
	}
		
	}
	
	public List<String> getWinnersallyoydata(String catfilename,String brandfile,String yoy,String yoyrevenue,String dimension,String brandandcat,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+brandfile);
		Map<String,String> map=new HashMap<>();
		List<String> pvpvalues=new ArrayList<String>();
		double PVP = 0;
		String category = "";
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    boolean hasyoy=r.has(yoy);
		    boolean hasdimension=r.has(dimension);
		    boolean haspvp=r.has(pvp);
	 if(hasyoy && hasdimension && haspvp){
		    try{
		    	repo2 = r.getJSONObject(yoy);
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=Allwinnersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
				    	for(String list:categories){
					    		boolean b= repo3.isNull(brandandcat);
					    		
					    		if(list==null & b){
					    			category=null;
					    			PVP=repo2.getDouble(yoyrevenue);
									String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP);
									    map.put(category, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(brandandcat);
							    	if(category.equals(list)){
							    		PVP=repo2.getDouble(yoyrevenue);
										String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP);
										    map.put(category, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}else{
			try{
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=Allwinnersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
				    	for(String list:categories){
					    		boolean b= repo3.isNull(brandandcat);
					    		
					    		if(list==null & b){
					    			category=null;
					    			PVP=0;
									String pvp1="0";
									    map.put(category, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(brandandcat);
							    	if(category.equals(list)){
							    		PVP=0;
										String pvp1="0";
										    map.put(category, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		}
		String	jsonFile1 = Readjson.readFileAsString(workspace+catfilename);
		double PVP1 = 0;
		String category1 = "";
		RTC.dataparser(jsonFile1);
		JSONObject repo5=null;
		JSONObject repo6=null;
		JSONObject temp2 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo7 = temp2.getJSONArray("data");
		for(int n = 0; n < repo7.length(); n++)
		{
		    JSONObject r = repo7.getJSONObject(n);
		    boolean hasyoy=r.has(yoy);
		    boolean hasdimension=r.has(dimension);
		    boolean haspvp=r.has(pvp);
	 if(hasyoy && hasdimension && haspvp){
		    try{
		    	repo5 = r.getJSONObject(yoy);
		    	repo6 = r.getJSONObject(dimension);
				    	List<String> categories=Allwinnersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo6.isNull(catandbrand);
					    		
					    		if(list==null & b){
					    			category1=null;
					    			PVP1=repo5.getDouble(yoyrevenue);
									String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP1);
									    map.put(category1, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category1 =repo6.getString(catandbrand);
							    	if(category1.equals(list)){
							    		PVP1=repo5.getDouble(yoyrevenue);
										String pvp1=getBigmoversWinnersYOYandPVPconversionPvalues(PVP1);
										    map.put(category1, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
	 else{
		  try{
		    	repo6 = r.getJSONObject(dimension);
				    	List<String> categories=Allwinnersvalidation( catfilename,brandfile,yoy,yoyrevenue,dimension,brandandcat,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo6.isNull(catandbrand);
					    		
					    		if(list==null & b){
					    			category1=null;
					    			PVP1=0;
									String pvp1="0";
									    map.put(category1, pvp1);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category1 =repo6.getString(catandbrand);
							    	if(category1.equals(list)){
							    		PVP1=0;
										String pvp1="0";
										    map.put(category1, pvp1);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
	 }
		}
	 
		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	pvpvalues.add(entry.getValue());
        }
		return pvpvalues;

	}
	public void winnersallyoyvalidation(List<WebElement> listvalues,String categoryfilename,String brandfilename,String metrix,String value,String value2,String value3,String value4,String value5,String value6){
		String l="";
		String values1;
		List<String> categories=getWinnersallyoydata(categoryfilename,brandfilename,metrix,value,value2,value3,value4,value5,value6);
		String actualtext="";
		for(String values:categories){
			if(values=="0"){
				values1="$"+"---"+" "+metrix;
			}
			else{
			values1= "▲" +"$" +values +" "+ metrix;
			}
			outer:
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				Assert.assertEquals(actualtext, l);
		
	}
		
	}

	public void winnerspvpvalidation(List<WebElement> listvalues,String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		String l="";
		String values1;
		List<String> categories=getWinnerspvpdata(filename,metrix,value,value2,value3,value4,value5);
		String actualtext="";
		for(String values:categories){
			values1= "▲" +"$" +values +" "+ value4;
			outer:
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				Assert.assertEquals(actualtext, l);
		
	}
		
	}
	
	public List<String> getWinnersyoydata(String filename,String yoy,String yoyrevenue,String dimension,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+filename);
		Map<String,String> map=new HashMap<>();
		List<String> yoyvalues=new ArrayList<String>();
		double YOY=0;
		String category = "";
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    boolean hasyoy=r.has(yoy);
		    boolean hasdimension=r.has(dimension);
		    boolean haspvp=r.has(pvp);
	 if(hasyoy && hasdimension && haspvp){
		    try{
		    	
		    	repo2 = r.getJSONObject(yoy);
		    	repo3 = r.getJSONObject(dimension);
		   
				    	List<String> categories=getwinnersdata(filename,yoy,yoyrevenue,dimension,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo3.isNull(catandbrand);
					    		if(list==null & b){
					    			category=null;
					    			YOY=repo2.getDouble(yoyrevenue);
							    	String yoy12=getBigmoversWinnersYOYandPVPconversionPvalues(YOY);
									    map.put(category, yoy12);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(catandbrand);
							    	if(category.equals(list)){
							    		YOY=repo2.getDouble(yoyrevenue);
								    	String yoy12=getBigmoversWinnersYOYandPVPconversionPvalues(YOY);
										    map.put(category, yoy12);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
	 }else{
		 try{
		    	
		    	repo3 = r.getJSONObject(dimension);
		   
				    	List<String> categories=getwinnersdata(filename,yoy,yoyrevenue,dimension,catandbrand,pvp,pvprevenue);
					    	for(String list:categories){
					    		boolean b= repo3.isNull(catandbrand);
					    		if(list==null & b){
					    			category=null;
					    			YOY=0;
							    	String yoy12="0";
									    map.put(category, yoy12);
					    		}
					    		else if(b & list !=null){
					    			continue;
					    		}
					    		else{
					    			category =repo3.getString(catandbrand);
							    	if(category.equals(list)){
							    		YOY=0;
								    	String yoy12="0";
										    map.put(category, yoy12);
					    		}
					    		}
					    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
	 }
		}
		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size1=newMap.size();
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	yoyvalues.add(entry.getValue());
        }
		return yoyvalues;

	}
	public void winnersyoyvalidation(List<WebElement> listvalues,String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		String l="";
		String values1;
		List<String> categories=getWinnersyoydata(filename,metrix,value,value2,value3,value4,value5);
		String actualtext="";
		for(String values:categories){
			if(values=="0"){
				values1="$"+"---"+" "+metrix;
			}else{
			values1= "▲" +"$" +values +" "+ metrix;
			}
			outer:
				
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				Assert.assertEquals(actualtext, l);	
		
	}
		
	}
	public List<String> getLoserspvpdata(String filename,String yoy,String yoyrevenue,String dimension,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+filename);
		Map<String,String> map=new HashMap<>();
		List<String> pvpvalues=new ArrayList<String>();
		double PVP = 0;
		String category ="";
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    try{
		    	
		    	
		    	repo2 = r.getJSONObject(pvp);
		    	repo3 = r.getJSONObject(dimension);
				    	List<String> categories=getlosersdata(filename,yoy,yoyrevenue,dimension,catandbrand,pvp,pvprevenue);
				    	
						    	for(String list:categories){
						    		boolean b= repo3.isNull(catandbrand);
						    		if(list==null & b){
						    			category=null;
						    			PVP=repo2.getDouble(pvprevenue);
						    			String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP);
						    			map.put(category, pvp1);
						    		}
						    		else if(b & list !=null){
						    			continue;
						    		}
						    		else{
						    			category =repo3.getString(catandbrand);
								    	if(category.equals(list)){
								    		PVP=repo2.getDouble(pvprevenue);
							    			String pvp1=getBigmoversLosersYOYandPVPconversionvalues(PVP);
							    			map.put(category, pvp1);
						    		}
						    		}
				    	}
		    }catch(Exception e){
		    	e.getMessage();
		    }
		}
		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	pvpvalues.add(entry.getValue());
        }
		return pvpvalues;

	}
	public void loserspvpvalidation(List<WebElement> listvalues,String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		String l="";
		String values1;
		List<String> categories=getLoserspvpdata(filename,metrix,value,value2,value3,value4,value5);
		String actualtext="";
		for(String values:categories){
			values1= "▼" +"$" +values +" "+ value4;
			outer:
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				
				Assert.assertEquals(actualtext, l);
	}
		
	}
	public List<String> getLosersyoydata(String filename,String yoy,String yoyrevenue,String dimension,String catandbrand,String pvp,String pvprevenue){
		String	jsonFile = Readjson.readFileAsString(workspace+filename);
		Map<String,String> map=new HashMap<>();
		List<String> yoyvalues=new ArrayList<String>();
		double YOY=0;
		String category = null;
		RTC.dataparser(jsonFile);
		JSONObject repo2=null;
		JSONObject repo3=null;
		JSONObject temp1 = new JSONObject(RTC.getExpectedOutput());
		JSONArray repo = temp1.getJSONArray("data");
		for(int n = 0; n < repo.length(); n++)
		{
		    JSONObject r = repo.getJSONObject(n);
		    boolean hasyoy=r.has(yoy);
		    boolean hasdimension=r.has(dimension);
		    boolean haspvp=r.has(pvp);
	 if(hasyoy && hasdimension && haspvp){
		    try{
		    	
		    	repo2 = r.getJSONObject(yoy);
		    	repo3 = r.getJSONObject(dimension);
		   
				    	List<String> categories=getlosersdata(filename,yoy,yoyrevenue,dimension,catandbrand,pvp,pvprevenue);
				    	for(String list:categories){
				    		boolean b= repo3.isNull(catandbrand);
				    		if(list==null & b){
				    			category=null;
				    			YOY=repo2.getDouble(yoyrevenue);
						    	String yoy12=getBigmoversLosersYOYandPVPconversionvalues(YOY);
								    map.put(category, yoy12);
				    		}
				    		else if(b & list !=null){
				    			continue;
				    		}
				    		else{
				    			category =repo3.getString(catandbrand);
						    	if(category.equals(list)){
						    	YOY=repo2.getDouble(yoyrevenue);
						    	String yoy12=getBigmoversLosersYOYandPVPconversionvalues(YOY);
								    map.put(category, yoy12);
				    		}
				    		}
				    	}
				    	
		    }catch(Exception e){
		    	e.getMessage();
		    }
	 }else{
		 try{
		    	
		    	repo3 = r.getJSONObject(dimension);
		   
				    	List<String> categories=getlosersdata(filename,yoy,yoyrevenue,dimension,catandbrand,pvp,pvprevenue);
				    	for(String list:categories){
				    		boolean b= repo3.isNull(catandbrand);
				    		if(list==null & b){
				    			category=null;
				    			YOY=0;
						    	String yoy12="0";
								    map.put(category, yoy12);
				    		}
				    		else if(b & list !=null){
				    			continue;
				    		}
				    		else{
				    			category =repo3.getString(catandbrand);
						    	if(category.equals(list)){
						    	YOY=0;
						    	String yoy12="0";
								    map.put(category, yoy12);
				    		}
				    		}
				    	}
				    	
		    }catch(Exception e){
		    	e.getMessage();
		    }
	 }
		}
		MyComparator1 comparator = new MyComparator1(map);

	    Map<String, String> newMap = new TreeMap<String, String>(comparator);
	    newMap.putAll(map);
	    Map<String, String> map1 = new HashMap<String, String>(newMap);
	    int size=map1.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, String>> greatest = findGreatest(map1, size);
        for (Entry<String, String> entry : greatest)
        {
        	yoyvalues.add(entry.getValue());
        }
		return yoyvalues;

	}
	public void loseryoyvalidation(List<WebElement> listvalues,String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		String l="";
		String values1;
		List<String> categories=getLosersyoydata(filename,metrix,value,value2,value3,value4,value5);
		String actualtext="";
		for(String values:categories){
			if(values=="0"){
				values1="$"+"---"+" "+metrix;
			}
			else{
			values1= "▼" +"$" +values +" "+ metrix;
			}
			outer:
			for(WebElement list:listvalues){
				l=values1;
				actualtext=list.getText();
							if(list.getText().equals(values1)){
								l=values1;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				Assert.assertEquals(actualtext, l);
	}
		
	}
		public String getBigmoversLosersYOYandPVPvalues(Double metrix) throws Exception{
		Double value;
		String resrevenue1 = null;
		Double value1 = null;
		if(metrix.toString().contains("-")){
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
			resrevenue1=value1.toString();
		
		return resrevenue1;
		}else{
			Assert.assertTrue(true);
		}
		return resrevenue1;
}
	public String getBigmoversWinnersYOYandPVPvalues(Double metrix) throws Exception{
		Double value;
		String resrevenue1 = null;
		Double value1 = null;
		if(!(metrix.toString().contains("-"))){
		
		if(metrix.toString().contains("E8")){
		value=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
		value1=(double)value.longValue();
		}
		else if(metrix.toString().contains("E7")){
			value=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
			value1=(double)value.longValue();
			}
		else{
		value1=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
		
		}
			resrevenue1=value1.toString();
			return resrevenue1;
		}
		else{
			
			Assert.assertTrue(true);
		}
		return resrevenue1;
}
	public String getBigmoversLosersYOYandPVPconversionvalues(Double metrix) throws Exception{
		Double value;
		String resrevenue1 = null;
		Double value1 = null;
		if(metrix.toString().contains("-")){
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
		 resrevenue1 =conversioninthousands(value1);
		
		return resrevenue1;
		}else{
			Assert.assertTrue(true);
		}
		return resrevenue1;
}
	public String getBigmoversWinnersYOYandPVPconversionPvalues(Double metrix) throws Exception{
		Double value;
		String resrevenue1 = null;
		Double value1 = null;
		if(!(metrix.toString().contains("-"))){
		
		if(metrix.toString().contains("E8")){
		value=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
		value1=(double)value.longValue();
		}
		else if(metrix.toString().contains("E7")){
			value=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
			value1=(double)value.longValue();
			}
		else{
		value1=Double.parseDouble(metrix.toString().replaceAll(",", "").trim());
		
		}
		 resrevenue1 =conversioninthousands(value1);
			return resrevenue1;
		}
		else{
			
			Assert.assertTrue(true);
		}
		return resrevenue1;
}
	public String conversioninthousands(Double value1) throws Exception{
		String resrevenue1;
		if(value1>=0 && value1<1000){
			resrevenue1=gl.truncate(value1);
		}
		else if(value1>=1000 && value1<1000000){
			resrevenue1=gl.truncate(value1/1000) + "K";
			}
			else if(value1>=1000000 && value1<1000000000){
				resrevenue1=gl.truncate(value1/1000000) + "M";
			}
			else{
				resrevenue1=gl.truncate(value1/1000000000) + "K";
			}
		return resrevenue1;
	}
	public String conversion(Double value1) throws Exception{
		String resrevenue1;
		if(value1>=0 && value1<1000){
			resrevenue1=gl.truncate(value1);
		}
		else if(value1>=1000 && value1<1000000){
			resrevenue1=gl.truncate(value1/1000);
			}
			else if(value1>=1000000 && value1<1000000000){
				resrevenue1=gl.truncate(value1/1000000);
			}
			else{
				resrevenue1=gl.truncate(value1/1000000000);
			}
		return resrevenue1;
	}
	public List<String> getlosersdata(String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		List<String> category=new ArrayList<String>();
		Map<String,Double>lmap=getLosersresponseslistintdata(filename,metrix,value,value2,value3,value4,value5);
		MyComparator comparator = new MyComparator(lmap);

	    Map<String, Double> newMap = new TreeMap<String, Double>(comparator);
	    newMap.putAll(lmap);
	    Map<String, Double> map = new HashMap<String, Double>(newMap);
	    int size=map.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, Double>> greatest = findGreatest(map, size);
        for (Entry<String, Double> entry : greatest)
        {
            category.add(entry.getKey());
        }
        return category;
	}
	
	public List<String> getwinnersdata(String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		List<String> category=new ArrayList<String>();
		Map<String,Double>lmap=getwinnersresponseslistintdata(filename,metrix,value,value2,value3,value4,value5);
		MyComparator comparator = new MyComparator(lmap);
		 Map<String, Double> newMap = new TreeMap<String, Double>(comparator);
		    newMap.putAll(lmap);
		    Map<String, Double> map = new HashMap<String, Double>(newMap);
		    Set<Entry<String, Double>> entrySet = map.entrySet();
	        List<Entry<String, Double>> listOfentrySet = new ArrayList<Entry<String, Double>>(entrySet);
	        int size=listOfentrySet.size();
	       
		    Collections.sort(listOfentrySet, new SortByValue());
		    if(size>=5){
	        for(int i=size-5;i<size;i++){
	        	listOfentrySet.get(i).getKey();
	        	 category.add(listOfentrySet.get(i).getKey());
	        }
		    }
		    else{
		    	 for(int i=0;i<size;i++){
			        	listOfentrySet.get(i).getKey();
			        	 category.add(listOfentrySet.get(i).getKey());
			        }
		    }
	        return category;
	       
	}
	
	public Map<String, Double> getloserdata(String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		Map<String,Double>lmap=getLosersresponseslistintdata(filename,metrix,value,value2,value3,value4,value5);
		MyComparator comparator = new MyComparator(lmap);
		 Map<String, Double> newMap = new TreeMap<String, Double>(comparator);
		    newMap.putAll(lmap);
		    return newMap;
	}
	public Map<String, Double> getwinnerdata(String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		Map<String,Double>lmap=getwinnersresponseslistintdata(filename,metrix,value,value2,value3,value4,value5);
		MyComparator comparator = new MyComparator(lmap);
		 Map<String, Double> newMap = new TreeMap<String, Double>(comparator);
		    newMap.putAll(lmap);
		    return newMap;
	}
	public void categoriesandbrandlosersvalidation(List<WebElement> listvalues,String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		String l="";
		List<String> categories=getlosersdata(filename,metrix,value,value2,value3,value4,value5);
		String actualtext="";
		for(String values:categories){
			
			outer:
				
			for(WebElement list:listvalues){
				l=values;
				actualtext=list.getText();
				if(values != null){
							if(values.toLowerCase().equals(list.getText().toLowerCase())){
								l=values;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +list.getText().toLowerCase());
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l.toLowerCase());
								break outer;
							}
							else{
								continue;
							}
				}
				else{
				if(list.getText().equals("")){
					actualtext=list.getText();
					 l="";
				Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
				Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
				break outer;
				}
				else{
					continue;
				}
				}
		
	}
			Assert.assertEquals(actualtext.toLowerCase(), l.toLowerCase());
	}
	}
	public void categoriesandbrandwinnersvalidation(List<WebElement> listvalues,String filename,String metrix,String value,String value2,String value3,String value4,String value5){
		String l="";
		List<String> categories=getwinnersdata(filename,metrix,value,value2,value3,value4,value5);
		String actualtext="";
		for(String values:categories){
			
			outer:
			for(WebElement list:listvalues){
				l=values;
				actualtext=list.getText();
				if(values != null){
							if(values.toLowerCase().equals(list.getText().toLowerCase())){
								l=values;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +list.getText().toLowerCase());
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l.toLowerCase());
								break outer;
							}
							else{
								continue;
							}
				}
				else{
				if(list.getText().equals("")){
					actualtext=list.getText();
					 l="";
				Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
				Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
				break outer;
				}
				else{
					continue;
				}
				}
		
	}
			Assert.assertEquals(actualtext.toLowerCase(), l.toLowerCase());
	}
	}
	public List<String> Alllosersvalidation(String categoryfilename,String brandfilename,String yoy,String yoyrevenue,String dimension,String brand,String category,String pvp,String pvprevenue){
		Map<String, Double> lmap = new HashMap<String, Double>();
		List<String> category1=new ArrayList<String>();
		Map<String, Double> categorydata=getloserdata(categoryfilename,yoy,yoyrevenue,dimension,category,pvp,pvprevenue);
		Map<String, Double> branddata=getloserdata(brandfilename,yoy,yoyrevenue,dimension,brand,pvp,pvprevenue);
		lmap.putAll(categorydata);
		lmap.putAll(branddata);
		MyComparator comparator = new MyComparator(lmap);

	    Map<String, Double> newMap = new TreeMap<String, Double>(comparator);
	    newMap.putAll(lmap);
	    Map<String, Double> map = new HashMap<String, Double>(newMap);
	    int size=map.size();
	    if(size>=5){
	    	size=5;
	    }
	    else if(size<5){
	    	size=size;
	    }
	    List<Entry<String, Double>> greatest = findGreatest(map, size);
        for (Entry<String, Double> entry : greatest)
        {
            category1.add(entry.getKey());
        }	
        
		return category1;
	}
	
	public void Alllosersyoydatavalidation(List<WebElement> listvalues,String categoryfilename,String brandfilename,String yoy,String yoyrevenue,String dimension,String brand,String category,String pvp,String pvprevenue){
		String l="";
		List<String> categories=Alllosersvalidation(categoryfilename, brandfilename, yoy, yoyrevenue, dimension, brand, category, pvp,pvprevenue);
		String actualtext="";
		for(String values:categories){
			outer:
				
			for(WebElement list:listvalues){
				l=values;
				actualtext=list.getText();
				if(values != null){
							if(values.equals(list.getText())){
								l=values;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +list.getText());
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
								break outer;
							}
							else{
								continue;
							}
				}
				else{
				if(list.getText().equals("")){
					 l="";
					 actualtext=list.getText();
				Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
				Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
				break outer;
				}
				else{
					continue;
				}
				}
		
	}
			Assert.assertEquals(actualtext, l);
	}
	}
	public List<String> Allwinnersvalidation(String categoryfilename,String brandfilename,String yoy,String yoyrevenue,String dimension,String brand,String category,String pvp,String pvprevenue){
		Map<String, Double> lmap = new HashMap<String, Double>();
		List<String> category1=new ArrayList<String>();
		Map<String, Double> categorydata=getwinnerdata(categoryfilename,yoy,yoyrevenue,dimension,category,pvp,pvprevenue);
		Map<String, Double> branddata=getwinnerdata(brandfilename,yoy,yoyrevenue,dimension,brand,pvp,pvprevenue);
		lmap.putAll(categorydata);
		lmap.putAll(branddata);
		MyComparator comparator = new MyComparator(lmap);

	    Map<String, Double> newMap = new TreeMap<String, Double>(comparator);
	    newMap.putAll(lmap);
	    Map<String, Double> map = new HashMap<String, Double>(newMap);
	    Set<Entry<String, Double>> entrySet = map.entrySet();
        List<Entry<String, Double>> listOfentrySet = new ArrayList<Entry<String, Double>>(entrySet);
        int size=listOfentrySet.size();
   
	   Collections.sort(listOfentrySet,new SortByValue());
	    if(size>=5){
        for(int i=size-5;i<size;i++){
        	 category1.add(listOfentrySet.get(i).getKey());
        }
	    }
	   else{
	    	for(int i=0;i<size;i++){
	        	listOfentrySet.get(i).getKey();
	        	 category1.add(listOfentrySet.get(i).getKey());
	        }
	    }
        return category1;
	}
	public void Alllosersdatavalidation(List<WebElement> listvalues,String categoryfilename,String brandfilename,String yoy,String yoyrevenue,String dimension,String brand,String category,String pvp,String pvprevenue){
		String l="";
		List<String> categories=Alllosersvalidation(categoryfilename, brandfilename, yoy, yoyrevenue, dimension, brand, category, pvp,pvprevenue);
		String actualtext="";
		for(String values:categories){
			
			outer:
				
			for(WebElement list:listvalues){
				l=values;
				actualtext=list.getText();
				if(values != null){
							if(values.toLowerCase().equals(list.getText().toLowerCase())){
								l=values;
								actualtext=list.getText();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +list.getText().toLowerCase());
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l.toLowerCase());
								break outer;
							}
							else{
								continue;
							}
				}
				else{
				if(list.getText().equals("")){
					 l="";
					 actualtext=list.getText();
				Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
				Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
				break outer;
				}
				else{
					continue;
				}
				}
		
	}
			Assert.assertEquals(actualtext.toLowerCase(), l.toLowerCase());
	}
	}
	public void Allwinnersdatavalidation(List<WebElement> listvalues,String categoryfilename,String brandfilename,String yoy,String yoyrevenue,String dimension,String brand,String category,String pvp,String pvprevenue){
		String l="";
		
		List<String> categories=Allwinnersvalidation(categoryfilename, brandfilename, yoy, yoyrevenue, dimension, brand, category, pvp,pvprevenue);
		String actualtext="";
		for(String values:categories){
			
			outer:
				
			for(WebElement list:listvalues){
				l=values.toLowerCase();
				actualtext=list.getText().toLowerCase();
				if(values != null){
							if(values.toLowerCase().equals(list.getText().toLowerCase())){
								l=values;
								actualtext=list.getText().toLowerCase();
								Testbase.test.log(LogStatus.INFO,"","Actual data : " +list.getText().toLowerCase());
								Testbase.test.log(LogStatus.INFO,"","Expected data : " +l.toLowerCase());
								break outer;
							}
							else{
								continue;
							}
				}
				else{
				if(list.getText().equals("")){
					actualtext=list.getText().toLowerCase();
					 l="";
				Testbase.test.log(LogStatus.INFO,"","Actual data : " +actualtext);
				Testbase.test.log(LogStatus.INFO,"","Expected data : " +l);
				break outer;
				}
				else{
					continue;
				}
				}
		
	}
			Assert.assertEquals(actualtext.toLowerCase(), l.toLowerCase());
	}
	}
	private static <K, V extends Comparable<? super V>> List<Entry<K, V>> 
    findGreatest(Map<K, V> map, int n)
{
    Comparator<? super Entry<K, V>> comparator = 
        new Comparator<Entry<K, V>>()
    {
        @Override
        public int compare(Entry<K, V> e0, Entry<K, V> e1)
        {
            V v0 = e0.getValue();
            V v1 = e1.getValue();
            return v0.compareTo(v1);
        }
    };
    PriorityQueue<Entry<K, V>> highest = 
        new PriorityQueue<Entry<K,V>>(n, comparator);
    for (Entry<K, V> entry : map.entrySet())
    {
        highest.offer(entry);
        while (highest.size() > n)
        {
            highest.poll();
        }
    }

    List<Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
    while (highest.size() > 0)
    {
        result.add(highest.poll());
    }
    return result;
}
}
class MyComparator implements Comparator<Object> {

    Map<? extends String, ? extends Double> map1;

    public MyComparator(Map<? extends String, ? extends Double> map2) {
        this.map1 = map2;
    }

    public int compare(Object o1, Object o2) {

        if (map1.get(o2) == map1.get(o1))
            return 1;
        else
            return ((Double) map1.get(o2)).compareTo((Double)map1.get(o1));

    }
    
}
class MyComparator1 implements Comparator<Object> {

    Map<? extends String, ? extends String> map1;

    public MyComparator1(Map<? extends String, ? extends String> map2) {
        this.map1 = map2;
    }

    public int compare(Object o1, Object o2) {

        if (map1.get(o2) == map1.get(o1))
            return 1;
        else
            return ((String) map1.get(o2)).compareTo((String)map1.get(o1));

    }
    
}
class SortByValue implements Comparator<Map.Entry<String, Double>>{
	 
    @Override
    public int compare( Map.Entry<String,Double> entry1, Map.Entry<String,Double> entry2){
        return (entry1.getValue()).compareTo( entry2.getValue() );
    }
} 

    