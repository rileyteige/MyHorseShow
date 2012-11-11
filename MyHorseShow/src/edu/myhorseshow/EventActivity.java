package edu.myhorseshow;

import edu.myhorseshow.event.Event;
import edu.myhorseshow.showclass.ShowClass;
import edu.myhorseshow.showclass.ShowClassAdapter;
import edu.myhorseshow.division.Division;
import edu.myhorseshow.division.DivisionAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EventActivity extends Activity implements OnClickListener, OnItemClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		
		long eventId = getIntent().getLongExtra(HomeActivity.EVENT_ID, 0);
		setEvent(UserInfo.getEvent(eventId));
		
		setupViews();
		setupClickListeners();
	}

	public void onClick(View clickedView) {
		switch(clickedView.getId())
		{
		case R.id.event_class_list_button:
			makeViewVisible(R.id.event_division_list_view);
			break;
		case R.id.event_ride_times_button:
			makeViewVisible(R.id.event_ride_times_view);
			break;
		case R.id.event_barn_info_button:
			makeViewVisible(R.id.event_barn_info_view);
			break;
		case R.id.event_contact_info_button:
			makeViewVisible(R.id.event_contact_info_view);
			break;
		}
	}
	
	public void onItemClick(AdapterView<?> parent, View clickedView, int position, long rowViewResourceId)
	{
		switch(parent.getId())
		{
		case R.id.event_division_list_view:
			divisionItemClicked(clickedView, position, rowViewResourceId);
			break;
		case R.id.event_class_list_view:
			classItemClicked(clickedView, position, rowViewResourceId);
			break;
		}
	}
	
	private void setupViews()
	{
		TextView headerLabelTextView = (TextView) findViewById(R.id.event_header_label);		
		headerLabelTextView.setText(getEvent().getName());
		
		setupListAdapters();
	}
	
	private void setupListAdapters()
	{
		ListView classesListView = (ListView)findViewById(R.id.event_division_list_view);
		
		if (getEvent().getDivisions() != null)
		{
			classesListView.setAdapter(new DivisionAdapter(this, R.layout.row_view_division, getEvent().getDivisions()));
			classesListView.setOnItemClickListener(this);
		}
	}
	
	private void setupClickListeners()
	{
		((Button) findViewById(R.id.event_class_list_button)).setOnClickListener(this);
		((Button) findViewById(R.id.event_ride_times_button)).setOnClickListener(this);
		((Button) findViewById(R.id.event_barn_info_button)).setOnClickListener(this);
		((Button) findViewById(R.id.event_contact_info_button)).setOnClickListener(this);
	}
	
	private void divisionItemClicked(View clickedView, int position, long rowViewResourceId)
	{
		Division clickedDivision = getEvent().getDivisions()[position];
		if (clickedDivision != null)
			loadClassView(clickedDivision);
	}
	
	private void classItemClicked(View clickedView, int position, long rowViewResourceId)
	{
		ListView classListView = (ListView)findViewById(R.id.event_class_list_view);
		ShowClassAdapter classAdapter = (ShowClassAdapter)classListView.getAdapter();
		if (classAdapter == null)
			return;
		
		ShowClass clickedClass = classAdapter.getClasses().get(position);
		if (clickedClass != null)
		{
			Intent classIntent = new Intent(this, ClassActivity.class);
			classIntent.putExtra(CLASS_ID, clickedClass.getId());
			classIntent.putExtra(EVENT_ID, getEvent().getId());
			startActivity(classIntent);
		}
	}
	
	private void loadClassView(Division division)
	{
		ListView classListView = (ListView)findViewById(R.id.event_class_list_view);
		if (classListView == null)
			return;
		
		classListView.setAdapter(new ShowClassAdapter(this, R.layout.row_view_class, division.getClasses()));
		classListView.setOnItemClickListener(this);
		classListView.setVisibility(View.VISIBLE);
	}
	
	private void hideClassView()
	{
		ListView classListView = (ListView)findViewById(R.id.event_class_list_view);
		if (classListView != null)
			classListView.setVisibility(View.INVISIBLE);
	}
	
	private void makeViewVisible(int viewId)
	{
		hideClassView();
		View nextView = findViewById(viewId);
		if (nextView == null)
			return;
		
		View currentView = getCurrentView();
		if (currentView != null)
			currentView.setVisibility(View.INVISIBLE);
		
		nextView.setVisibility(View.VISIBLE);
		setCurrentView(nextView);
	}
	
	private View getCurrentView() { return mCurrentView; }
	private void setCurrentView(View view) { mCurrentView = view; }
	
	private Event getEvent() { return mEvent; }
	private void setEvent(Event event) { mEvent = event; }
	
	private View mCurrentView;
	private Event mEvent;
	
	public static final String CLASS_ID = "edu.myhorseshow.CLASS_ID";
	public static final String EVENT_ID = "edu.myhorseshow.EVENT_ID";
}
