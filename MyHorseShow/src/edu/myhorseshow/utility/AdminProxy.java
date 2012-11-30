package edu.myhorseshow.utility;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.util.Log;
import edu.myhorseshow.Constants;
import edu.myhorseshow.activities.AdminActivity;
import edu.myhorseshow.models.ShowEvent;
import edu.myhorseshow.models.User;

public final class AdminProxy
{
	private AdminProxy() {}
	
	public static void createEvent(final AdminActivity activity, ShowEvent event)
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
	
	public static void addUserToEvent(final AdminActivity activity, String email, long eventId)
	{
		new AsyncTask<Object, Integer, ShowEvent>()
		{
			public ShowEvent doInBackground(Object... params)
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
			
			public void onPostExecute(ShowEvent event)
			{
				activity.signalProxyFinished(AdminActivity.ADD_USER_TO_EVENT, event);
			}
		}
		.execute(email, eventId);
	}
	
	private static final String TAG = "AdminProxy";
}
