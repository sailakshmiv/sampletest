package com.boomerang.canvas.genericlibrary;

import org.json.JSONException;
import org.json.JSONObject;

public class ReadOutputdata {

	private String expectedOutput;
	public ReadOutputdata dataparser(String jsonFile)
	{
		try
		{
			 JSONObject js = new JSONObject(jsonFile);			
			 setExpectedOutput(js.getJSONObject("output").toString());
		}catch(JSONException je)
		{
			System.out.println(je.getMessage());
		}
		
		return null;		
	}
	public String getExpectedOutput() {
		return expectedOutput;
	}
	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
}
