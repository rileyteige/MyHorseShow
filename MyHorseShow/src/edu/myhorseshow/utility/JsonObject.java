package edu.myhorseshow.utility;

public class JsonObject
{	
	public JsonObject(String type, Object obj)
	{
		setType(type);
		setObj(obj);
	}
	
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	
	public Object getObj() { return obj; }
	public void setObj(Object obj) { this.obj = obj; }
	
	private String type;
	private Object obj;
}
