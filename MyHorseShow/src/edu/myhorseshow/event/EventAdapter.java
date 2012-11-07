package edu.myhorseshow.event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import edu.myhorseshow.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class EventAdapter extends ArrayAdapter<Event>
{
	public EventAdapter(Context context, int rowViewResourceId, ArrayList<Event> events)
	{
		super(context, rowViewResourceId, events);
		mEvents = events;
		setRowViewResourceId(rowViewResourceId);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		if (rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(getRowViewResourceId(), null);
		}
		Event event = getEvents().get(position);
		TextView rowLabel = (TextView) rowView.findViewById(R.id.row_view_event_label);
		TextView dateLabel = (TextView) rowView.findViewById(R.id.row_view_event_date_label);
		rowLabel.setText(event.getName());
		dateLabel.setText(getDateString(event.getStartDate()) + " - " + getDateString(event.getEndDate()));
		return rowView;
	}
	
	protected String getDateString(String str)
	{
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		Date date = null;
		try {
			date = formatter.parse(str);
		} catch (ParseException e) {
			Log.w(TAG, "Bad date given.");
			e.printStackTrace();
		}
		return new SimpleDateFormat("MMM dd, yyyy").format(date);
	}
	
	protected int getRowViewResourceId()
	{
		return mRowViewResourceId;
	}
	
	protected void setRowViewResourceId(int value)
	{
		mRowViewResourceId = value;
	}

	protected ArrayList<Event> getEvents()
	{
		return mEvents;
	}
	
	protected void setEvents(ArrayList<Event> events)
	{
		mEvents = events;
	}
	
	private int mRowViewResourceId;
	private ArrayList<Event> mEvents;
	private String TAG = "EventAdapter";
}
