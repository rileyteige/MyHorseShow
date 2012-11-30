package edu.myhorseshow.events;

public class SimpleEvent implements Event {

	public SimpleEvent(String type, Object source)
	{
		mType = type;
		mSource = source;
	}
	
	public String getType() { return mType; }
	public Object getSource() { return mSource; }
	public void setSource(Object source) { mSource = source; }

	private String mType;
	private Object mSource;
}
