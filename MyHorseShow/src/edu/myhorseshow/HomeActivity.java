package edu.myhorseshow;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import edu.myhorseshow.alert.*;
import edu.myhorseshow.event.*;

public class HomeActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		welcomeUser(getIntent().getStringExtra(MainActivity.USERNAME));
		setupListAdapters();
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
		alertsListView.setAdapter(new AlertAdapter(this, R.layout.row_view_alert, alerts));
	}
}
