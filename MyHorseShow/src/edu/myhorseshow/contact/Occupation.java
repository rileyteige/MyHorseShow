package edu.myhorseshow.contact;

public class Occupation
{
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public String getPlural() { return plural; }
	public void setPlural(String plural) { this.plural = plural; }
	
	private int id;
	private String name;
	private String plural;
}
