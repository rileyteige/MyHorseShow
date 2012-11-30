package edu.myhorseshow.models;

import edu.myhorseshow.events.EventDispatcher;


public class Contact extends EventDispatcher implements NamedObject
{
	public static class EventMeta
	{
		public static final String NAME_CHANGED = getFullName("NAME_CHANGED");
		public static final String EMAIL_CHANGED = getFullName("EMAIL_CHANGED");
		public static final String PHONE_CHANGED = getFullName("PHONE_CHANGED");
		public static final String OCCUPATION_CHANGED = getFullName("OCCUPATION_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	/** Returns the full name of a contact. */
	public String getName()
	{
		return new StringBuilder(getFirstName())
						.append(' ')
						.append(getLastName())
						.toString();
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getFirstName() { return firstname; }
	public void setFirstName(String name)
	{
		firstname = name;
		notifyChange(EventMeta.NAME_CHANGED);
	}
	
	public String getLastName() { return lastname; }
	public void setLastName(String name)
	{
		lastname = name;
		notifyChange(EventMeta.NAME_CHANGED);
	}
	
	public String getEmail() { return email; }
	public void setEmail(String email)
	{
		this.email = email;
		notifyChange(EventMeta.EMAIL_CHANGED);
	}
	
	public String getPhoneNumber() { return phone; }
	public void setPhoneNumber(String phone)
	{
		this.phone = phone;
		notifyChange(EventMeta.PHONE_CHANGED);
	}
	
	public Occupation getOccupation() { return occupation; }
	public void setOccupation(Occupation occupation)
	{
		this.occupation = occupation;
		notifyChange(EventMeta.OCCUPATION_CHANGED);
	}
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private Occupation occupation;
}
