package edu.myhorseshow.showclass;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ShowClassAdapter extends ArrayAdapter<ShowClass>
{
	
	public ShowClassAdapter(Context context, ShowClass[] classes)
	{
		this(context, new ArrayList<ShowClass>(Arrays.asList(classes)));
	}
	
	public ShowClassAdapter(Context context, ArrayList<ShowClass> classes)
	{
		this(context, R.layout.row_view_class, classes);
	}
	
	private ShowClassAdapter(Context context, int rowViewResourceId, ArrayList<ShowClass> classes)
	{
		super(context, rowViewResourceId, classes);
		setClasses(classes);
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
		
		ShowClass showClass = getClasses().get(position);
		TextView rowLabel = (TextView) rowView.findViewById(R.id.row_view_class_label);
		TextView rowTime = (TextView) rowView.findViewById(R.id.row_view_class_time);
		
		rowLabel.setText(showClass.getName());
		rowTime.setText(showClass.getStartTime() != null ? showClass.getStartTime() : "Ride times not posted yet.");
		return rowView;
	}
	
	public ArrayList<ShowClass> getClasses() { return mClasses; }
	protected void setClasses(ArrayList<ShowClass> classes) { mClasses = classes; }
	
	public int getRowViewResourceId() { return mRowViewResourceId; }
	protected void setRowViewResourceId(int rowViewResourceId) { mRowViewResourceId = rowViewResourceId; }
	
	private ArrayList<ShowClass> mClasses;
	private int mRowViewResourceId;
}
