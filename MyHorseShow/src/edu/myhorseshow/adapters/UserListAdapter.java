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
	public UserListAdapter(Context context, User[] users)
	{
		this(context, users, true);
	}
	
	public UserListAdapter(Context context, User[] users, boolean showCurrentUser)
	{
		this(context, new ArrayList<User>(Arrays.asList(users)), showCurrentUser);
	}
	
	public UserListAdapter(Context context, ArrayList<User> users, boolean showCurrentUser)
	{
		this(context, R.layout.row_view_user, users, showCurrentUser);
	}
	
	private UserListAdapter(Context context, int rowViewResourceId, ArrayList<User> users, boolean showCurrentUser) {
		super(context, rowViewResourceId, users);
		setRowViewResourceId(rowViewResourceId);
		setRiders(users);
		setShowCurrentUser(showCurrentUser);
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
		rowLabel.setText((AppModel.getInstance().isCurrentUser(rider.getId()) ? "YOU: " : "") + rider.getFirstName() + " " + rider.getLastName());
		return rowView;
	}
	
	public ArrayList<User> getRiders() { return mRiders; }
	protected void setRiders(ArrayList<User> riders) { mRiders = riders; }
	
	public int getRowViewResourceId() { return mRowViewResourceId; }
	protected void setRowViewResourceId(int id) { mRowViewResourceId = id; }
	
	protected boolean getShowCurrentUser() { return mShowCurrentUser; }
	protected void setShowCurrentUser(boolean value) { mShowCurrentUser = value; }
	
	private int mRowViewResourceId;
	private ArrayList<User> mRiders;
	private boolean mShowCurrentUser;
}
