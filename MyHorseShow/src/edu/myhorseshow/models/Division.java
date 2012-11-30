package edu.myhorseshow.models;

import edu.myhorseshow.NamedObject;

public class Division implements NamedObject
{
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	
	public ShowClass[] getClasses() { return ownClass; }
	public void setClasses(ShowClass[] classes) { this.ownClass = classes; }
	
	private String name;
	private ShowClass[] ownClass;
}
