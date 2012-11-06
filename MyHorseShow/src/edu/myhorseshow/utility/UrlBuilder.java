package edu.myhorseshow.utility;

import java.util.HashMap;
import java.util.Map;

public class UrlBuilder
{
	public UrlBuilder(String domain)
	{
		mDomain = domain;
	}

	public void setScript(String scriptName)
	{
		mScript = scriptName;
	}
	
	public UrlBuilder setScriptChained(String scriptName)
	{
		setScript(scriptName);
		return this;
	}
	
	public UrlBuilder addArg(String key, String value)
	{
		mKeyValues.put(key, value);
		return this;
	}
	
	@Override
	public String toString()
	{
		StringBuilder rtnBuilder = new StringBuilder();
		
		rtnBuilder.append(mProtocol).append("://");
		rtnBuilder.append(mDomain).append("/");
		if (mScript != null)
		{
			rtnBuilder.append(mScript).append("?");
			
			for (String key: mKeyValues.keySet())
			{
				rtnBuilder.append(key).append("=").append(mKeyValues.get(key)).append("&");
			}
		}
		
		return rtnBuilder.toString();
	}
	
	private final Map<String, String> mKeyValues = new HashMap<String, String>();
	private String mProtocol = "http";
	private String mDomain;
	private String mScript;
}
