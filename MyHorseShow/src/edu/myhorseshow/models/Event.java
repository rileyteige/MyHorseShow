package edu.myhorseshow.models;


public class Event
{	
	public long getId() { return id; }
	public int getAdminId() { return adminId; }
	public String getName() { return name; }
	public String getStartDate() { return startdate; }
	public String getEndDate() { return enddate; }
	public Division[] getDivisions() { return ownDivision; }
	public Barn[] getBarns() { return ownBarn; }
	public Contact[] getContacts() { return ownContact; }
	
	public void setId(int id) { this.id = id; }
	public void setAdminId(int id) { this.adminId = id; }
	public void setName(String name) { this.name = name; }
	public void setStartDate(String startdate) { this.startdate = startdate; }
	public void setEndDate(String enddate) { this.enddate = enddate; }
	public void setDivisions(Division[] ownDivision) { this.ownDivision = ownDivision; }
	public void setBarns(Barn[] barns) { this.ownBarn = barns; }
	public void setContacts(Contact[] contacts) { this.ownContact = contacts; }
	
	private long id;
	private int adminId;
	private String name;
	private String startdate;
	private String enddate;
	private Division[] ownDivision;
	private Barn[] ownBarn;
	private Contact[] ownContact;
}
