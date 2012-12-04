package edu.myhorseshow.adapters;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;
import edu.myhorseshow.models.ShowClass;
import edu.myhorseshow.models.ShowClassParticipator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class CheckableShowClassAdapter extends ItemListAdapter<ShowClassParticipator>
{
	public CheckableShowClassAdapter(Context context, ShowClassParticipator[] items)
	{
		this(context, new ArrayList<ShowClassParticipator>(Arrays.asList(items)));
	}
	
	public CheckableShowClassAdapter(Context context, ArrayList<ShowClassParticipator> items)
	{
		this(context, R.layout.row_view_checkable_class, items);
	}
	
	public CheckableShowClassAdapter(Context context, int rowViewResourceId, ArrayList<ShowClassParticipator> items)
	{
		super(context, rowViewResourceId, items);
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(getRowViewResourceId(), null);
		}
		
		final ShowClassParticipator item = getItems().get(position);
		TextView nameTextView = (TextView)rowView.findViewById(R.id.row_view_checkable_class_label);
		nameTextView.setText(item.getShowClass().getName());
		
		CheckBox ridingCheckBox = (CheckBox)rowView.findViewById(R.id.row_view_checkable_class_checkbox);
		ridingCheckBox.setChecked(item.getIsParticipating());
		ridingCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton button, boolean isChecked) {
				if (!item.getIsParticipating() && isChecked)
					item.setIsParticipating(isChecked);
			}
		
		});
		
		return rowView;
	}
}
