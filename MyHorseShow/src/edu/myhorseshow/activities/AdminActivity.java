package edu.myhorseshow.activities;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;
import edu.myhorseshow.UserInfo;
import edu.myhorseshow.adapters.EventAdapter;
import edu.myhorseshow.models.Event;
import android.app.Activity;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import edu.myhorseshow.utility.AdminProxy;
import edu.myhorseshow.utility.Utility;

public class AdminActivity extends Activity implements OnClickListener, OnItemClickListener
{
	public static final int CREATE_EVENT = 0;
	public static final int ADD_USER_TO_EVENT = 1;
	
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
		case R.id.admin_create_event_button:
			showCreateEventForm();
			break;
		case R.id.admin_create_event_submit_button:
			createEvent();
			break;
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
		ListView EventsListView = (ListView) findViewById(R.id.admin_event_list_view);
		
		if (UserInfo.getCurrentUser().getEvents() != null)
			setEvents(new ArrayList<Event>(Arrays.asList(UserInfo.getCurrentUser().getEvents())));
		
		if (getEvents() != null)
		{
			EventsListView.setAdapter(new EventAdapter(this, R.layout.row_view_event, getEvents()));
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
		Button createEventButton = (Button)findViewById(R.id.admin_create_event_button);
		Button submitEventButton = (Button)findViewById(R.id.admin_create_event_submit_button);
		Button riders = (Button) findViewById(R.id.admin_rider_button);
		Button instructors = (Button) findViewById(R.id.admin_instructor_button);
		Button divisions = (Button) findViewById(R.id.admin_divisions_button);
		Button arenas = (Button) findViewById(R.id.admin_arenas_button);
		Button final_results = (Button) findViewById(R.id.admin_final_results_button);
		
		createEventButton.setOnClickListener(this);
		submitEventButton.setOnClickListener(this);
		riders.setOnClickListener(this);
		instructors.setOnClickListener(this);
		divisions.setOnClickListener(this);
		arenas.setOnClickListener(this);
		final_results.setOnClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> parent, View clickedView, int position, long rowViewResourceId)
	{
		sayNotImplemented("clicking on the list");
		Event clickedEvent = getEvents().get(position);
		setCurrentEvent(UserInfo.getEvent(clickedEvent.getId()));
		showEventInfoButtons();
	}
	
	private void addEvent(Event event)
	{
		UserInfo.getCurrentUser().addEvent(event);
		setupListAdapters();
	}
	
	private void createEvent()
	{
		EditText eventNameEditText = (EditText)findViewById(R.id.admin_create_event_event_name_edit_text);
		DatePicker eventStartDateDatePicker = (DatePicker)findViewById(R.id.admin_create_event_event_start_date_date_picker);
		DatePicker eventEndDateDatePicker = (DatePicker)findViewById(R.id.admin_create_event_event_end_date_date_picker);
		
		String eventName = eventNameEditText.getText().toString();
		String startDate = extractDatePickerDateString(eventStartDateDatePicker);
		String endDate = extractDatePickerDateString(eventEndDateDatePicker);
		
		Event createdEvent = new Event();
		createdEvent.setAdminId(UserInfo.getCurrentUser().getId());
		createdEvent.setName(eventName);
		createdEvent.setStartDate(startDate);
		createdEvent.setEndDate(endDate);
		
		Utility.showProgressDialog(this, 
				getString(R.string.admin_creating_event_caption), 
				getString(R.string.admin_creating_event_description));
		AdminProxy.createEvent(this, createdEvent);
	}
	
	public void signalProxyFinished(int what, Object returnValue)
	{
		switch (what)
		{
		case (CREATE_EVENT):
			if (returnValue != null)
				addEvent((Event)returnValue);
			break;
		case (ADD_USER_TO_EVENT):
			break;
		}
		
		Utility.hideDialog();
	}
	
	private String extractDatePickerDateString(DatePicker datePicker)
	{
		final char separator = '-';
		
		return new StringBuilder()
			.append(datePicker.getYear())
			.append(separator)
			.append(datePicker.getMonth() + 1) // Why is this off by one?
			.append(separator)
			.append(datePicker.getDayOfMonth())
			.toString();
	}
	
	private void showCreateEventForm()
	{
		hideEventInfoButtons();
		setViewVisible(R.id.admin_create_event_relative_layout, true);
	}
	
	private void hideCreateEventForm()
	{
		setViewVisible(R.id.admin_create_event_relative_layout, false);
	}
	
	private void showEventInfoButtons()
	{
		hideCreateEventForm();
		setViewVisible(R.id.admin_event_info_linear_layout, true);
	}
	
	private void hideEventInfoButtons()
	{
		setViewVisible(R.id.admin_event_info_linear_layout, false);
	}
	
	private void setViewVisible(int id, boolean visibilityFlag)
	{
		View view = findViewById(id);
		if (view != null)
			view.setVisibility(visibilityFlag ? View.VISIBLE : View.INVISIBLE);
	}
	
	private static void sayNotImplemented(String what)
	{
		Log.w(TAG, what + " has not been implemented yet.");
	}
	
	// prefer unused getter to not having a getter available
	@SuppressWarnings("unused")
	private Event getCurrentEvent() { return mCurrentEvent; }
	private void setCurrentEvent(Event event) { mCurrentEvent = event; }
	
	private ArrayList<Event> getEvents() { return mEvents; }
	private void setEvents(ArrayList<Event> events) { mEvents = events; }
	
	private ArrayList<Event> mEvents;
	private Event mCurrentEvent;
	private static final String TAG = "AdminActivity";
	
}

