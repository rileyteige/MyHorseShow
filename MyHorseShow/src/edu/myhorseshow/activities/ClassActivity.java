package edu.myhorseshow.activities;

import edu.myhorseshow.R;
import edu.myhorseshow.AppModel;
import edu.myhorseshow.adapters.ParticipationListAdapter;
import edu.myhorseshow.models.ShowClass;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ClassActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class);
		
		long eventId = getIntent().getLongExtra(EventActivity.EVENT_ID, 0);
		long classId = getIntent().getLongExtra(EventActivity.CLASS_ID, 0);
		
		setShowClass(AppModel.getClass(eventId, classId));
		if (getShowClass() == null)
			throw new NullPointerException("ShowClass");
		
		setupHeader();
		setupListAdapters();
	}
	
	private void setupHeader()
	{
		TextView headerTextView = (TextView)findViewById(R.id.class_header_label);
		if (headerTextView != null)
			headerTextView.setText(getShowClass().getName());
	}
	
	private void setupListAdapters()
	{
		ListView ridersListView = (ListView)findViewById(R.id.class_riders_list_view);
		if (ridersListView == null)
			return;
		
		ridersListView.setAdapter(new ParticipationListAdapter(this, getShowClass().getParticipations()));
	}
	
	public ShowClass getShowClass() { return mShowClass; }
	public void setShowClass(ShowClass showClass) { mShowClass = showClass; }
	
	private ShowClass mShowClass;
}
