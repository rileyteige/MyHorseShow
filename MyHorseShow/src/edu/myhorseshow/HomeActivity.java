package edu.myhorseshow;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import edu.myhorseshow.alert.*;
import edu.myhorseshow.event.*;

public class HomeActivity extends Activity implements OnItemClickListener, OnClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		welcomeUser(getIntent().getStringExtra(MainActivity.USERNAME));
		setupClickListeners();
		setupListAdapters();
	}
	
	public void onClick(View clickedView)
	{
		switch(clickedView.getId())
		{
		case R.id.home_admin_portal_button:
			takeAdminPortal();
			break;
		case R.id.home_instructor_portal_button:
			takeInstructorPortal();
			break;
		case R.id.home_rider_portal_button:
			takeRiderPortal();
			break;
		}
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
	
	private void setupClickListeners()
	{
		Button adminPortalButton = (Button) findViewById(R.id.home_admin_portal_button);
		Button instructorPortalButton = (Button) findViewById(R.id.home_instructor_portal_button);
		Button riderPortalButton = (Button) findViewById(R.id.home_rider_portal_button);
		
		adminPortalButton.setOnClickListener(this);
		instructorPortalButton.setOnClickListener(this);
		riderPortalButton.setOnClickListener(this);
	}
	
	private void setupListAdapters()
	{
		ListView upcomingEventsListView = (ListView) findViewById(R.id.home_upcoming_events_listview);
		ListView alertsListView = (ListView) findViewById(R.id.home_alerts_listview);
		
		mAlerts = new ArrayList<Alert>();
		mEvents = new ArrayList<Event>();
		for (int i = 0; i < 30; i++)
		{
			mAlerts.add(new Alert(i + 1));
			mEvents.add(new Event(i + 1));
		}
		
		upcomingEventsListView.setAdapter(new EventAdapter(this, R.layout.row_view_event, mEvents));
		upcomingEventsListView.setOnItemClickListener(this);
		
		alertsListView.setAdapter(new AlertAdapter(this, R.layout.row_view_alert, mAlerts));
		alertsListView.setOnItemClickListener(this);
	}
	
	private void upcomingEventClicked(View clickedView, int position, long resourceId)
	{
		Event clickedEvent = mEvents.get(position);
		Intent eventIntent = new Intent(this, EventActivity.class);
		eventIntent.putExtra(EVENT_ID, clickedEvent.getId());
		startActivity(eventIntent);
	}
	
	private void alertClicked(View clickedView, int position, long resourceId)
	{
		sayNotImplemented("alertClicked()");
	}
	
	private void takeAdminPortal()
	{
		startActivity(new Intent(this, AdminActivity.class));
	}
	
	private void takeInstructorPortal()
	{
		startActivity(new Intent(this, InstructorActivity.class));
	}
	
	private void takeRiderPortal()
	{
		startActivity(new Intent(this, RiderActivity.class));
	}
	
	private static void sayNotImplemented(String what)
	{
		Log.w(TAG, what + " has not been implemented yet.");
	}
	
	private ArrayList<Alert> mAlerts;
	private ArrayList<Event> mEvents;
	public static final String EVENT_ID = "edu.myhorseshow.EVENT_ID";
	private static final String TAG = "HomeActivity";
}
