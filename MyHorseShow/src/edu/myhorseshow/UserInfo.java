package edu.myhorseshow;

import edu.myhorseshow.user.User;

public final class UserInfo
{
	private static User currentUser;
	public static void setCurrentUser(User user)
	{
		currentUser = user;
	}
	public static User getCurrentUser()
	{
		return currentUser;
	}
}