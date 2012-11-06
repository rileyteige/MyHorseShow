package edu.myhorseshow;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class EventActivity extends Activity implements OnClickListener
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		
		TextView headerLabelTextView = (TextView) findViewById(R.id.event_header_label);
		headerLabelTextView.setText("Event " + getIntent().getLongExtra(HomeActivity.EVENT_ID, 0));
		
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
	
	private View getCurrentView()
	{
		return mCurrentView;
	}
	
	private void setCurrentView(View view)
	{
		mCurrentView = view;
	}
	
	private View mCurrentView;
}
