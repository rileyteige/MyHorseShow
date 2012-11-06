package edu.myhorseshow;

import android.app.Activity;
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
    	EditText userNameField = (EditText) findViewById(R.id.create_account_username_edit_text);
    	
    	
    	String emailAddress = emailField.getText().toString();
    	String password = passwordField.getText().toString();
    	String firstName = firstNameField.getText().toString();
    	String lastName = lastNameField.getText().toString();
    	String userName = userNameField.getText().toString();
    	
    	/*AsyncTask<String, Integer, User> fetcher = new AsyncTask<String, Integer, User>()
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
    	
    	fetcher.execute(emailAddress, password);*/
    }
}