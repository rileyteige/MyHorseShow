package edu.myhorseshow.activities;

import edu.myhorseshow.AppModel;
import android.app.Activity;

public class AppActivity extends Activity
{
	protected AppModel getModel() { return mAppModel; }
	private AppModel mAppModel = AppModel.getInstance();
}
