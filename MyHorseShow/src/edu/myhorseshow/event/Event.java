package edu.myhorseshow.event;

public class Event
{
	
	public long getId() { return id; }
	public String getName() { return name; }
	public String getStartDate() { return sdate; }
	public String getEndDate() { return edate; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setStartDate(String sdate) { this.sdate = sdate; }
	public void setEndDate(String edate) { this.edate = edate; }
	
	private long id;
	private String name;
	private String sdate;
	private String edate;
}
