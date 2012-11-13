package edu.myhorseshow.event;

import edu.myhorseshow.barn.Barn;
import edu.myhorseshow.contact.Contact;
import edu.myhorseshow.division.Division;
import edu.myhorseshow.user.User;

public class Event
{
	
	public long getId() { return id; }
	public User getAdmin() { return admin; }
	public String getName() { return name; }
	public String getStartDate() { return startdate; }
	public String getEndDate() { return enddate; }
	public Division[] getDivisions() { return ownDivision; }
	public Barn[] getBarns() { return ownBarn; }
	public Contact[] getContacts() { return ownContact; }
	
	public void setId(int id) { this.id = id; }
	public void setAdmin(User admin) { this.admin = admin; }
	public void setName(String name) { this.name = name; }
	public void setStartDate(String startdate) { this.startdate = startdate; }
	public void setEndDate(String enddate) { this.enddate = enddate; }
	public void setDivisions(Division[] ownDivision) { this.ownDivision = ownDivision; }
	public void setBarns(Barn[] barns) { this.ownBarn = barns; }
	public void setContacts(Contact[] contacts) { this.ownContact = contacts; }
	
	private long id;
	private User admin;
	private String name;
	private String startdate;
	private String enddate;
	private Division[] ownDivision;
	private Barn[] ownBarn;
	private Contact[] ownContact;
}
