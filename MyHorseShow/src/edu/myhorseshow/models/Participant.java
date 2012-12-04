package edu.myhorseshow.models;

public class Participant extends User
{
	public ShowClass[] getClasses() { return ownClass; }
	
	private ShowClass[] ownClass;
}
