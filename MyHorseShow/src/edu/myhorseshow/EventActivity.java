package edu.myhorseshow;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.event.Event;
import edu.myhorseshow.showclass.ShowClass;
import edu.myhorseshow.showclass.ShowClassAdapter;
import edu.myhorseshow.division.Division;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class EventActivity extends Activity implements OnClickListener
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
			makeViewVisible(R.id.event_class_list_view);
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
	
	private void setupViews()
	{
		TextView headerLabelTextView = (TextView) findViewById(R.id.event_header_label);		
		headerLabelTextView.setText(getEvent().getName());
		
		setupListAdapters();
	}
	
	private void setupListAdapters()
	{
		ListView classesListView = (ListView)findViewById(R.id.event_class_list_view);
		
		if (getEvent().getDivisions()[0].getClasses() != null)
		{
			ArrayList<ShowClass> classes = new ArrayList<ShowClass>(Arrays.asList(getEvent().getDivisions()[0].getClasses()));
			classesListView.setAdapter(new ShowClassAdapter(this, R.layout.row_view_class, classes));
		}
	}
	
	private void setupClickListeners()
	{
		((Button) findViewById(R.id.event_class_list_button)).setOnClickListener(this);
		((Button) findViewById(R.id.event_ride_times_button)).setOnClickListener(this);
		((Button) findViewById(R.id.event_barn_info_button)).setOnClickListener(this);
		((Button) findViewById(R.id.event_contact_info_button)).setOnClickListener(this);
	}
	
	private void makeViewVisible(int viewId)
	{
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
}
