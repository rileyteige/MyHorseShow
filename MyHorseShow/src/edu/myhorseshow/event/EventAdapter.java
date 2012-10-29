package edu.myhorseshow.event;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class EventAdapter extends ArrayAdapter<Event>
{
	public EventAdapter(Context context, int rowViewResourceId, ArrayList<Event> events)
	{
		super(context, rowViewResourceId, events);
		mEvents = events;
		setRowViewResourceId(rowViewResourceId);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		if (rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater) getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(getRowViewResourceId(), null);
		}
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
