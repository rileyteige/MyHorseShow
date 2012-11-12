package edu.myhorseshow;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.alert.Alert;
import edu.myhorseshow.alert.AlertAdapter;
import edu.myhorseshow.event.Event;
import edu.myhorseshow.event.EventAdapter;
import android.app.Activity;
import android.app.SearchManager.OnCancelListener;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import edu.myhorseshow.user.User;

public class AdminActivity extends Activity implements OnClickListener, OnItemClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		
		setupListAdapters();
		setupClickListeners();
	}

	public void onClick(View clickedView)
	{
		switch(clickedView.getId())
		{
		case R.id.admin_divisions_button:
			takedivisions();
			break;
		case R.id.admin_final_results_button:
			takefinal_results();
			break;
		case R.id.admin_instructor_button:
			takeinstructor();
			break;
		case R.id.admin_rider_button:
			takerider();
			break;
		case R.id.admin_arenas_button:
			takearenas();
			break;
		}
	}
	
	private void takedivisions()
	{
		sayNotImplemented("divisions()");
	}
	
	private void takefinal_results()
	{
		sayNotImplemented("final_results()");
	}
	
	private void takeinstructor()
	{
		sayNotImplemented("instructor()");
	}
	
	private void takerider()
	{
		sayNotImplemented("rider()");
	}
	
	private void takearenas()
	{
		sayNotImplemented("arenas()");
	}
	
	private void setupListAdapters()
	{
		ListView EventsListView = (ListView) findViewById(R.id.mylist);
		
		if (UserInfo.getCurrentUser().getEvents() != null)
			mEvents = new ArrayList<Event>(Arrays.asList(UserInfo.getCurrentUser().getEvents()));
		
		if (mEvents != null)
		{
			EventsListView.setAdapter(new EventAdapter(this, R.layout.row_view_event, mEvents));
			EventsListView.setOnItemClickListener(this);
			EventsListView.setEnabled(true);
		}
		else
		{
			EventsListView.setEnabled(false);
		}
	}
	
	private void setupClickListeners()
	{
		Button riders = (Button) findViewById(R.id.admin_rider_button);
		Button instructors = (Button) findViewById(R.id.admin_instructor_button);
		Button divisions = (Button) findViewById(R.id.admin_divisions_button);
		Button arenas = (Button) findViewById(R.id.admin_arenas_button);
		Button final_results = (Button) findViewById(R.id.admin_final_results_button);
		
		riders.setOnClickListener(this);
		instructors.setOnClickListener(this);
		divisions.setOnClickListener(this);
		arenas.setOnClickListener(this);
		final_results.setOnClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View clickedView, int position, long rowViewResourceId)
	{
		sayNotImplemented("clicking on the list");
		findViewById(R.id.admin_event_info).setVisibility(0);
		Event clickedEvent = mEvents.get(position);
		setEvent(UserInfo.getEvent(clickedEvent.getId()));
		
	}
	
	private static void sayNotImplemented(String what)
	{
		Log.w(TAG, what + " has not been implemented yet.");
	}
	
	private void setEvent(Event event) { mEvent = event; }
	
	private ArrayList<Event> mEvents;
	private Event mEvent;
	private static final String TAG = "AdminActivity";
}

