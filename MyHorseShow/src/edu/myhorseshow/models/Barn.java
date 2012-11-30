package edu.myhorseshow.models;


public class Barn implements NamedObject
{
	public int getId() { return id; }
	public String getName() { return name; }
	public Stall[] getStalls() { return ownStall; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setStalls(Stall[] stalls) { this.ownStall = stalls; }
	
	private int id;
	private String name;
	private Stall[] ownStall;
}
