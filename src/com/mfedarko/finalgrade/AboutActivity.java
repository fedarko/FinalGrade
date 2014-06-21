/* Copyright 2013, 2014 Marcus Fedarko
 * Contact Email: marcus.fedarko@gmail.com
 * 
 * This file is part of FinalGrade.
 * 
 *     FinalGrade is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  FinalGrade is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with FinalGrade.  If not, see <http://www.gnu.org/licenses/>.
 */

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

	public void open_other_apps(View view) {
		/* Called when the user presses the "Other Apps" button. */
		
		Intent i = new Intent(Intent.ACTION_VIEW);
		try {
			i.setData(Uri.parse("market://search?q=pub:Marcus Fedarko"));
			startActivity(i);
		} catch (android.content.ActivityNotFoundException e) {
			// If Google Play isn't installed on the device, just open the Google Play query in the browser
			i.setData(Uri.parse("http://play.google.com/store/search?q=pub:Marcus Fedarko"));
			startActivity(i);
		}
	}
}
