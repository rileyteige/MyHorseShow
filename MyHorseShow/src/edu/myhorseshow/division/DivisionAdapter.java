package edu.myhorseshow.division;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DivisionAdapter extends ArrayAdapter<Division>
{
	public DivisionAdapter(Context context, int rowViewResourceId, Division[] divisions)
	{
		this(context, rowViewResourceId, new ArrayList<Division>(Arrays.asList(divisions)));
	}
	
	public DivisionAdapter(Context context, int rowViewResourceId, ArrayList<Division> divisions)
	{
		super(context, rowViewResourceId, divisions);
		setRowViewResourceId(rowViewResourceId);
		setDivisions(divisions);
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
		
		Division division = getDivisions().get(position);
		TextView rowLabel = (TextView) rowView.findViewById(R.id.row_view_division_label);
		rowLabel.setText(division.getName());
		return rowView;
	}
	
	protected int getRowViewResourceId() { return mRowViewResourceId; }
	protected void setRowViewResourceId(int id) { mRowViewResourceId = id; }
	
	protected ArrayList<Division> getDivisions() { return mDivisions; }
	protected void setDivisions(ArrayList<Division> divisions) { mDivisions = divisions; }
	
	private int mRowViewResourceId;
	private ArrayList<Division> mDivisions;

}
