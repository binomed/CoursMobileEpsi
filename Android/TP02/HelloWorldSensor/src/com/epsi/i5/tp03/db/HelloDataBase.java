package com.epsi.i5.tp03.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HelloDataBase extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "HelloDatabase";
	private static final int VERSION = 1;

	public static final String COL_ID = "_id";
	public static final String COL_EVENT_NAME = "eventname";

	public static final String TABLE_EVENT = "gdgevent";
	private static final String CREATE_TABLE_EVENT = "create table " + TABLE_EVENT + "("//
			+ COL_ID + " integer primary key autoincrement, "//
			+ COL_EVENT_NAME + " text not null"//
			+ ");";

	public HelloDataBase(Context context) {

		super(context, //
				DATABASE_NAME,// On fixe le nom de la base
				null, // On ne passe pas de Factory
				VERSION // On fixe la version
		);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// On créer notre table
		db.execSQL(CREATE_TABLE_EVENT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int olderVersion, int newVersion) {
		// Sera vide car on a pas d'évolution de la base de donnée
	}

}
