package com.mfedarko.finalgrade;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.text.method.LinkMovementMethod;

public class AboutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
        // Make the hyperlinks in the credits TextView clickable
        TextView credits = (TextView) findViewById(R.id.credits);
        credits.setMovementMethod(LinkMovementMethod.getInstance());
	}

	public void open_m_physics(View view) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		try {
			i.setData(Uri.parse("market://details?id=com.mfedarko.m_physics"));
			startActivity(i);
		} catch (android.content.ActivityNotFoundException e) {
			// If Google Play isn't installed on the device, just open M-Physics in the browser
			i.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.mfedarko.m_physics"));
			startActivity(i);
		}
	}
}
