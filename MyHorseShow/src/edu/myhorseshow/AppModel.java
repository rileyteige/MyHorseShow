package edu.myhorseshow;

import java.util.ArrayList;

import edu.myhorseshow.events.EventDispatcher;
import edu.myhorseshow.models.Division;
import edu.myhorseshow.models.ShowEvent;
import edu.myhorseshow.models.Participation;
import edu.myhorseshow.models.ShowClass;
import edu.myhorseshow.models.User;

public class AppModel extends EventDispatcher
{
	private static AppModel mInstance;
	private User currentUser;
	private ArrayList<ShowClass> currentUserParticipatingClasses;
	private ArrayList<ShowEvent> currentUserAdminEvents;
	private int lastClassesEventId = 0;
	
	public static class EventMeta
	{
		public static final String CURRENT_USER_CHANGED = "AppModel.EventMeta.CURRENT_USER_CHANGED";
	}
	
	private AppModel() { super(); }
	public static AppModel getInstance()
	{
		if (mInstance == null)
			mInstance = new AppModel();
		return mInstance;
	}
	
	public void setCurrentUser(User user)
	{
		resetCache();
		currentUser = user;
		notifyChange(EventMeta.CURRENT_USER_CHANGED);
	}
	
	public User getCurrentUser()
	{
		return currentUser;
	}
	
	public boolean isUserLoggedIn()
	{
		return currentUser != null;
	}
	
	public boolean isCurrentUser(int id)
	{
		return id == getCurrentUser().getId();
	}
	
	public ShowEvent getEvent(long eventId) throws NullPointerException
	{
		if (!isUserLoggedIn())
			return null;
		
		for (ShowEvent e: currentUser.getShowEvents())
		{
			if (e.getId() == eventId)
				return e;
		}
		
		throw new NullPointerException("Event id not found.");
	}
	
	public ShowClass getClass(long eventId, long classId)
	{
		if (!isUserLoggedIn())
			return null;
		
		if (eventId <= 0 || classId <= 0)
			return null;
		
		ShowEvent event = getEvent(eventId);
		if (event == null)
			return null;
		
		for (Division division: event.getDivisions())
		{
			if (division.getClasses() == null)
				continue;
			
			for (ShowClass showClass: division.getClasses())
			{
				if (showClass.getId() == classId)
					return showClass;
			}
		}
		
		return null;
	}
	
	public ArrayList<ShowClass> getEventClasses(long eventId)
	{
		if (!isUserLoggedIn())
			return null;
		
		if (eventId <= 0)
			return null;
		
		ShowEvent event = getEvent(eventId);
		if (event == null)
			return null;
		
		ArrayList<ShowClass> classes = new ArrayList<ShowClass>();
		
		if (event.getDivisions() == null)
			return null;
		
		for (Division division: event.getDivisions())
		{
			if (division.getClasses() == null)
				continue;
			
			for (ShowClass showClass: division.getClasses())
				classes.add(showClass);
		}
		
		return classes;
	}
	
	public ArrayList<ShowClass> getUserClasses(long eventId)
	{
		if (!isUserLoggedIn())
			return null;
		
		if (eventId <= 0)
			return null;
		
		// If this problem has already been solved for this event,
		// there's no need to do the work again.
		if (currentUserParticipatingClasses != null && eventId == lastClassesEventId)
			return currentUserParticipatingClasses;
		
		ShowEvent event = getEvent(eventId);
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
	
	public ArrayList<ShowEvent> getUserAdminEvents()
	{
		if (!isUserLoggedIn())
			return null;
		
		if (currentUserAdminEvents != null)
			return currentUserAdminEvents;
		
		currentUserAdminEvents = new ArrayList<ShowEvent>();
		
		for (ShowEvent event: currentUser.getShowEvents())
		{
			if (isCurrentUser(event.getAdminId()))
				currentUserAdminEvents.add(event);
		}
		
		return currentUserAdminEvents.size() > 0 ? currentUserAdminEvents : null;
	}
	
	private void resetCache()
	{
		currentUserParticipatingClasses = null;
		currentUserAdminEvents = null;
		lastClassesEventId = 0;
	}
}
