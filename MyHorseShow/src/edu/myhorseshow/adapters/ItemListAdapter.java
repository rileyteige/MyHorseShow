package edu.myhorseshow.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;

public class ItemListAdapter<T> extends ArrayAdapter<T>
{
	public ItemListAdapter(Context context, int rowViewResourceId, ArrayList<T> items)
	{
		super(context, rowViewResourceId, items);
		setRowViewResourceId(rowViewResourceId);
		setItems(items);
	}
	
	public ArrayList<T> getItems() { return mItems; }
	protected void setItems(ArrayList<T> items) { mItems = items; }
	
	public int getRowViewResourceId() { return mRowViewResourceId; }
	protected void setRowViewResourceId(int id) { mRowViewResourceId = id; }
	
	private ArrayList<T> mItems;
	private int mRowViewResourceId;
}
