package edu.myhorseshow;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

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
    	
    	String serverIp = "140.160.62.42";
    	String url = "http://" + serverIp + "/enter.php?addr=" + emailAddress + "&p=" + password;
    	HttpClient httpclient = new DefaultHttpClient();
    	HttpGet httpget = new HttpGet(url);
    	HttpResponse response;
    	try
    	{
    		response = httpclient.execute(httpget);
    		Log.d(TAG, response.getStatusLine().toString());
    		
    		/*HttpEntity entity = response.getEntity();
    		if (entity != null)
    		{
    			InputStream instream = entity.getContent();
    			String result = convertStreamToString(instream);
    			Log.d(TAG, result);
    			instream.close();
    		}*/
    	} catch (Exception e) { e.printStackTrace(); }
    	
    	Intent homeActivityIntent = new Intent(this, HomeActivity.class);
    	String username = "<<SomeUser>>";
    	homeActivityIntent.putExtra(USERNAME, username);
    	startActivity(homeActivityIntent);
    }
    
    private void createAccount()
    {
    	Intent createAccountActivityIntent = new Intent(this, CreateAccountActivity.class);
    	startActivity(createAccountActivityIntent);
    }
    
    private static final String TAG = "MainActivity";
    public static final String USERNAME = "edu.myhorseshow.USERNAME";
}
