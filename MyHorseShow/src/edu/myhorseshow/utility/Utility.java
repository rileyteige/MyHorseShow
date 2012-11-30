package edu.myhorseshow.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import edu.myhorseshow.Constants;

public final class Utility
{
	private static final String TAG = "Utility";
	private static Thread sMainThread = null;
	private static Dialog mDialog;
	
	public static void setMainThread(Thread thread)
	{
		sMainThread = thread;
	}
	
	public static void showProgressDialog(Context context, String header, String details)
	{
		if (verifyThreadAccess())
			return;
		
    	mDialog = ProgressDialog.show(context, header, details);	
	}
	
	public static void hideDialog()
	{
		if (!verifyThreadAccess() && mDialog != null)
		{
			mDialog.dismiss();
			mDialog = null;
		}
	}
	
	public static <T> T getJsonObject(String url, Class<T> rtnClass)
	{
		if (!verifyThreadAccess())
			return null;
		
    	HttpGet httpget = new HttpGet(url);
    	HttpClient httpclient = getDefaultHttpClient();
    	String result = null;
    	
    	try
    	{
    		HttpResponse response = httpclient.execute(httpget);
    		
    		HttpEntity entity = response.getEntity();
    		if (entity != null)
    		{
    			InputStream instream = entity.getContent();
    			result = Utility.convertStreamToString(instream);
    			instream.close();
    		}
    		
    	}
    	catch (Exception e)
    	{
    		Log.e(TAG, "Download failed.");
    		e.printStackTrace();
    	}
    	
    	try
    	{
    		return new Gson().fromJson(result, rtnClass);
    	}
    	catch (Exception e)
    	{
    		Log.e(TAG, "JSON PARSE EXCEPTION: " + e.getMessage());
    		Log.e(TAG, result);
    		return null;
    	}
	}
	
	public static String postJsonObject(String url, JsonObject jsonObject)
	{
		if (!verifyThreadAccess())
			return null;
		
		String json = new Gson().toJson(jsonObject);
		
		Log.d(TAG, "POSTING: " + json);
		
    	HttpClient httpClient = getDefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		
		try {
			httppost.setEntity(new StringEntity(json));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
		
		httppost.setHeader("Accept", "application/json");
		httppost.setHeader("Content-type", "application/json");
		String result = null;
		
		try
		{
			HttpResponse response = httpClient.execute(httppost);
			
			HttpEntity entity = response.getEntity();
			if (entity != null)
			{
				result = convertStreamToString(entity.getContent());
			}
		} catch (Exception e) { e.printStackTrace(); }
		
		return result;
	}
	
	private static String convertStreamToString(InputStream instream)
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		StringBuilder builder = new StringBuilder();
		
		String line = null;
		
		try
		{
			while ((line = reader.readLine()) != null)
				builder.append(line + "\n");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				instream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return builder.toString();
	}
	
	private static HttpClient getDefaultHttpClient()
	{
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, Constants.HTTP_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, Constants.HTTP_WAITING_TIMEOUT);
		return new DefaultHttpClient(params);
	}
	
	private static boolean verifyThreadAccess()
	{
		if (sMainThread == null)
		{
			Log.w(TAG, "Main thread has not been set in Utility.");
			return false;
		}
		
		if (Thread.currentThread() == sMainThread)
		{
			Log.w(TAG, "Cannot execute in main thread.");
			return false;
		}
		
		return true;
	}
	
}
