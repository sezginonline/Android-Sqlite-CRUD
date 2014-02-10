package com.sezginserin.asrehber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBController extends SQLiteOpenHelper {

	public DBController(Context applicationcontext) {
		super(applicationcontext, "androidsqlite.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;
		query = "CREATE TABLE persons ( " + "id INTEGER PRIMARY KEY, "
				+ "name TEXT, " + "surname TEXT, " + "firm TEXT, "
				+ "gsm TEXT, " + "email TEXT " + ")";
		database.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old,
			int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS persons";
		database.execSQL(query);
		onCreate(database);
	}

	public void insertPerson(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", queryValues.get("name"));
		values.put("surname", queryValues.get("surname"));
		values.put("firm", queryValues.get("firm"));
		values.put("gsm", queryValues.get("gsm"));
		values.put("email", queryValues.get("email"));
		database.insert("persons", null, values);
		database.close();
	}

	public int updatePerson(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", queryValues.get("name"));
		values.put("surname", queryValues.get("surname"));
		values.put("firm", queryValues.get("firm"));
		values.put("gsm", queryValues.get("gsm"));
		values.put("email", queryValues.get("email"));
		return database.update("persons", values, "id" + " = ?",
				new String[] { queryValues.get("id") });
	}

	public void deletePerson(String id) {
		SQLiteDatabase database = this.getWritableDatabase();
		String deleteQuery = "DELETE FROM persons where id = '" + id + "'";
		database.execSQL(deleteQuery);
	}

	public ArrayList<HashMap<String, String>> searchPerson(String q) {
		q = q.replace("'", "''"); // Escape single-quotes
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT * FROM persons WHERE LOWER(name) LIKE '%"
				+ q.toLowerCase(Locale.getDefault())
				+ "%' OR LOWER(surname) LIKE '%"
				+ q.toLowerCase(Locale.getDefault())
				+ "%' OR LOWER(firm) LIKE '%"
				+ q.toLowerCase(Locale.getDefault())
				+ "%' OR UPPER(name) LIKE '%"
				+ q.toUpperCase(Locale.getDefault())
				+ "%' OR UPPER(surname) LIKE '%"
				+ q.toUpperCase(Locale.getDefault())
				+ "%' OR UPPER(firm) LIKE '%"
				+ q.toUpperCase(Locale.getDefault())
				+ "%' ORDER BY name COLLATE LOCALIZED";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", cursor.getString(0));
				map.put("name", cursor.getString(1));
				map.put("surname", cursor.getString(2));
				map.put("firm", cursor.getString(3));
				map.put("gsm", cursor.getString(4));
				map.put("email", cursor.getString(5));
				wordList.add(map);
			} while (cursor.moveToNext());
		}

		return wordList;
	}

	public ArrayList<HashMap<String, String>> getAllPersons() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT * FROM persons ORDER BY "
				+ "name COLLATE LOCALIZED, surname COLLATE LOCALIZED";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("id", cursor.getString(0));
				map.put("name", cursor.getString(1));
				map.put("surname", cursor.getString(2));
				map.put("firm", cursor.getString(3));
				map.put("gsm", cursor.getString(4));
				map.put("email", cursor.getString(5));
				wordList.add(map);
			} while (cursor.moveToNext());
		}

		return wordList;
	}

	public HashMap<String, String> getPersonInfo(String id) {
		HashMap<String, String> wordList = new HashMap<String, String>();
		SQLiteDatabase database = this.getReadableDatabase();
		String selectQuery = "SELECT * FROM persons where id = '" + id + "'";
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				wordList.put("name", cursor.getString(1));
				wordList.put("surname", cursor.getString(2));
				wordList.put("firm", cursor.getString(3));
				wordList.put("gsm", cursor.getString(4));
				wordList.put("email", cursor.getString(5));
			} while (cursor.moveToNext());
		}
		return wordList;
	}
}
