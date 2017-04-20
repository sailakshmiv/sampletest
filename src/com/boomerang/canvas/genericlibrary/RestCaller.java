package com.boomerang.canvas.genericlibrary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

public class RestCaller {

	String responseStr;
	int statuscode;
	ResponseBody rb = new ResponseBody();
	String token;
	JSONObject js;
	
	
	

	public ResponseBody httpPost(String endpoint, String payload, String format) {
		String responseStr = null;
		try {
			
			HttpClient client = new DefaultHttpClient();
			final HttpParams httpParams = new BasicHttpParams();
		    HttpConnectionParams.setConnectionTimeout(httpParams, 30000);
		    client = new DefaultHttpClient(httpParams);		    
			String url = endpoint;
			HttpPost httppost = new HttpPost(url);
			
			if (format.equalsIgnoreCase("JSON")) {
				httppost.addHeader("Content-Type", "application/json");
				StringEntity setcontent = new StringEntity(payload);
				setcontent.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				httppost.setEntity(setcontent);
			} 
			HttpResponse response = client.execute(httppost);
			statuscode = response.getStatusLine().getStatusCode();
			InputStream in = response.getEntity().getContent();
			responseStr = Readjson.readInputStreamAsString(in);
			rb.setResponseCode(statuscode);
			rb.setResponseBody(responseStr);
		} catch (ClientProtocolException cp) {
			System.out.println("Connection failed " + cp.getMessage());
		} catch (IOException io) {
			System.out.println(io.getMessage());
		} catch (JSONException je) {
			System.out.println(je.getMessage());
		}
		return rb;

	}

	
}
