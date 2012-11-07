package edu.myhorseshow.user;

import edu.myhorseshow.event.Event;

public class User
{
	private int id;
	private String email;
	private String firstname;
	private String lastname;
	private Event[] events;
	
	public int getId() { return id; }
	public String getEmailAddress() { return email; }
	public String getFirstName() { return firstname; }
	public String getLastName() { return lastname; }
	public Event[] getEvents() { return events; }
	
	public void setId(int id) { this.id = id; }
	public void setEmailAdress(String email) { this.email = email; }
	public void setFirstName(String first) { this.firstname = first; }
	public void setLastName(String last) { this.lastname = last; }
	public void setEvents(Event[] events) { this.events = events; }
	
}
