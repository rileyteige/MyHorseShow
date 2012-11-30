package edu.myhorseshow.models;

import edu.myhorseshow.events.EventDispatcher;


public class Division extends EventDispatcher implements NamedObject
{
	public static class EventMeta
	{
		public static final String NAME_CHANGED = getFullName("NAME_CHANGED");
		public static final String CLASSES_CHANGED = getFullName("CLASSES_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public String getName() { return name; }
	public void setName(String name)
	{ 
		this.name = name;
		notifyChange(EventMeta.NAME_CHANGED);
	}
	
	public ShowClass[] getClasses() { return ownClass; }
	public void setClasses(ShowClass[] classes)
	{
		this.ownClass = classes;
		notifyChange(EventMeta.CLASSES_CHANGED);
	}
	
	private String name;
	private ShowClass[] ownClass;
}
