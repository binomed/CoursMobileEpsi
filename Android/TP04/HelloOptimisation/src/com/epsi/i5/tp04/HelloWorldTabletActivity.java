package com.epsi.i5.tp04;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class HelloWorldTabletActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);

		getSupportFragmentManager().beginTransaction() //
				.replace(R.id.listLeft, new HelloListFragment())//
				.replace(R.id.listRight, new HelloListOptimFragment())//
				.commit();

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
