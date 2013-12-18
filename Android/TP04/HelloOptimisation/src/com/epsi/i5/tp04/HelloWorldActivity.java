package com.epsi.i5.tp04;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.android.debug.hv.ViewServer;

public class HelloWorldActivity extends ActionBarActivity {

	// Déclaration des boutons
	private Button button, buttonOptim, buttonFragment, buttonFragmentOptim;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);
		getSupportActionBar().setTitle("Action Bar");

		button = (Button) findViewById(R.id.btnFirstListActivity);
		buttonOptim = (Button) findViewById(R.id.btnOptimListActivity);
		buttonFragment = (Button) findViewById(R.id.btnFirstListFragment);
		buttonFragmentOptim = (Button) findViewById(R.id.btnOptimListFragment);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HelloListActivity.class);
				startActivity(intent);
			}

		});

		buttonOptim.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HelloListOptimActivity.class);
				startActivity(intent);
			}

		});

		buttonFragment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HelloWorldPhoneActivity.class);
				intent.putExtra("index", 1);
				startActivity(intent);
			}

		});
		buttonFragmentOptim.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HelloWorldPhoneActivity.class);
				intent.putExtra("index", 2);
				startActivity(intent);
			}

		});

		ViewServer.get(this).addWindow(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ViewServer.get(this).removeWindow(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		ViewServer.get(this).setFocusedWindow(this);
		// On vérifie qu'on a pas déjà des données
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hello_world, menu);
		return true;
	}

}
