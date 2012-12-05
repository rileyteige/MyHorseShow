package edu.myhorseshow.adapters;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;
import edu.myhorseshow.models.NamedObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NameListAdapter extends ArrayAdapter<NamedObject>
{
	public NameListAdapter(Context context, NamedObject[] objects)
	{
		this(context, objects != null ?
				new ArrayList<NamedObject>(Arrays.asList(objects)) :
				new ArrayList<NamedObject>());
	}
	
	public NameListAdapter(Context context, ArrayList<NamedObject> objects)
	{
		this(context, R.layout.row_view_named_object, objects);
	}
	
	private NameListAdapter(Context context, int rowViewResourceId, ArrayList<NamedObject> objects)
	{
		super(context, rowViewResourceId, objects);
		
		setObjects(objects);
		setRowViewResourceId(rowViewResourceId);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		if (rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(getRowViewResourceId(), null);
		}
		
		NamedObject obj = getObjects().get(position);
		if (obj == null)
			return null;
		
		TextView labelTextView = (TextView)rowView.findViewById(R.id.row_view_named_object_label);
		labelTextView.setText(obj.getName());
		
		return rowView;
	}
	
	public ArrayList<NamedObject> getObjects() { return mObjects; }
	protected void setObjects(ArrayList<NamedObject> objects) { mObjects = objects; }
	
	protected int getRowViewResourceId() { return mRowViewResourceId; }
	protected void setRowViewResourceId(int id) { mRowViewResourceId = id; }
	
	private ArrayList<NamedObject> mObjects;
	private int mRowViewResourceId;
}
