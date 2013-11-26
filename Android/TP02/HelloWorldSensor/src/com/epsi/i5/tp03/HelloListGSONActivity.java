package com.epsi.i5.tp03;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

public class HelloListGSONActivity extends ListActivity {

	// Url d'appel
	private static final String URL = "http://developers.google.com/events/feed/json?group=104080355394243371522&start=0";
	// Constante utilisé dans les logs
	private static final String TAG = "HelloListActivity";

	// Tache asyncrhone qui va faire l'appel http
	private GDGAsyncTask asynctask = null;
	// Données à afficher
	private EventGDG[] datas = null;
	// Text vide permettant d'afficher l'état
	private TextView emptyView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_hello_list);
		// On récupère le texte vide
		emptyView = (TextView) findViewById(android.R.id.empty);
	}

	@Override
	protected void onPause() {
		// On doit stopper la tache si elle ne l'a pas déjà été.
		if (asynctask != null && asynctask.inProgress) {
			asynctask.cancel(true);
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// On vérifie qu'on a pas déjà des données
		if (datas == null) {
			// Sinon on fait l'appel au webservice
			asynctask = new GDGAsyncTask();
			asynctask.execute();
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
				datas = result;

				setListAdapter(new ArrayAdapter<EventGDG>(HelloListGSONActivity.this, android.R.layout.simple_list_item_1, datas));

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
