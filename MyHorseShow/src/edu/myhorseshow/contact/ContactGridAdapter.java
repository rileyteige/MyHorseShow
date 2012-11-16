package edu.myhorseshow.contact;

import java.util.ArrayList;
import java.util.Arrays;

import edu.myhorseshow.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ContactGridAdapter extends BaseAdapter
{
	public ContactGridAdapter(Context context, Contact[] contacts)
	{
		this(context, new ArrayList<Contact>(Arrays.asList(contacts)));
	}
	
	public ContactGridAdapter(Context context, ArrayList<Contact> contacts)
	{
		this(context, R.layout.cell_view_contact, contacts);
	}
	
	public ContactGridAdapter(Context context, int cellViewResourceId, ArrayList<Contact> contacts)
	{
		setContext(context);
		setCellViewResourceId(cellViewResourceId);
		setContacts(contacts);
	}
	
	public int getCount()
	{
		return mContacts != null ? mContacts.size() : 0;
	}
	
	public Object getItem(int position) {
		return mContacts.get(position);
	}
	
	public long getItemId(int position)
	{
		return mContacts.get(position).getId();
	}
	
	public View getView(int position, View convertView, ViewGroup parent)
	{
		View cellView = convertView;
		if (cellView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			cellView = inflater.inflate(getCellViewResourceId(), null);
		}
		
		Contact contact = getContacts().get(position);
		if (contact == null)
			return null;
		
		TextView nameTextView = (TextView)cellView.findViewById(R.id.cell_view_contact_name_label);
		TextView phoneTextView = (TextView)cellView.findViewById(R.id.cell_view_contact_phone_label);
		TextView emailTextView = (TextView)cellView.findViewById(R.id.cell_view_contact_email_label);
		
		if (nameTextView != null)
			nameTextView.setText(contact.getName());
		
		if (phoneTextView != null)
			phoneTextView.setText(contact.getPhoneNumber());
		
		if (emailTextView != null)
			emailTextView.setText(contact.getEmail());
		
		return cellView;
	}
	
	public Context getContext() { return mContext; }
	protected void setContext(Context context) { mContext = context; }
	
	public ArrayList<Contact> getContacts() { return mContacts; }
	protected void setContacts(ArrayList<Contact> contacts) { mContacts = contacts; }
	
	protected int getCellViewResourceId() { return mCellViewResourceId; }
	protected void setCellViewResourceId(int id) { mCellViewResourceId = id; }
	
	private Context mContext;
	private ArrayList<Contact> mContacts;
	private int mCellViewResourceId;
}
