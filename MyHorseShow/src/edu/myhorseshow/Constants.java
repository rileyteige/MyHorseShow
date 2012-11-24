package edu.myhorseshow;

public final class Constants
{
	public static final String SERVER_IP_ADDRESS = "10.0.0.15";
	public static final String SERVER_DOMAIN = SERVER_IP_ADDRESS + "/other/index.php";
	public static final int INVALID_LOGIN_CODE = -1;
	public static final String TYPE_USER = "user";
	public static final String TYPE_START = "start";
	public static final String TYPE_STALL = "stall";
	public static final String TYPE_CLASS = "class";
	public static final String TYPE_DIVISION = "division";
	public static final String TYPE_BARN = "barn";
	public static final String TYPE_EVENT = "event";
	public static final int HTTP_CONNECTION_TIMEOUT = 3000; // milliseconds, waiting for connection
	public static final int HTTP_WAITING_TIMEOUT = 5000; // milliseconds, waiting for data
}