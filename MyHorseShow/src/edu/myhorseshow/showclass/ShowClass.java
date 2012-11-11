package edu.myhorseshow.showclass;

public class ShowClass
{
	public long getId() { return id; }
	public String getName() { return name; }
	public Participation[] getParticipations() { return ownParticipation; }
	
	public void setId(long id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setParticipations(Participation[] participations) { this.ownParticipation = participations; }
	
	private long id;
	private String name;
	private Participation[] ownParticipation;
}
