package edu.myhorseshow.division;

import edu.myhorseshow.showclass.ShowClass;

public class Division
{
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public ShowClass[] getClasses() { return ownClass; }
	public void setClasses(ShowClass[] classes) { this.ownClass = classes; }
	
	private String name;
	private ShowClass[] ownClass;
}
