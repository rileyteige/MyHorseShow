package edu.myhorseshow.models;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.events.EventDispatcher;
import edu.myhorseshow.events.SimpleEvent;


public class User extends EventDispatcher
{
	private int id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private ShowEvent[] events;
	private long usefid;
	
	public User() { super(); }
	
	public static class EventMeta
	{
		public static final String EVENT_COUNT_CHANGED = EventMeta.class.getName() + "EVENT_COUNT_CHANGED";
	}
	
	public String getName() { return getFirstName() + " " + getLastName(); }
	
	public void addEvent(ShowEvent event)
	{
		ArrayList<ShowEvent> events = getEvents() != null ?
				new ArrayList<ShowEvent>(Arrays.asList(getEvents())) :
				new ArrayList<ShowEvent>();
		
		events.add(event);
		
		setEvents(events.toArray(new ShowEvent[events.size()]));
		dispatchEvent(new SimpleEvent(EventMeta.EVENT_COUNT_CHANGED));
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
