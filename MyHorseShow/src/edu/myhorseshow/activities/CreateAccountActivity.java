package edu.myhorseshow.activities;

import com.google.gson.Gson;

import edu.myhorseshow.Constants;
import edu.myhorseshow.R;
import edu.myhorseshow.UserInfo;
import edu.myhorseshow.models.User;
import edu.myhorseshow.utility.JsonObject;
import edu.myhorseshow.utility.UrlBuilder;
import edu.myhorseshow.utility.Utility;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreateAccountActivity extends Activity implements OnClickListener
{
	private static final String TAG = "CreateAccountActivity";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_account);
		
		setOnClickListeners();
	}
	
	private void setOnClickListeners()
    {
    	Button createAccountButton = (Button) findViewById(R.id.create_account_submit_button);
    	
    	createAccountButton.setOnClickListener(this);
    }

	public void onClick(View clickedView) 
	{
		switch(clickedView.getId())
    	{
    	case R.id.create_account_submit_button:
    		submitCreateInfo();
    		break;
    	default:
    		Log.w(TAG, "Firing unimplemented button event.");
    	}
	}
	
	private void submitCreateInfo()
    {
    	EditText emailField = (EditText) findViewById(R.id.create_account_email_edit_text);
    	EditText passwordField = (EditText) findViewById(R.id.create_account_password_edit_text);
    	EditText firstNameField = (EditText) findViewById(R.id.create_account_first_name_edit_text);
    	EditText lastNameField = (EditText) findViewById(R.id.create_account_last_name_edit_text);
    	EditText confirmPasswordField = (EditText) findViewById(R.id.create_account_confirmpass_edit_text);
    	
    	String emailAddress = emailField.getText().toString();
    	String password = passwordField.getText().toString();
    	String firstName = firstNameField.getText().toString();
    	String lastName = lastNameField.getText().toString();
    	String passwordCheck = confirmPasswordField.getText().toString();
    	
    	if (!password.contentEquals(passwordCheck))
    		return;
    	
    	User createdUser = new User();
    	createdUser.setEmailAdress(emailAddress);
    	createdUser.setPassword(password);
    	createdUser.setFirstName(firstName);
    	createdUser.setLastName(lastName);
    	createdUser.setUsefid(0);
    	
    	AsyncTask<User, Integer, User> fetcher = new AsyncTask<User, Integer, User>()
    	{
    		@Override
			protected User doInBackground(User... users)
    		{
    			if (users.length != 1)
    				return null;
    			
    			User user = users[0];
    			
    			String url = new UrlBuilder(Constants.SERVER_DOMAIN)
    					.addPath(Constants.TYPE_USER)
    					.toString();
		    	
		    	String result = Utility.postJsonObject(url, new JsonObject(Constants.TYPE_USER, user));
		    	return new Gson().fromJson(result, User.class);
    		}
    		
    		@Override
			protected void onPostExecute(User newUser)
    		{
    			Utility.hideDialog();
    			if (newUser == null)
    				return;
    			
    			processUserLogin(newUser);
    		}
    	};
    	
    	Utility.showProgressDialog(this,
    			getString(R.string.creating_account_caption),
    			getString(R.string.creating_account_description));
    	fetcher.execute(createdUser);
    }
	
	private void processUserLogin(User user)
	{
		UserInfo.setCurrentUser(user);
		Intent homeActivity = new Intent(this, HomeActivity.class);
		startActivity(homeActivity);
	}
}