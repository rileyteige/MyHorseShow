package edu.myhorseshow;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
    	Intent homeActivityIntent = new Intent(this, HomeActivity.class);
    	String username = "<<SomeUser>>";
    	homeActivityIntent.putExtra(USERNAME, username);
    	startActivity(homeActivityIntent);
    }
    
    private void createAccount()
    {
    	sayNotImplemented("createAccount");
    }
    
    private static void sayNotImplemented(String what)
    {
    	Log.w(TAG, what + " has not been implemented yet.");
    }
    
    private static final String TAG = "MainActivity";
    public static final String USERNAME = "edu.myhorseshow.USERNAME";
}
