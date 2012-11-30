package edu.myhorseshow.models;

import edu.myhorseshow.events.EventDispatcher;


public class Participation extends EventDispatcher
{
	public static class EventMeta
	{
		public static final String RANK_CHANGED = getFullName("RANK_CHANGED");
		public static final String HORSE_CHANGED = getFullName("HORSE_CHANGED");
		private static String getFullName(String str) { return EventMeta.class.getName() + str; }
	}
	
	public long getId() { return id; }
	public User getRider() { return rider; }
	public String getHorse() { return horse; }
	public int getRank() { return rank; }
	
	public void setId(long id) { this.id = id; }
	public void setUser(User rider) { this.rider = rider; }
	
	public void setHorse(String horse)
	{
		this.horse = horse;
		notifyChange(EventMeta.HORSE_CHANGED);
	}
	
	public void setRank(int rank)
	{
		this.rank = rank;
		notifyChange(EventMeta.RANK_CHANGED);
	}
	
	private long id;
	private User rider;
	private String horse;
	private int rank;
}
