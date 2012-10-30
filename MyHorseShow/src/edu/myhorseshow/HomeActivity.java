package edu.myhorseshow;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import edu.myhorseshow.alert.*;
import edu.myhorseshow.event.*;

public class HomeActivity extends Activity implements OnItemClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		welcomeUser(getIntent().getStringExtra(MainActivity.USERNAME));
		setupListAdapters();
	}
	
	public void onItemClick(AdapterView<?> parent, View clickedView, int position, long resourceId)
	{
		switch(parent.getId())
		{
		case R.id.home_upcoming_events_listview:
			upcomingEventClicked(clickedView, position, resourceId);
			break;
		case R.id.home_alerts_listview:
			alertClicked(clickedView, position, resourceId);
			break;
		}
	}
	
	private void welcomeUser(String username)
	{
		TextView headerTextView = (TextView) findViewById(R.id.home_header_text_view);
		headerTextView.setText(String.format(getString(R.string.welcome_header), username));
	}
	
	private void setupListAdapters()
	{
		ListView upcomingEventsListView = (ListView) findViewById(R.id.home_upcoming_events_listview);
		ListView alertsListView = (ListView) findViewById(R.id.home_alerts_listview);
		
		ArrayList<Alert> alerts = new ArrayList<Alert>();
		ArrayList<Event> events = new ArrayList<Event>();
		for (int i = 0; i < 30; i++)
		{
			alerts.add(new Alert());
			events.add(new Event());
		}
		
		upcomingEventsListView.setAdapter(new EventAdapter(this, R.layout.row_view_event, events));
		upcomingEventsListView.setOnItemClickListener(this);
		
		alertsListView.setAdapter(new AlertAdapter(this, R.layout.row_view_alert, alerts));
		alertsListView.setOnItemClickListener(this);
	}
	
	private void upcomingEventClicked(View clickedView, int position, long resourceId)
	{
		sayNotImplemented("upcomingEventClicked()");
	}
	
	private void alertClicked(View clickedView, int position, long resourceId)
	{
		sayNotImplemented("alertClicked()");
	}
	
	private static void sayNotImplemented(String what)
	{
		Log.w(TAG, what + " has not been implemented yet.");
	}
	
	private static final String TAG = "HomeActivity";
}
