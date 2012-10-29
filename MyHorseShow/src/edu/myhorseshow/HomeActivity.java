package edu.myhorseshow;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		welcomeUser(getIntent().getStringExtra(MainActivity.USERNAME));
	}
	
	private void welcomeUser(String username)
	{
		TextView headerTextView = (TextView) findViewById(R.id.activity_home_header_text_view);
		headerTextView.setText(String.format(getString(R.string.welcome_header), username));
	}
}
