package edu.myhorseshow.events;

public class EventMetaBase
{
	protected static <T> String getFullName(Class<T> type, String str)
	{
		return type.getName() + "." + str;
	}
}
