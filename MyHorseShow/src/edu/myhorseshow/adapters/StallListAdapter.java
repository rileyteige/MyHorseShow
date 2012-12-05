package edu.myhorseshow.adapters;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;
import edu.myhorseshow.models.Stall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StallListAdapter extends ArrayAdapter<Stall>
{
	public StallListAdapter(Context context, Stall[] stalls)
	{
		this(context, stalls != null ?
				new ArrayList<Stall>(Arrays.asList(stalls)) :
				new ArrayList<Stall>());
	}
	
	public StallListAdapter(Context context, ArrayList<Stall> stalls)
	{
		this(context, R.layout.row_view_named_object, stalls);
	}
	
	private StallListAdapter(Context context, int rowViewResourceId, ArrayList<Stall> stalls)
	{
		super(context, rowViewResourceId, stalls);
		
		setRowViewResourceId(rowViewResourceId);
		setStalls(stalls);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		if (rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(getRowViewResourceId(), null);
		}
		
		Stall stall = getStalls().get(position);
		if (stall == null)
			return null;
		
		TextView labelTextView = (TextView)rowView.findViewById(R.id.row_view_named_object_label);
		labelTextView.setText(stall.getName());
		
		return rowView;
	}
	
	public ArrayList<Stall> getStalls() { return mStalls; }
	protected void setStalls(ArrayList<Stall> stalls) { mStalls = stalls; }
	
	public int getRowViewResourceId() { return mRowViewResourceId; }
	public void setRowViewResourceId(int id) { mRowViewResourceId = id; }
	
	private int mRowViewResourceId;
	private ArrayList<Stall> mStalls;
}
