package edu.myhorseshow.models;

import edu.myhorseshow.NamedObject;

public class Contact implements NamedObject
{
	/** Returns the full name of a contact. */
	public String getName()
	{
		return new StringBuilder(getFirstName())
						.append(' ')
						.append(getLastName())
						.toString();
	}
	
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getFirstName() { return firstname; }
	public void setFirstName(String name) { firstname = name; }
	
	public String getLastName() { return lastname; }
	public void setLastName(String name) { lastname = name; }
	
	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }
	
	public String getPhoneNumber() { return phone; }
	public void setPhoneNumber(String phone) { this.phone = phone; }
	
	public Occupation getOccupation() { return occupation; }
	public void setOccupation(Occupation occupation) { this.occupation = occupation; }
	
	private int id;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private Occupation occupation;
}
