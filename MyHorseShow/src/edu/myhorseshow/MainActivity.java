package edu.myhorseshow;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.myhorseshow.utility.*;
import edu.myhorseshow.user.User;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        setOnClickListeners();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
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
    		protected User doInBackground(String... emailPassword)
    		{
    			if (emailPassword.length != 2)
    				return null;
    			
    			String email = emailPassword[0];
    			String password = emailPassword[1];
    			
		    	String serverIp = "140.160.62.42";
		    	String url = "http://" + serverIp + "/enter.php?"
		    					+ Constants.EMAIL_ADDR_PARAM + "=" + email + "&"
		    					+ Constants.PASSWORD_PARAM + "=" + password;
		    	
		    	HttpClient httpclient = new DefaultHttpClient();
		    	HttpGet httpget = new HttpGet(url);
		    	HttpResponse response;
		    	String result = null;
		    	
		    	try
		    	{
		    		response = httpclient.execute(httpget);
		    		Log.d(TAG, response.getStatusLine().toString());
		    		
		    		HttpEntity entity = response.getEntity();
		    		if (entity != null)
		    		{
		    			InputStream instream = entity.getContent();
		    			result = Utility.convertStreamToString(instream);
		    			instream.close();
		    		}
		    		
		    	} catch (Exception e) { e.printStackTrace(); }
		    	
		    	return new Gson().fromJson(result, User.class);
    		}
    		
    		protected void onPostExecute(User user)
    		{
    			mLoadingDialog.dismiss();
    			processUserLogin(user);
    			clearForm();
    		}
    	};
    	
    	mLoadingDialog = ProgressDialog.show(this, 
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
    	Intent homeActivityIntent = new Intent(this, HomeActivity.class);
    	homeActivityIntent.putExtra(USERNAME, user.getFirstName() + " " + user.getLastName());
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
    
    private ProgressDialog mLoadingDialog;
    private static final String TAG = "MainActivity";
    public static final String USERNAME = "edu.myhorseshow.USERNAME";
}
