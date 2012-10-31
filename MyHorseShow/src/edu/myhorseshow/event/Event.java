package edu.myhorseshow.event;

public class Event
{
	public Event(long id)
	{
		mId = id;
	}
	
	public long getId()
	{
		return mId;
	}
	
	private long mId;
}
