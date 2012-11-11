package edu.myhorseshow;

import edu.myhorseshow.division.Division;
import edu.myhorseshow.event.Event;
import edu.myhorseshow.showclass.ShowClass;
import edu.myhorseshow.user.User;

public final class UserInfo
{
	private static User currentUser;
	
	public static void setCurrentUser(User user)
	{
		currentUser = user;
	}
	
	public static User getCurrentUser()
	{
		return currentUser;
	}
	
	public static boolean isUserLoggedIn()
	{
		return currentUser != null;
	}
	
	public static boolean isCurrentUser(User user)
	{
		return user.getId() == getCurrentUser().getId();
	}
	
	public static Event getEvent(long eventId) throws NullPointerException
	{
		if (!isUserLoggedIn())
			return null;
		
		for (Event e: currentUser.getEvents())
		{
			if (e.getId() == eventId)
				return e;
		}
		
		throw new NullPointerException("Event id not found.");
	}
	
	public static ShowClass getClass(long eventId, long classId)
	{
		if (eventId <= 0 || classId <= 0)
			return null;
		
		Event event = getEvent(eventId);
		if (event == null)
			return null;
		
		for (Division division: event.getDivisions())
		{
			for (ShowClass showClass: division.getClasses())
			{
				if (showClass.getId() == classId)
					return showClass;
			}
		}
		
		return null;
	}
}
