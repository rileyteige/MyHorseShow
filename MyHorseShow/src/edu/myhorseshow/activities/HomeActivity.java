package edu.myhorseshow.activities;

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

import edu.myhorseshow.R;
import edu.myhorseshow.UserInfo;
import edu.myhorseshow.adapters.AlertAdapter;
import edu.myhorseshow.adapters.EventAdapter;
import edu.myhorseshow.models.Alert;
import edu.myhorseshow.models.Event;
import edu.myhorseshow.models.User;

public class HomeActivity extends Activity implements OnItemClickListener, OnClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		if (UserInfo.getCurrentUser() == null)
			throw new NullPointerException("Current user not set");
		
		welcomeUser(UserInfo.getCurrentUser());
		setupClickListeners();
		setupListAdapters();
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
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
	
	public void onItemClick(AdapterView<?> parent, View clickedView, int position, long rowViewResourceId)
	{
		switch(parent.getId())
		{
		case R.id.home_upcoming_events_listview:
			upcomingEventClicked(clickedView, position, rowViewResourceId);
			break;
		case R.id.home_alerts_listview:
			alertClicked(clickedView, position, rowViewResourceId);
			break;
		}
	}
	
	private void welcomeUser(User user)
	{
		TextView headerTextView = (TextView) findViewById(R.id.home_header_text_view);
		headerTextView.setText(String.format(getString(R.string.welcome_header), user.getFirstName() + " " + user.getLastName()));
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
		
		if (UserInfo.getCurrentUser().getEvents() != null)
		{
			upcomingEventsListView.setAdapter(new EventAdapter(this, UserInfo.getCurrentUser().getEvents()));
			upcomingEventsListView.setOnItemClickListener(this);
			upcomingEventsListView.setEnabled(true);
		}
		else
		{
			upcomingEventsListView.setEnabled(false);
		}
		
		alertsListView.setAdapter(new AlertAdapter(this, R.layout.row_view_alert, mAlerts));
		alertsListView.setOnItemClickListener(this);
	}
	
	private void upcomingEventClicked(View clickedView, int position, long resourceId)
	{
		Event clickedEvent = UserInfo.getCurrentUser().getEvents()[position];
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
	public static final String EVENT_ID = "edu.myhorseshow.EVENT_ID";
	private static final String TAG = "HomeActivity";
}