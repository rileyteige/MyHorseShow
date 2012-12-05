package edu.myhorseshow.proxies;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;
import edu.myhorseshow.Constants;
import edu.myhorseshow.activities.AdminActivity;
import edu.myhorseshow.models.Division;
import edu.myhorseshow.models.Participant;
import edu.myhorseshow.models.Participation;
import edu.myhorseshow.models.ShowClass;
import edu.myhorseshow.models.ShowEvent;
import edu.myhorseshow.models.User;
import edu.myhorseshow.utility.JsonObject;
import edu.myhorseshow.utility.UrlBuilder;
import edu.myhorseshow.utility.Utility;

public final class AdminProxy
{
	private AdminProxy() {}
	
	public static void createShowEvent(final AdminActivity activity, ShowEvent event)
	{
		new AsyncTask<ShowEvent, Integer, ShowEvent>()
    	{
    		@Override
			protected ShowEvent doInBackground(ShowEvent... events)
    		{
    			if (events.length != 1)
    				return null;
    			
    			ShowEvent event = events[0];
    			
    			String url = new UrlBuilder(Constants.SERVER_DOMAIN)
    					.addPath(Constants.TYPE_EVENT)
    					.toString();
		    	
		    	String result = Utility.postJsonObject(url, new JsonObject(Constants.TYPE_EVENT, event));
		    	if (result != null)
		    		Log.d(TAG, result);
		    	return new Gson().fromJson(result, ShowEvent.class);
    		}
    		
    		@Override
			protected void onPostExecute(ShowEvent newEvent)
    		{
    			activity.signalProxyFinished(AdminActivity.CREATE_EVENT, newEvent);
    		}
    	}
		.execute(event);
	}
	
	public static void addClassToDivision(final AdminActivity activity, final long divisionId, final String className)
	{
		new AsyncTask<Object, Integer, ShowClass>()
		{
			protected ShowClass doInBackground(Object... params)
			{
				ShowClass newShowClass = new ShowClass();
				newShowClass.setName(className);
				
				String url = new UrlBuilder(Constants.SERVER_DOMAIN)
					.addPath(Constants.TYPE_DIVISIONS)
					.addPath(divisionId)
					.toString();
				
				String result = Utility.postJsonObject(url, new JsonObject(Constants.TYPE_CLASS, newShowClass));
				if (result == null)
					return null;
				
				return new Gson().fromJson(result, ShowClass.class);
			}
			
			protected void onPostExecute(ShowClass result)
			{
				activity.signalProxyFinished(AdminActivity.ADD_CLASS_TO_DIVISION, result);
			}
		}
		.execute();
	}
	
	public static void addDivisionToShowEvent(final AdminActivity activity, long eventId, String name)
	{
		new AsyncTask<Object, Integer, Division>()
		{
			protected Division doInBackground(Object... params)
			{
				if (params.length != 2)
					return null;
				
				long eventId = (Long)params[0];
				String name = (String)params[1];
				
				Division newDivision = new Division();
				newDivision.setName(name);
				
				String url = new UrlBuilder(Constants.SERVER_DOMAIN)
					.addPath(Constants.TYPE_EVENT)
					.addPath(eventId)
					.toString();
				
				String result = Utility.postJsonObject(url, new JsonObject(Constants.TYPE_DIVISION, newDivision));
				if (result != null)
					Log.d(TAG, result);
				else
					return null;
				
				return new Gson().fromJson(result, Division.class);
			}
			
			protected void onPostExecute(Division result)
			{
				activity.signalProxyFinished(AdminActivity.ADD_DIVISION_TO_EVENT, result);
			}
		}
		.execute(eventId, name);
	}
	
	public static void addUserToShowEvent(final AdminActivity activity, String email, long eventId)
	{
		new AsyncTask<Object, Integer, Participant>()
		{
			public Participant doInBackground(Object... params)
			{
				if (params.length != 2)
					return null;
				
				String email = (String)params[0];
				long eventId = (Long)params[1];
				
				User newUser = new User();
				newUser.setEmailAdress(email);
				
				String url = new UrlBuilder(Constants.SERVER_DOMAIN)
						.addPath(Constants.TYPE_EVENT)
						.addPath(eventId)
						.toString();
				
				String result = Utility.postJsonObject(url, new JsonObject(Constants.TYPE_USER, newUser));
				Log.d(TAG, result);
				
				return new Gson().fromJson(result, Participant.class);
			}
			
			public void onPostExecute(Participant user)
			{
				activity.signalProxyFinished(AdminActivity.ADD_USER_TO_EVENT, user);
			}
		}
		.execute(email, eventId);
	}
	
	public static void addUserToClass(final AdminActivity activity, long eventId, long classId, long userId, String horseName)
	{
		new AsyncTask<Object, Integer, ShowClass>()
		{
			public ShowClass doInBackground(Object... params)
			{
				if (params.length != 4)
					return null;
				
				long eventId = (Long)params[0];
				long classId = (Long)params[1];
				long userId = (Long)params[2];
				String horseName = (String)params[3];
				
				Participation newParticipation = new Participation();
				newParticipation.setId((int)userId);
				newParticipation.setHorse(horseName);
				
				String url = new UrlBuilder(Constants.SERVER_DOMAIN)
						.addPath(Constants.TYPE_EVENTS)
						.addPath(eventId)
						.addPath(Constants.TYPE_CLASSES)
						.addPath(classId)
						.toString();
				String result = Utility.postJsonObject(url, new JsonObject(Constants.TYPE_USER, newParticipation));
				Log.d(TAG, result);
				
				try {
					return new Gson().fromJson(result, ShowClass.class);
				} catch (Exception e) {
					return null;
				}
			}
			
			public void onPostExecute(ShowClass result)
			{
				activity.signalProxyFinished(AdminActivity.ADD_USER_TO_CLASS, result);
			}
		}
		.execute(eventId, classId, userId, horseName);
	}
	
	private static final String TAG = "AdminProxy";
}
