package edu.myhorseshow.models;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;

import edu.myhorseshow.events.EventDispatcher;


public class ShowClass extends EventDispatcher implements NamedObject
{
	public static class EventMeta
	{
		public static final String NAME_CHANGED = getFullName("NAME_CHANGED");
		public static final String START_TIME_CHANGED = getFullName("START_TIME_CHANGED");
		public static final String PARTICIPANTS_CHANGED = getFullName("PARTICIPANTS_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public void addParticipation(Participation item)
	{
		Log.d("ASD", "Adding participation");
		ArrayList<Participation> items = getParticipations() != null ?
				new ArrayList<Participation>(Arrays.asList(getParticipations())) :
				new ArrayList<Participation>();
		items.add(item);
		setParticipations(items.toArray(new Participation[items.size()]));
		Log.d("ASD", "" + getParticipations().length);
	}
	
	public long getId() { return id; }
	public String getName() { return name; }
	public String getStartTime() { return starttime; }
	public Participation[] getParticipations() { return ownParticipation; }
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public void setName(String name)
	{
		this.name = name;
		notifyChange(EventMeta.NAME_CHANGED);
	}
	
	public void setStartTime(String time)
	{
		this.starttime = time;
		notifyChange(EventMeta.START_TIME_CHANGED);
	}
	
	public void setParticipations(Participation[] participations)
	{
		this.ownParticipation = participations;
		notifyChange(EventMeta.PARTICIPANTS_CHANGED);
	}
	
	private long id;
	private String name;
	private String starttime;
	private Participation[] ownParticipation;
}
