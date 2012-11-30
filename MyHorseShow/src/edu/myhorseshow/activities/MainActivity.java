package edu.myhorseshow.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.myhorseshow.Constants;
import edu.myhorseshow.R;
import edu.myhorseshow.UserInfo;
import edu.myhorseshow.R.id;
import edu.myhorseshow.R.layout;
import edu.myhorseshow.R.string;
import edu.myhorseshow.models.User;
import edu.myhorseshow.utility.*;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Utility.setMainThread(Thread.currentThread());
        setOnClickListeners();
        
        test();
    }
    
    private void test()
    {
    	EditText emailField = (EditText) findViewById(R.id.main_email_edit_text);
    	EditText passwordField = (EditText) findViewById(R.id.main_password_edit_text);
    	
    	emailField.setText("riley.teige@gmail.com");
    	passwordField.setText("password");
    	
    	submitLoginInfo();
    }
    
    public void onClick(View clickedView)
    {
    	switch(clickedView.getId())
    	{
    	case R.id.main_screen_submit_button:
    		submitLoginInfo();
    		break;
    	case R.id.main_screen_create_account_button:
    		createAccount();
    		break;
    	default:
    		Log.w(TAG, "Firing unimplemented button event.");
    	}
    }
    
    private void setOnClickListeners()
    {
    	Button submitButton = (Button) findViewById(R.id.main_screen_submit_button);
    	Button createAccountButton = (Button) findViewById(R.id.main_screen_create_account_button);
    	
    	submitButton.setOnClickListener(this);
    	createAccountButton.setOnClickListener(this);
    }
    
    private void submitLoginInfo()
    {
    	EditText emailField = (EditText) findViewById(R.id.main_email_edit_text);
    	EditText passwordField = (EditText) findViewById(R.id.main_password_edit_text);
    	
    	String emailAddress = emailField.getText().toString();
    	String password = passwordField.getText().toString();
    	
    	AsyncTask<String, Integer, User> fetcher = new AsyncTask<String, Integer, User>()
    	{
    		@Override
			protected User doInBackground(String... emailPassword)
    		{
    			if (emailPassword.length != 2)
    				return null;
    			
    			String email = emailPassword[0];
    			String password = emailPassword[1];
    			
    			String url = new UrlBuilder(Constants.SERVER_DOMAIN)
    					.addPath(Constants.TYPE_USER)
    					.addPath(email)
    					.addPath(password)
    					.toString();
		    	
		    	return Utility.getJsonObject(url, User.class);
    		}
    		
    		@Override
			protected void onPostExecute(User user)
    		{
    			Utility.hideDialog();
    			processUserLogin(user);
    			clearForm();
    		}
    	};
    	
    	Utility.showProgressDialog(this, 
    			getString(R.string.logging_in_caption), 
    			getString(R.string.logging_in_description));
    	
    	fetcher.execute(emailAddress, password);
    }
    
    private void processUserLogin(User user)
    {
    	if (user == null)
    		return;
    	
    	if (user.getId() == Constants.INVALID_LOGIN_CODE)
    	{
    		setInvalidLoginVisible(true);
    		return;
    	}
    	
    	setInvalidLoginVisible(false);
    	UserInfo.setCurrentUser(user);
    	Intent homeActivityIntent = new Intent(this, HomeActivity.class);
    	startActivity(homeActivityIntent);
    }
    
    private void createAccount()
    {
    	Intent createAccountActivityIntent = new Intent(this, CreateAccountActivity.class);
    	startActivity(createAccountActivityIntent);
    }
    
    private void clearForm()
    {
    	EditText emailText = (EditText) findViewById(R.id.main_email_edit_text);
    	EditText passwordText = (EditText) findViewById(R.id.main_password_edit_text);
    	emailText.setText(null);
    	passwordText.setText(null);
    }
    
    private void setInvalidLoginVisible(boolean value)
    {
    	TextView invalidTextView = (TextView) findViewById(R.id.main_invalid_login_text_view);
    	invalidTextView.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }
    
    private static final String TAG = "MainActivity";
    public static final String USERNAME = "edu.myhorseshow.USERNAME";
}
