package edu.myhorseshow.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Utility
{
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
	
}
