package edu.myhorseshow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class EventActivity extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		
		TextView headerLabelTextView = (TextView) findViewById(R.id.event_header_label);
		headerLabelTextView.setText("Event " + getIntent().getLongExtra(HomeActivity.EVENT_ID, 0));
	}
}
