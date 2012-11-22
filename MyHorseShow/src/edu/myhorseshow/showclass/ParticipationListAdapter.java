package edu.myhorseshow.showclass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import edu.myhorseshow.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ParticipationListAdapter extends ArrayAdapter<Participation>
{
	public ParticipationListAdapter(Context context, Participation[] participations)
	{
		this(context, participations != null ?
				new ArrayList<Participation>(Arrays.asList(participations)) :
				new ArrayList<Participation>());
	}
	
	public ParticipationListAdapter(Context context, ArrayList<Participation> participations)
	{
		this(context, R.layout.row_view_participation, participations != null ?
				participations :
				new ArrayList<Participation>());
	}
	
	public ParticipationListAdapter(Context context, int rowViewResourceId, ArrayList<Participation> participations)
	{
		super(context, rowViewResourceId, participations);
		setRowViewResourceId(rowViewResourceId);
		if (participations != null)
			Collections.sort(participations, new ParticipationComparator());
		setParticipations(participations);
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		View rowView = convertView;
		if (rowView == null)
		{
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(getRowViewResourceId(), null);
		}
		
		Participation participation = getParticipations().get(position);
		if (participation == null)
			return null;
		
		int rank = participation.getRank();
		
		String riderNameLabel = new StringBuilder()
			.append(rank > 0 ? rank + " " : "")
			.append(participation.getRider().getName())
			.toString();
		
		TextView riderNameTextView = (TextView)rowView.findViewById(R.id.row_view_participation_rider_name_text_view);
		if (riderNameTextView != null)
			riderNameTextView.setText(riderNameLabel);
		
		return rowView;
	}
	
	private class ParticipationComparator implements Comparator<Participation>
	{
		public int compare(Participation left, Participation right) {
			return left.getRank() - right.getRank();
		}
	}
	
	public ArrayList<Participation> getParticipations() { return mParticipations; }
	protected void setParticipations(ArrayList<Participation> p) { mParticipations = p; }
	
	protected int getRowViewResourceId() { return mRowViewResourceId; }
	protected void setRowViewResourceId(int id) { this.mRowViewResourceId = id; }
	
	private ArrayList<Participation> mParticipations;
	private int mRowViewResourceId;
}
