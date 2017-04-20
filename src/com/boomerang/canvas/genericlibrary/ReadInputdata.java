package com.boomerang.canvas.genericlibrary;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadInputdata {

	private String input;
	public ReadInputdata dataparser(String jsonFile)
	{
		try
		{
			 JSONObject js = new JSONObject(jsonFile);			
			 setInput(js.getJSONObject("input").toString());			
		}catch(JSONException je)
		{
			System.out.println(je.getMessage());
		}
		
		return null;		
	}
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
}
