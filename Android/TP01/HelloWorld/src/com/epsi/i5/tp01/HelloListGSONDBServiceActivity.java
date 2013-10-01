package com.epsi.i5.tp01;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.epsi.i5.tp01.db.HelloDataBase;
import com.epsi.i5.tp01.service.HelloService;
import com.google.gson.Gson;

public class HelloListGSONDBServiceActivity extends ListActivity {

	// Url d'appel
	private static final String URL = "http://developers.google.com/events/feed/json?group=104080355394243371522&start=0";
	// Constante utilisé dans les logs
	private static final String TAG = "HelloListActivity";

	// Tache asyncrhone qui va faire l'appel http
	private GDGAsyncTask asynctask = null;
	// Text vide permettant d'afficher l'état
	private TextView emptyView = null;

	// Déclaration de la base de données
	private HelloDataBase dataBaseHelper = null;
	private SQLiteDatabase database = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_hello_list);
		// On récupère le texte vide
		emptyView = (TextView) findViewById(android.R.id.empty);
		dataBaseHelper = new HelloDataBase(this);
	}

	@Override
	protected void onPause() {
		// On doit stopper la tache si elle ne l'a pas déjà été.
		if (asynctask != null && asynctask.inProgress) {
			asynctask.cancel(true);
		}
		if (dataBaseHelper != null && database != null && database.isOpen()) {
			dataBaseHelper.close();
			database = null;
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// On vérifie qu'on a pas déjà des données

		if (dataBaseHelper != null) {
			database = dataBaseHelper.getReadableDatabase();
			new DataBaseAsyncTask().execute();
		} else {
			// Sinon on fait l'appel au webservice
			asynctask = new GDGAsyncTask();
			asynctask.execute();
		}
	}

	class DataBaseAsyncTask extends AsyncTask<Void, Void, Cursor> {

		@Override
		protected Cursor doInBackground(Void... params) {
			return database.query(HelloDataBase.TABLE_EVENT, // Nom de la table à requêter
					new String[] { HelloDataBase.COL_ID, HelloDataBase.COL_EVENT_NAME }, // Colonnes à récupérer
					null, // Sélection
					null, // Arguments
					null, // Groupby
					null, // Having
					null // orderBy
					);
		}

		@Override
		protected void onPostExecute(Cursor result) {
			if (result == null) {
				emptyView.setText(R.string.error);
			} else if (result.moveToFirst()) {
				setListAdapter(new SimpleCursorAdapter(HelloListGSONDBServiceActivity.this, // context
						android.R.layout.simple_list_item_1, // layout
						result, // Cursor
						new String[] { HelloDataBase.COL_EVENT_NAME }, // from
						new int[] { android.R.id.text1 },// to
						0 // flags
				));

				Toast.makeText(getApplicationContext(), "Donnée obtenues depuis la base de données", Toast.LENGTH_LONG).show();
				;
			} else {
				new GDGAsyncTask().execute();
			}
		}

		@Override
		protected void onPreExecute() {
			// Au démarage, on met à jour la vue
			emptyView.setText(R.string.database_read);
			super.onPreExecute();
		}

	}

	class GDGAsyncTask extends AsyncTask<Void, Void, EventGDG[]> {

		private boolean inProgress = false;

		@Override
		protected EventGDG[] doInBackground(Void... params) {
			// On récupère le client http qui va nous servir à faire l'appel
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(URL);
			httpget.setHeader("Content-type", "application/json");
			httpget.addHeader("Referer", "http://referer.url.com");

			InputStream inputStream = null;
			EventGDG[] eventArray = null;
			try {
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();

				inputStream = entity.getContent();
				// json is UTF-8 by default
				Gson gson = new Gson();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
				eventArray = gson.fromJson(reader, EventGDG[].class);

			} catch (Exception e) {
				// Oops
				Log.e(TAG, "Error during getting JSON ", e);
			} finally {
				try {
					if (inputStream != null)
						inputStream.close();
				} catch (Exception squish) {
				}
			}

			return eventArray;
		}

		@Override
		protected void onPostExecute(EventGDG[] result) {
			// On se resyncrhonise avec le UI Thread
			inProgress = false;

			if (result == null) {
				emptyView.setText(R.string.error);
			} else {

				ArrayList<String> dataToStore = new ArrayList<String>();
				for (EventGDG event : result) {
					dataToStore.add(event.title);
				}
				Intent intentService = new Intent(getApplicationContext(), HelloService.class);
				intentService.putStringArrayListExtra("datas", dataToStore);
				startService(intentService);

				setListAdapter(new ArrayAdapter<EventGDG>(HelloListGSONDBServiceActivity.this, android.R.layout.simple_list_item_1, result));

				Toast.makeText(getApplicationContext(), "Donnée obtenues depuis le web", Toast.LENGTH_LONG).show();
				;

			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// Au démarage, on met à jour la vue
			emptyView.setText(R.string.Loading);
			inProgress = true;
			super.onPreExecute();
		}

	}

	class EventGDG {

		public String description;

		public String title;

		public String temporalRelation;

		private String link;

		private String location;

		private long id;

		@Override
		public String toString() {
			return title;
		}

	}

}
