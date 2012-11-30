package edu.myhorseshow.models;

import edu.myhorseshow.NamedObject;

public class ShowClass implements NamedObject
{
	public long getId() { return id; }
	public String getName() { return name; }
	public String getStartTime() { return starttime; }
	public Participation[] getParticipations() { return ownParticipation; }
	
	public void setId(long id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setStartTime(String time) { this.starttime = time; }
	public void setParticipations(Participation[] participations) { this.ownParticipation = participations; }
	
	private long id;
	private String name;
	private String starttime;
	private Participation[] ownParticipation;
}
