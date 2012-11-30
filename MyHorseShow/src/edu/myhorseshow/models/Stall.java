package edu.myhorseshow.models;

import edu.myhorseshow.events.EventDispatcher;
import edu.myhorseshow.events.SimpleEvent;


public class Stall extends EventDispatcher implements NamedObject
{
	public Stall() { super(); }
	
	public static class EventMeta
	{
		public static final String OCCUPANT_CHANGED = getFullName("OCCUPANT_CHANGED");
		public static final String NAME_CHANGED = getFullName("NAME_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public int getId() { return id; }
	public String getName() { return name; }
	public User getOccupant() { return occupant; }
	
	public void setName(String name)
	{
		this.name = name;
		notifyChange(EventMeta.NAME_CHANGED);
	}
	
	public void setOccupant(User occupant)
	{
		this.occupant = occupant;
		notifyChange(EventMeta.OCCUPANT_CHANGED);
	}
	
	private void notifyChange(String type)
	{
		dispatchEvent(new SimpleEvent(type));
	}
	
	private int id;
	private String name;
	private User occupant;
}
