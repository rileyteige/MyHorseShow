package edu.myhorseshow.models;

import java.util.ArrayList;
import java.util.Arrays;


public class User
{
	private int id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private ShowEvent[] events;
	private long usefid;
	
	public String getName() { return getFirstName() + " " + getLastName(); }
	
	public void addEvent(ShowEvent event)
	{
		ArrayList<ShowEvent> events = getEvents() != null ?
				new ArrayList<ShowEvent>(Arrays.asList(getEvents())) :
				new ArrayList<ShowEvent>();
		
		events.add(event);
		
		setEvents(events.toArray(new ShowEvent[events.size()]));
	}
	
	public int getId() { return id; }
	public String getEmailAddress() { return email; }
	public String getPassword() { return password; }
	public String getFirstName() { return firstname; }
	public String getLastName() { return lastname; }
	public ShowEvent[] getEvents() { return events; }
	public long getUsefid() { return usefid; }
	
	public void setId(int id) { this.id = id; }
	public void setEmailAdress(String email) { this.email = email; }
	public void setPassword(String password) { this.password = password; }
	public void setFirstName(String first) { this.firstname = first; }
	public void setLastName(String last) { this.lastname = last; }
	public void setEvents(ShowEvent[] events) { this.events = events; }
	public void setUsefid(long usefid) { this.usefid = usefid; }
	
}
