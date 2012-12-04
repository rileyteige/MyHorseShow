package edu.myhorseshow.activities;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;
import edu.myhorseshow.adapters.CheckableShowClassAdapter;
import edu.myhorseshow.adapters.EventAdapter;
import edu.myhorseshow.adapters.UserListAdapter;
import edu.myhorseshow.events.Event;
import edu.myhorseshow.events.EventListener;
import edu.myhorseshow.models.Participant;
import edu.myhorseshow.models.Participation;
import edu.myhorseshow.models.ShowClass;
import edu.myhorseshow.models.ShowClassParticipator;
import edu.myhorseshow.models.ShowEvent;
import edu.myhorseshow.models.User;
import edu.myhorseshow.proxies.AdminProxy;
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
import edu.myhorseshow.utility.Utility;

public class AdminActivity extends AppActivity implements OnClickListener, OnItemClickListener, EventListener
{
	public static final int CREATE_EVENT = 0;
	public static final int ADD_USER_TO_EVENT = 1;
	public static final int ADD_USER_TO_CLASS = 2;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_admin);
		getModel().getCurrentUser().addListener(User.EventMeta.EVENT_COUNT_CHANGED, this);
		
		setupListAdapters();
		setupClickListeners();
	}

	@Override
	public void onDestroy()
	{
		getModel().getCurrentUser().removeListener(User.EventMeta.EVENT_COUNT_CHANGED, this);
		super.onDestroy();
	}
	
	public void onEvent(Event event)
	{
		String type = event.getType();
		if (type == ShowClassParticipator.EventMeta.PARTICIPATING_CHANGED) {
			ShowClassParticipator item = (ShowClassParticipator)event.getSource();
			if (item.getIsParticipating())
				AdminProxy.addUserToClass(this, getCurrentEvent().getId(), item.getShowClass().getId(), getSelectedRider().getId(), "Deja Vu");
		}
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
			riderButtonClicked();
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
	
	private void riderButtonClicked()
	{
		View view = findViewById(R.id.admin_rider_subview);
		view.setVisibility(view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
	}
	
	private void takearenas()
	{
		sayNotImplemented("arenas()");
	}
	
	private void setupListAdapters()
	{
		ListView EventsListView = (ListView) findViewById(R.id.admin_event_list_view);
		
		if (getModel().getCurrentUser().getEvents() != null)
			setEvents(new ArrayList<ShowEvent>(Arrays.asList(getModel().getCurrentUser().getEvents())));			
		
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
		switch(parent.getId())
		{
		case (R.id.admin_event_list_view):
			eventListItemClicked(position);
			break;
		case (R.id.admin_event_participants_list_view):
			participantItemClicked(position);
			break;
		case (R.id.admin_event_participant_classes_list_view):
			classItemClicked(position);
			break;
		}
	}
	
	private void eventListItemClicked(int position)
	{
		ShowEvent clickedEvent = getEvents().get(position);
		setCurrentEvent(getModel().getEvent(clickedEvent.getId()));
		setupEventListAdapters();
		showEventInfoButtons();
	}
	
	private void participantItemClicked(int position)
	{
		Participant clickedParticipant = getCurrentEvent().getParticipants()[position];
		setSelectedRider(clickedParticipant);
		setupEventListAdapters();
	}
	
	private void classItemClicked(int position)
	{
		ListView lv = (ListView)findViewById(R.id.admin_event_participant_classes_list_view);
		CheckableShowClassAdapter adapter = (CheckableShowClassAdapter)lv.getAdapter();
		ShowClassParticipator item = adapter.getItems().get(position);
		if (item.getIsParticipating())
			Log.d(TAG, "true!");
	}
	
	private void setupEventListAdapters()
	{
		if (getCurrentEvent() == null)
			return;
		
		Participant[] participants = getCurrentEvent().getParticipants();
		ListView participantsListView = (ListView)findViewById(R.id.admin_event_participants_list_view);
		participantsListView.setAdapter(new UserListAdapter(this, getParticipantsWithoutCurrentUser(participants), false));
		participantsListView.setOnItemClickListener(this);
		
		ListView classesListView = (ListView)findViewById(R.id.admin_event_participant_classes_list_view);
		
		ArrayList<ShowClass> classes = getModel().getEventClasses(getCurrentEvent().getId());
		if (classes == null)
			classes = new ArrayList<ShowClass>();
		
		ArrayList<ShowClassParticipator> checkableClasses = new ArrayList<ShowClassParticipator>();
		for (ShowClass showClass: classes) {
			boolean riding = false;
			if (getSelectedRider() != null && showClass.getParticipations() != null) {
				for (Participation p: showClass.getParticipations()) {
					if (p.getRider().getId() == getSelectedRider().getId())
						riding = true;
				}
			}
			
			ShowClassParticipator item = new ShowClassParticipator(showClass, riding);
			item.addListener(ShowClassParticipator.EventMeta.PARTICIPATING_CHANGED, this);
			
			checkableClasses.add(item);
		}
		
		classesListView.setAdapter(new CheckableShowClassAdapter(this, checkableClasses));
		classesListView.setOnItemClickListener(this);
	}
	
	private ArrayList<User> getParticipantsWithoutCurrentUser(User[] users)
	{
		if (users == null)
			return new ArrayList<User>();
		
		ArrayList<User> newUsers = new ArrayList<User>();
		for (User user: users) {
			if (!getModel().isCurrentUser(user.getId()))
				newUsers.add(user);
		}
		return newUsers;
	}
	
	private void addEvent(ShowEvent event)
	{
		getModel().getCurrentUser().addEvent(event);
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
		
		ShowEvent createdEvent = new ShowEvent();
		createdEvent.setAdminId(getModel().getCurrentUser().getId());
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
				addEvent((ShowEvent)returnValue);
			break;
		case (ADD_USER_TO_EVENT):
			break;
		case (ADD_USER_TO_CLASS):
			if (returnValue != null)
				addLocalClassToUser((ShowClass)returnValue);
				setupEventListAdapters();
			break;
		}
		
		Utility.hideDialog();
	}
	
	private void addLocalClassToUser(ShowClass inClass)
	{
		ArrayList<ShowClass> classes = getModel().getEventClasses(getCurrentEvent().getId());
		ShowClass showClass = null;
		for (ShowClass c: classes) {
			if (c.getId() == inClass.getId()) {
				showClass = c;
				break;
			}
		}
		
		if (showClass == null)
			return;
		
		Participant participant = getSelectedRider();
		participant.addClass(showClass);
		Participation participation = new Participation();
		participation.setUser(participant);
		participation.setHorse("Deja vu");
		showClass.addParticipation(participation);
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
		setViewVisible(R.id.admin_event_info_scroll_view, true);
	}
	
	private void hideEventInfoButtons()
	{
		setViewVisible(R.id.admin_event_info_scroll_view, false);
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
	
	private ShowEvent getCurrentEvent() { return mCurrentEvent; }
	private void setCurrentEvent(ShowEvent event) { mCurrentEvent = event; }
	
	private Participant getSelectedRider() { return mSelectedRider; }
	private void setSelectedRider(Participant rider) { mSelectedRider = rider; }
	
	private ArrayList<ShowEvent> getEvents() { return mEvents; }
	private void setEvents(ArrayList<ShowEvent> events) { mEvents = events; }
	
	private ArrayList<ShowEvent> mEvents;
	private ShowEvent mCurrentEvent;
	private Participant mSelectedRider;
	private static final String TAG = "AdminActivity";
	
}

