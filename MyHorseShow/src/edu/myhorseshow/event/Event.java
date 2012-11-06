package edu.myhorseshow.event;

public class Event
{
	
	public long getId() { return id; }
	public String getName() { return name; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	
	private long id;
	private String name;
}
