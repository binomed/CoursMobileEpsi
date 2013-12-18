package com.epsi.i5.tp04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class HelloWorldStartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		boolean isTablet = getResources().getBoolean(R.bool.isTablet);
		if (isTablet) {
			startActivity(new Intent(getApplicationContext(), HelloWorldTabletActivity.class));
		} else {
			startActivity(new Intent(getApplicationContext(), HelloWorldActivity.class));
		}
		finish();
	}

}
