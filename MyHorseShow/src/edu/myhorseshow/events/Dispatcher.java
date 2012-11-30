package edu.myhorseshow.events;

public interface Dispatcher
{
	public void addListener(String type, EventListener listener);
	public void removeListener(String type, EventListener listener);
	public boolean hasListener(String type, EventListener listener);
	public void dispatchEvent(Event event);
}
