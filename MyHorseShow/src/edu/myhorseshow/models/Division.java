package edu.myhorseshow.models;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.events.EventDispatcher;


public class Division extends EventDispatcher implements NamedObject
{
	public static class EventMeta
	{
		public static final String NAME_CHANGED = getFullName("NAME_CHANGED");
		public static final String CLASSES_CHANGED = getFullName("CLASSES_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public void addClass(ShowClass showClass)
	{
		ArrayList<ShowClass> classes = getClasses() != null ?
				new ArrayList<ShowClass>(Arrays.asList(getClasses())) :
				new ArrayList<ShowClass>();
		classes.add(showClass);
		setClasses(classes.toArray(new ShowClass[classes.size()]));
	}
	
	public int getId() { return id; }
	
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
	
	private int id;
	private String name;
	private ShowClass[] ownClass;
}
