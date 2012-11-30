package edu.myhorseshow.adapters;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;
import edu.myhorseshow.AppModel;
import edu.myhorseshow.models.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class UserListAdapter extends ArrayAdapter<User>
{
	public UserListAdapter(Context context, int rowViewResourceId, User[] riders)
	{
		this(context, rowViewResourceId, new ArrayList<User>(Arrays.asList(riders)));
	}
	
	public UserListAdapter(Context context, int rowViewResourceId, ArrayList<User> riders) {
		super(context, rowViewResourceId, riders);
		setRowViewResourceId(rowViewResourceId);
		setRiders(riders);
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
		
		User rider = getRiders().get(position);
		TextView rowLabel = (TextView) rowView.findViewById(R.id.row_view_user_label);
		rowLabel.setText((AppModel.isCurrentUser(rider.getId()) ? "YOU: " : "") + rider.getFirstName() + " " + rider.getLastName());
		return rowView;
	}
	
	public ArrayList<User> getRiders() { return mRiders; }
	protected void setRiders(ArrayList<User> riders) { mRiders = riders; }
	
	public int getRowViewResourceId() { return mRowViewResourceId; }
	protected void setRowViewResourceId(int id) { mRowViewResourceId = id; }
	
	private int mRowViewResourceId;
	private ArrayList<User> mRiders;
}
