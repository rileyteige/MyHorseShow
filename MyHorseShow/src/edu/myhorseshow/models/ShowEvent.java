package edu.myhorseshow.models;

import edu.myhorseshow.events.EventDispatcher;


public class ShowEvent extends EventDispatcher
{	
	public static class EventMeta
	{
		private static final String ADMIN_CHANGED = getFullName("ADMIN_CHANGED");
		private static final String NAME_CHANGED = getFullName("NAME_CHANGED");
		private static final String DATE_CHANGED = getFullName("DATE_CHANGED");
		private static final String DIVISIONS_CHANGED = getFullName("DIVISIONS_CHANGED");
		private static final String BARNS_CHANGED = getFullName("BARNS_CHANGED");
		private static final String CONTACTS_CHANGED = getFullName("CONTACTS_CHANGED");
		private static final String PARTICIPANTS_CHANGED = getFullName("PARTICIPANTS_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public long getId() { return id; }
	public int getAdminId() { return adminId; }
	public String getName() { return name; }
	public String getStartDate() { return startdate; }
	public String getEndDate() { return enddate; }
	public Division[] getDivisions() { return ownDivision; }
	public Barn[] getBarns() { return ownBarn; }
	public Contact[] getContacts() { return ownContact; }
	public Participant[] getParticipants() { return sharedUser; }
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public void setAdminId(int id)
	{
		this.adminId = id;
		notifyChange(EventMeta.ADMIN_CHANGED);
	}
	
	public void setName(String name)
	{
		this.name = name;
		notifyChange(EventMeta.NAME_CHANGED);
	}
	
	public void setStartDate(String startdate)
	{
		this.startdate = startdate;
		notifyChange(EventMeta.DATE_CHANGED);
	}
	
	public void setEndDate(String enddate)
	{
		this.enddate = enddate;
		notifyChange(EventMeta.DATE_CHANGED);
	}
	
	public void setDivisions(Division[] ownDivision)
	{
		this.ownDivision = ownDivision;
		notifyChange(EventMeta.DIVISIONS_CHANGED);
	}
	
	public void setBarns(Barn[] barns)
	{
		this.ownBarn = barns;
		notifyChange(EventMeta.BARNS_CHANGED);
	}
	
	public void setContacts(Contact[] contacts)
	{
		this.ownContact = contacts;
		notifyChange(EventMeta.CONTACTS_CHANGED);
	}
	
	public void setParticipants(Participant[] users)
	{
		this.sharedUser = users;
		notifyChange(EventMeta.PARTICIPANTS_CHANGED);
	}
	
	private long id;
	private int adminId;
	private String name;
	private String startdate;
	private String enddate;
	private Division[] ownDivision;
	private Barn[] ownBarn;
	private Contact[] ownContact;
	private Participant[] sharedUser;
}
