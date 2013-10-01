package com.epsi.i5.tp01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class HelloWorldActivity extends Activity {

	// Déclaration des boutons
	private Button button, buttonGSON, buttonService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);

		button = (Button) findViewById(R.id.btnFirstListActivity);
		buttonGSON = (Button) findViewById(R.id.btnGsonListActivity);
		buttonService = (Button) findViewById(R.id.btnServiceListActivity);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), HelloListActivity.class);
				startActivity(intent);
			}

		});

		buttonGSON.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), HelloListGSONActivity.class));
			}

		});

		buttonService.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), HelloListGSONDBServiceActivity.class));
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hello_world, menu);
		return true;
	}

}
