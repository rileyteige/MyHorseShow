package edu.myhorseshow.activities;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;
import edu.myhorseshow.adapters.CheckableShowClassAdapter;
import edu.myhorseshow.adapters.EventAdapter;
import edu.myhorseshow.adapters.NameListAdapter;
import edu.myhorseshow.adapters.UserListAdapter;
import edu.myhorseshow.events.Event;
import edu.myhorseshow.events.EventListener;
import edu.myhorseshow.models.Division;
import edu.myhorseshow.models.Participant;
import edu.myhorseshow.models.Participation;
import edu.myhorseshow.models.ShowClass;
import edu.myhorseshow.models.ShowClassParticipator;
import edu.myhorseshow.models.ShowEvent;
import edu.myhorseshow.models.User;
import edu.myhorseshow.proxies.AdminProxy;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
	public static final int ADD_DIVISION_TO_EVENT = 3;
	public static final int ADD_CLASS_TO_DIVISION = 4;
	
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
		ShowEvent[] events = getModel().getCurrentUser().getShowEvents();
		for (ShowEvent e: events) {
			e.removeListener(this);
			if (e.getDivisions() != null) {
				for (Division d: e.getDivisions())
					d.removeListener(this);
			}
		}
		super.onDestroy();
	}
	
	public void onEvent(Event event)
	{
		String type = event.getType();
		if (type == ShowClassParticipator.EventMeta.PARTICIPATING_CHANGED) {
			final ShowClassParticipator item = (ShowClassParticipator)event.getSource();
			if (item.getIsParticipating()) {
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				final AdminActivity activity = this;
				final View dialogView = getLayoutInflater().inflate(R.layout.dialog_rider_enter_horse_name, null);
				
				builder.setView(dialogView)
						.setTitle(R.string.admin_prompt_horse_name)
						.setPositiveButton(R.string.ok_caption, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								
								EditText nameEditText = (EditText)dialogView.findViewById(R.id.dialog_rider_enter_horse_name_edit_text);
								String name = nameEditText.getText().toString();
								
								AdminProxy.addUserToClass(activity,
										getCurrentEvent().getId(),
										item.getShowClass().getId(), getSelectedRider().getId(),
										name);
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
		else if (type == ShowEvent.EventMeta.PARTICIPANTS_CHANGED) {
			Log.d(TAG, "Participants changed!");
			setupEventListAdapters();
		} else if (type == ShowEvent.EventMeta.DIVISIONS_CHANGED) {
			Log.d(TAG, "Divisions changed!");
			setupEventListAdapters();
		} else if (type == Division.EventMeta.CLASSES_CHANGED) {
			Log.d(TAG, "Classes changed!");
			setupEventListAdapters();
			setupClassListAdapters();
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
			createShowEvent();
			break;
		case R.id.admin_divisions_button:
			divisionsButtonClicked();
			break;
		case R.id.admin_rider_button:
			riderButtonClicked();
			break;
		case R.id.admin_event_add_participant_button:
			addParticipant(((EditText)findViewById(R.id.admin_event_add_participant_email_edit_text)).getText().toString());
			break;
		case R.id.admin_divisions_add_division_button:
			addDivision();
			break;
		case R.id.admin_divisions_add_class_button:
			if (getSelectedDivision() != null)
				addClass();
			break;
		}
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
		case (R.id.admin_divisions_list_view):
			divisionItemClicked(position);
			break;
		}
	}
	
	public void signalProxyFinished(int what, Object returnValue)
	{
		switch (what)
		{
		case (CREATE_EVENT):
			if (returnValue != null)
				addShowEvent((ShowEvent)returnValue);
			break;
		case (ADD_USER_TO_EVENT):
			if (returnValue != null)
				addLocalUserToEvent((Participant)returnValue);
			break;
		case (ADD_USER_TO_CLASS):
			if (returnValue != null) {
				addLocalClassToUser((ShowClass)returnValue);
				setupEventListAdapters();
			}
			break;
		case (ADD_DIVISION_TO_EVENT):
			if (returnValue != null) {
				getCurrentEvent().addDivision((Division)returnValue);
			}
			break;
		case (ADD_CLASS_TO_DIVISION):
			if (returnValue != null) {
				getSelectedDivision().addClass((ShowClass)returnValue);
			}
			break;
		}
		
		Utility.hideDialog();
	}
	
	private void divisionsButtonClicked()
	{
		toggleExpandedView(findViewById(R.id.admin_divisions_subview));
	}
	
	private void riderButtonClicked()
	{
		toggleExpandedView(findViewById(R.id.admin_rider_subview));
	}

	private void setupListAdapters()
	{
		ListView EventsListView = (ListView) findViewById(R.id.admin_event_list_view);
		
		if (getModel().getCurrentUser().getShowEvents() != null)
			setEvents(new ArrayList<ShowEvent>(Arrays.asList(getModel().getCurrentUser().getShowEvents())));			
		
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
		Button divisions = (Button) findViewById(R.id.admin_divisions_button);
		Button addRiderButton = (Button)findViewById(R.id.admin_event_add_participant_button);
		Button addDivisionButton = (Button)findViewById(R.id.admin_divisions_add_division_button);
		Button addClassButton = (Button)findViewById(R.id.admin_divisions_add_class_button);
		
		createEventButton.setOnClickListener(this);
		submitEventButton.setOnClickListener(this);
		riders.setOnClickListener(this);
		divisions.setOnClickListener(this);
		addRiderButton.setOnClickListener(this);
		addDivisionButton.setOnClickListener(this);
		addClassButton.setOnClickListener(this);
	}
	
	private void addClass()
	{
		final long divisionId = getSelectedDivision().getId();
		final AdminActivity activity = this;
		final View dialogView = getLayoutInflater().inflate(R.layout.dialog_rider_enter_horse_name, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialogView)
				.setTitle(R.string.admin_prompt_class_name)
				.setPositiveButton(R.string.ok_caption, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						EditText nameEditText = (EditText)dialogView.findViewById(R.id.dialog_rider_enter_horse_name_edit_text);
						String name = nameEditText.getText().toString();
						AdminProxy.addClassToDivision(activity, divisionId, name);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void addDivision()
	{
		final long eventId = getCurrentEvent().getId();
		final AdminActivity activity = this;
		final View dialogView = getLayoutInflater().inflate(R.layout.dialog_rider_enter_horse_name, null);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialogView)
				.setTitle(R.string.admin_prompt_division_name)
				.setPositiveButton(R.string.ok_caption, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						EditText nameEditText = (EditText)dialogView.findViewById(R.id.dialog_rider_enter_horse_name_edit_text);
						String name = nameEditText.getText().toString();
						AdminProxy.addDivisionToShowEvent(activity, eventId, name);
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	private void addParticipant(String email)
	{
		AdminProxy.addUserToShowEvent(this, email, getCurrentEvent().getId());
	}
	
	private void eventListItemClicked(int position)
	{
		ShowEvent clickedEvent = getEvents().get(position);
		setCurrentEvent(getModel().getEvent(clickedEvent.getId()));
		getCurrentEvent().addListener(ShowEvent.EventMeta.PARTICIPANTS_CHANGED, this);
		getCurrentEvent().addListener(ShowEvent.EventMeta.DIVISIONS_CHANGED, this);
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
	
	private void divisionItemClicked(int position)
	{
		if (getSelectedDivision() != null)
			getSelectedDivision().removeListener(Division.EventMeta.CLASSES_CHANGED, this);
		
		Division clickedDivision = getCurrentEvent().getDivisions()[position];
		setSelectedDivision(clickedDivision);
		getSelectedDivision().addListener(Division.EventMeta.CLASSES_CHANGED, this);
		setupClassListAdapters();
	}
	
	private void setupClassListAdapters()
	{
		if (getSelectedDivision() == null)
			return;
		
		ShowClass[] classes = getSelectedDivision().getClasses();
		ListView classesListView = (ListView)findViewById(R.id.admin_divisions_classes_list_view);
		classesListView.setAdapter(new NameListAdapter(this, classes));
		classesListView.setOnItemClickListener(this);
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
		
		Division[] divisions = getCurrentEvent().getDivisions();
		ListView divisionsListView = (ListView)findViewById(R.id.admin_divisions_list_view);
		divisionsListView.setAdapter(new NameListAdapter(this, divisions));
		divisionsListView.setOnItemClickListener(this);
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
	
	private void addShowEvent(ShowEvent event)
	{
		getModel().getCurrentUser().addShowEvent(event);
		setupListAdapters();
	}
	
	private void createShowEvent()
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
		AdminProxy.createShowEvent(this, createdEvent);
	}
	
	private void addLocalUserToEvent(Participant user)
	{
		Log.d(TAG, "Adding participant...");
		getCurrentEvent().addParticipant(user);
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
	
	private void toggleExpandedView(View view)
	{
		view.setVisibility(view.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
	}
	
	private void setViewVisible(int id, boolean visibilityFlag)
	{
		View view = findViewById(id);
		if (view != null)
			view.setVisibility(visibilityFlag ? View.VISIBLE : View.INVISIBLE);
	}
	
	private ShowEvent getCurrentEvent() { return mCurrentEvent; }
	private void setCurrentEvent(ShowEvent event) { mCurrentEvent = event; }
	
	private Participant getSelectedRider() { return mSelectedRider; }
	private void setSelectedRider(Participant rider) { mSelectedRider = rider; }
	
	private Division getSelectedDivision() { return mSelectedDivision; }
	private void setSelectedDivision(Division div) { mSelectedDivision = div; }
	
	private ArrayList<ShowEvent> getEvents() { return mEvents; }
	private void setEvents(ArrayList<ShowEvent> events) { mEvents = events; }
	
	private ArrayList<ShowEvent> mEvents;
	private ShowEvent mCurrentEvent;
	private Participant mSelectedRider;
	private Division mSelectedDivision;
	private static final String TAG = "AdminActivity";
	
}

