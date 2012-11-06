package edu.myhorseshow.event;

import java.util.ArrayList;

import edu.myhorseshow.R;

import android.content.Context;
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
		TextView rowLabel = (TextView) rowView.findViewById(R.id.row_view_event_label);
		rowLabel.setText("(EVENT)" + " " + getEvents().get(position).getId());
		return rowView;
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
}
