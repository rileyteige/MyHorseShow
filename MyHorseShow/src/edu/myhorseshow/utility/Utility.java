package edu.myhorseshow.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import edu.myhorseshow.user.User;

public final class Utility
{
	private static final String TAG = "Utility";
	private static Thread sMainThread = null;
	
	public static void setMainThread(Thread thread)
	{
		sMainThread = thread;
	}
	
	public static String convertStreamToString(InputStream instream)
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
	
	public static <T> T getJsonObject(String url, Class<T> rtnClass)
	{
		if (!VerifyThreadAccess())
			return null;
		
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpGet httpget = new HttpGet(url);
    	HttpResponse response;
    	String result = null;
    	
    	try
    	{
    		response = httpclient.execute(httpget);
    		Log.d(TAG, response.getStatusLine().toString());
    		
    		HttpEntity entity = response.getEntity();
    		if (entity != null)
    		{
    			InputStream instream = entity.getContent();
    			result = Utility.convertStreamToString(instream);
    			instream.close();
    		}
    		
    	} catch (Exception e) { e.printStackTrace(); }
    	
    	try
    	{
    		return new Gson().fromJson(result, rtnClass);
    	}
    	catch (JsonParseException e)
    	{
    		Log.w(TAG, result);
    		return null;
    	}
	}
	
	private static boolean VerifyThreadAccess()
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
