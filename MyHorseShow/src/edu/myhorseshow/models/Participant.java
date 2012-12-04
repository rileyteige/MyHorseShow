package edu.myhorseshow.models;

import java.util.ArrayList;
import java.util.Arrays;

import android.util.Log;

public class Participant extends User
{
	public void addClass(ShowClass showClass)
	{
		Log.d("PARTICIPANT", "Adding class");
		ArrayList<ShowClass> classes = new ArrayList<ShowClass>(Arrays.asList(getClasses()));
		classes.add(showClass);
		ownClass = classes.toArray(new ShowClass[classes.size()]);
	}
	
	public ShowClass[] getClasses() { return ownClass; }
	
	private ShowClass[] ownClass;
}
