package edu.myhorseshow.models;

import android.util.Log;
import edu.myhorseshow.events.EventDispatcher;

public class ShowClassParticipator extends EventDispatcher
{
	public static class EventMeta
	{
		public static final String PARTICIPATING_CHANGED = getFullName("PARTICIPATING_CHANGED");
		public static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public ShowClassParticipator(ShowClass showClass, boolean isParticipating)
	{
		mClass = showClass;
		mIsParticipating = isParticipating;
	}
	
	public ShowClass getShowClass() { return mClass; }
	public boolean getIsParticipating() { return mIsParticipating; }
	
	public void setShowClass(ShowClass showClass) { mClass = showClass; }
	public void setIsParticipating(boolean value)
	{
		mIsParticipating = value;
		notifyChange(EventMeta.PARTICIPATING_CHANGED);
	}
	
	private ShowClass mClass;
	private boolean mIsParticipating;
}
