package edu.myhorseshow.utility;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;
import edu.myhorseshow.Constants;
import edu.myhorseshow.activities.AdminActivity;
import edu.myhorseshow.models.Event;
import edu.myhorseshow.models.User;

public final class AdminProxy
{
	private AdminProxy() {}
	
	public static void createEvent(final AdminActivity activity, Event event)
	{
		new AsyncTask<Event, Integer, Event>()
    	{
    		@Override
			protected Event doInBackground(Event... events)
    		{
    			if (events.length != 1)
    				return null;
    			
    			Event event = events[0];
    			
    			String url = new UrlBuilder(Constants.SERVER_DOMAIN)
    					.addPath(Constants.TYPE_EVENT)
    					.toString();
		    	
		    	String result = Utility.postJsonObject(url, new JsonObject(Constants.TYPE_EVENT, event));
		    	if (result != null)
		    		Log.d(TAG, result);
		    	return new Gson().fromJson(result, Event.class);
    		}
    		
    		@Override
			protected void onPostExecute(Event newEvent)
    		{
    			activity.signalProxyFinished(AdminActivity.CREATE_EVENT, newEvent);
    		}
    	}
		.execute(event);
	}
	
	public static void addUserToEvent(final AdminActivity activity, String email, long eventId)
	{
		new AsyncTask<Object, Integer, Event>()
		{
			public Event doInBackground(Object... params)
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
				
				return null;
			}
			
			public void onPostExecute(Event event)
			{
				activity.signalProxyFinished(AdminActivity.ADD_USER_TO_EVENT, event);
			}
		}
		.execute(email, eventId);
	}
	
	private static final String TAG = "AdminProxy";
}
