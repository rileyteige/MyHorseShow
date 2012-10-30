package edu.myhorseshow.alert;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class AlertAdapter extends ArrayAdapter<Alert>
{

	public AlertAdapter(Context context, int rowViewResourceId, ArrayList<Alert> alerts) {
		super(context, rowViewResourceId, alerts);
		mAlerts = alerts;
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

	protected ArrayList<Alert> getAlerts()
	{
		return mAlerts;
	}
	
	protected void setAlerts(ArrayList<Alert> alerts)
	{
		mAlerts = alerts;
	}
	
	private int mRowViewResourceId;
	private ArrayList<Alert> mAlerts;
}