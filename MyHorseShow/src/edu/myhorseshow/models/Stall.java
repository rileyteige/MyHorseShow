package edu.myhorseshow.models;


public class Stall implements NamedObject
{
	public int getId() { return id; }
	public String getName() { return name; }
	public User getOccupant() { return occupant; }
	
	public void setId(int id) { this.id = id; }
	public void setName(String name) { this.name = name; }
	public void setOccupant(User occupant) { this.occupant = occupant; }
	
	private int id;
	private String name;
	private User occupant;
}
