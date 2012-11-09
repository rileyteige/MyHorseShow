package edu.myhorseshow.user;

import edu.myhorseshow.event.Event;

public class User
{
	private int uid;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private Event[] events;
	private long usefid;
	
	public int getUid() { return uid; }
	public String getEmailAddress() { return email; }
	public String getPassword() { return password; }
	public String getFirstName() { return firstname; }
	public String getLastName() { return lastname; }
	public Event[] getEvents() { return events; }
	public long getUsefid() { return usefid; }
	
	public void setUid(int uid) { this.uid = uid; }
	public void setEmailAdress(String email) { this.email = email; }
	public void setPassword(String password) { this.password = password; }
	public void setFirstName(String first) { this.firstname = first; }
	public void setLastName(String last) { this.lastname = last; }
	public void setEvents(Event[] events) { this.events = events; }
	public void setUsefid(long usefid) { this.usefid = usefid; }
	
}
