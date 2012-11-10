package edu.myhorseshow;

import edu.myhorseshow.event.Event;
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
	
	public static Event getEvent (long eventId) throws NullPointerException
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
}
