package edu.myhorseshow.showclass;

import edu.myhorseshow.user.User;

public class Participation
{
	public long getId() { return id; }
	public User getRider() { return rider; }
	public String getHorse() { return horse; }
	public int getRank() { return rank; }
	
	public void setId(long id) { this.id = id; }
	public void setUser(User rider) { this.rider = rider; }
	public void setHorse(String horse) { this.horse = horse; }
	public void setRank(int rank) { this.rank = rank; }
	
	private long id;
	private User rider;
	private String horse;
	private int rank;
}
