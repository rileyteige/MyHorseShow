package edu.myhorseshow.event;

public class Event
{
	
	public long getId() { return id; }
	public long getAid() { return aid; }
	public String getName() { return name; }
	public String getStartDate() { return startdate; }
	public String getEndDate() { return enddate; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setStartDate(String startdate) { this.startdate = startdate; }
	public void setEndDate(String enddate) { this.enddate = enddate; }
	
	private long id;
	private long aid;
	private String name;
	private String startdate;
	private String enddate;
}
