package com.epsi.i5.tp04;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class HelloWorldPhoneActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_empty);

		if (getIntent().getIntExtra("index", 1) == 1) {
			getSupportFragmentManager().beginTransaction().replace(R.id.content, new HelloListFragment()).commit();
		} else {
			getSupportFragmentManager().beginTransaction().replace(R.id.content, new HelloListOptimFragment()).commit();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

}
