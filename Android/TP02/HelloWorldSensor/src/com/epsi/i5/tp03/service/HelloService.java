package com.epsi.i5.tp03.service;

import java.util.List;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epsi.i5.tp03.db.HelloDataBase;

public class HelloService extends IntentService {

	private static final String TAG = "HelloService";

	public HelloService() {
		super(HelloService.class.getCanonicalName());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// On va récupérer les données de l'intent et écrire dans la base de données

		List<String> datas = intent.getExtras().getStringArrayList("datas");

		HelloDataBase dataBaseHelper = new HelloDataBase(this);

		try {
			// On commence par vider la table
			SQLiteDatabase dataBase = dataBaseHelper.getWritableDatabase();
			dataBase.delete(HelloDataBase.TABLE_EVENT, null, null);
			ContentValues values = new ContentValues();
			long result = -1;
			for (String data : datas) {
				values.put(HelloDataBase.COL_EVENT_NAME, data);
				result = dataBase.insert(HelloDataBase.TABLE_EVENT, null, values);
				if (result == -1) {
					Log.e(TAG, "Une donnée a été mal inséré : " + data);
				}
			}

			Log.i(TAG, "Données correctement stockées");
		} catch (Exception e) {
			Log.e(TAG, "erreur pendant l'écriture en base : " + e);
		} finally {
			if (dataBaseHelper != null) {
				dataBaseHelper.close();
			}
		}

	}

}
