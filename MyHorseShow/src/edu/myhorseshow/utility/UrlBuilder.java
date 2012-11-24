package edu.myhorseshow.utility;

import java.util.ArrayList;

public class UrlBuilder
{
	public UrlBuilder(String domain)
	{
		mDomain = domain;
	}

	public UrlBuilder addPath(int value) { return addPath("" + value); }
	public UrlBuilder addPath(long value) { return addPath("" + value); }
	public UrlBuilder addPath(String value)
	{
		mPaths.add(value);
		return this;
	}
	
	@Override
	public String toString()
	{
		StringBuilder rtnBuilder = new StringBuilder();
		
		rtnBuilder.append(mProtocol).append("://").append(mDomain);
			
		for (String path: mPaths)
		{
			rtnBuilder.append("/").append(path);
		}
		
		return rtnBuilder.toString();
	}
	
	private final ArrayList<String> mPaths = new ArrayList<String>();
	private String mProtocol = "http";
	private String mDomain;
}
