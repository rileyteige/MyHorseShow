package edu.myhorseshow;

import java.util.ArrayList;

import edu.myhorseshow.models.Division;
import edu.myhorseshow.models.Event;
import edu.myhorseshow.models.Participation;
import edu.myhorseshow.models.ShowClass;
import edu.myhorseshow.models.User;

public final class UserInfo
{
	private static User currentUser;
	private static ArrayList<ShowClass> currentUserParticipatingClasses;
	private static ArrayList<Event> currentUserAdminEvents;
	private static int lastClassesEventId = 0;
	
	public static void setCurrentUser(User user)
	{
		resetCache();
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
	
	public static boolean isCurrentUser(int id)
	{
		return id == getCurrentUser().getId();
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
		if (!isUserLoggedIn())
			return null;
		
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
	
	public static ArrayList<ShowClass> getUserClasses(long eventId)
	{
		if (!isUserLoggedIn())
			return null;
		
		if (eventId <= 0)
			return null;
		
		// If this problem has already been solved for this event,
		// there's no need to do the work again.
		if (currentUserParticipatingClasses != null && eventId == lastClassesEventId)
			return currentUserParticipatingClasses;
		
		Event event = getEvent(eventId);
		if (event == null)
			return null;
		
		currentUserParticipatingClasses = new ArrayList<ShowClass>();
		
		if (event.getDivisions() == null)
			return currentUserParticipatingClasses;
		
		for (Division division: event.getDivisions())
		{
			if (division.getClasses() == null)
				continue;
			
			for (ShowClass showClass: division.getClasses())
			{
				if (showClass.getParticipations() == null)
					continue;
				
				for (Participation participation: showClass.getParticipations())
				{
					if (isCurrentUser(participation.getRider().getId()))
					{
						currentUserParticipatingClasses.add(showClass);
						break;
					}
				}
			}
		}
		
		return currentUserParticipatingClasses.size() > 0 ? currentUserParticipatingClasses : null;
	}
	
	public static ArrayList<Event> getUserAdminEvents()
	{
		if (!isUserLoggedIn())
			return null;
		
		if (currentUserAdminEvents != null)
			return currentUserAdminEvents;
		
		currentUserAdminEvents = new ArrayList<Event>();
		
		for (Event event: currentUser.getEvents())
		{
			if (isCurrentUser(event.getAdminId()))
				currentUserAdminEvents.add(event);
		}
		
		return currentUserAdminEvents.size() > 0 ? currentUserAdminEvents : null;
	}
	
	private static void resetCache()
	{
		currentUserParticipatingClasses = null;
		currentUserAdminEvents = null;
		lastClassesEventId = 0;
	}
}
