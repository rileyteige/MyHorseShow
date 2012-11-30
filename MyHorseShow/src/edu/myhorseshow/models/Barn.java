package edu.myhorseshow.models;

import edu.myhorseshow.events.EventDispatcher;


public class Barn extends EventDispatcher implements NamedObject
{
	public static class EventMeta
	{
		public static String NAME_CHANGED = getFullName("NAME_CHANGED");
		public static String STALLS_CHANGED = getFullName("STALLS_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public int getId() { return id; }
	public String getName() { return name; }
	public Stall[] getStalls() { return ownStall; }

	public void setId(int id) { this.id = id; }
	
	public void setName(String name) 
	{
		this.name = name;
		notifyChange(EventMeta.NAME_CHANGED);
	}
	
	public void setStalls(Stall[] stalls) 
	{
		this.ownStall = stalls;
		notifyChange(EventMeta.STALLS_CHANGED);
	}
	
	private int id;
	private String name;
	private Stall[] ownStall;
}
