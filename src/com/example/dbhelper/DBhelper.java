package com.example.dbhelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {

	public static String DB_PATH;
	public static String DB_NAME;
	public SQLiteDatabase database;
	public final Context context;
	
	public SQLiteDatabase getDb() {
		return database;
	}

	public DBhelper(Context context, String databaseName) {
		super(context, databaseName, null, 1);
		this.context = context;
		String packageName = context.getPackageName();
		DB_PATH = String.format("//data//data//%s//databases//", packageName);
		DB_NAME = databaseName;
		openDataBase();
	}

	public void createDataBase() {
		boolean dbExist = checkDataBase();
		if (!dbExist) {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				Log.e(this.getClass().toString(), "Copying error");
				throw new Error("Error copying database!");
			}
		} else {
			Log.i(this.getClass().toString(), "Database already exists");
		}
	}
	private boolean checkDataBase() {
		SQLiteDatabase checkDb = null;
		try {
			String path = DB_PATH + DB_NAME;
			checkDb = SQLiteDatabase.openDatabase(path, null,
					SQLiteDatabase.OPEN_READONLY);
		} catch (SQLException e) {
			Log.e(this.getClass().toString(), "Error while checking db");
		}
		if (checkDb != null) {
			checkDb.close();
		}
		return checkDb != null;
	}
	private void copyDataBase() throws IOException {
		InputStream externalDbStream = context.getAssets().open(DB_NAME);

		String outFileName = DB_PATH + DB_NAME;

		OutputStream localDbStream = new FileOutputStream(outFileName);

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = externalDbStream.read(buffer)) > 0) {
			localDbStream.write(buffer, 0, bytesRead);
		}
		
		localDbStream.close();
		externalDbStream.close();

	}

	public SQLiteDatabase openDataBase() throws SQLException {
		String path = DB_PATH + DB_NAME;
		if (database == null) {
			createDataBase();
			database = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READWRITE);
		}
		return database;
	}
	@Override
	public synchronized void close() {
		if (database != null) {
			database.close();
		}
		super.close();
	}
	@Override
	public void onCreate(SQLiteDatabase db) {}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
	public Cursor getAllBooths () {
  		String SQL = "SELECT * FROM Booth;" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getAllBanks () {
  		String SQL = "SELECT * FROM Bank order by bankName;" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getAllCities() {
  		String SQL = "SELECT DISTINCT city FROM Location order by city;" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getPlaces(String city) {
  		String SQL = "SELECT place FROM Location where city='"+city+"';" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getBooths(String bank,String city) {
  		String SQL = "SELECT boothId, address, longitude, latitude FROM Booth bo, Bank ba,Location l where bo.bankId=ba.bankId and bo.locationId=l.locationId and ba.bankName='"+bank+"' and l.city='"+city+"';" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getUserBooths(String bank) {
  		String SQL = "SELECT address FROM Booth bo, Bank ba,Location l where bo.bankId=ba.bankId and bo.locationId=l.locationId and ba.bankName='"+bank+"';" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getOnlyBooth(int id) {
  		String SQL = "SELECT * FROM Booth where boothId = "+id+";" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getBankBooth(int id) {
  		String SQL = "SELECT bo.address, ba.bankName FROM Booth bo, Bank ba where bo.bankId=ba.bankId and bo.boothId = "+id+";" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getBoothbyPlace(String bank,String city,String place) {
  		String SQL = "SELECT boothId, address, longitude, latitude FROM Booth bo, Bank ba,Location l where bo.bankId=ba.bankId and bo.locationId=l.locationId and ba.bankName='"+bank+"' and l.city='"+city+"'and l.place='"+place+"';" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public Cursor getBoothCount(String bank,String city) {
  		String SQL = "SELECT count(*) FROM Booth bo, Bank ba,Location l where bo.bankId=ba.bankId and bo.locationId=l.locationId and ba.bankName='"+bank+"' and l.city='"+city+"';" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
  	}
	public void addUserBank(String bankName){
		String SQL="SELECT bankId FROM Bank where bankName='"+bankName+"';";
		Cursor cursor =database.rawQuery(SQL, null) ;
		int id=1 ;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()) {
			do {
				id = cursor.getInt(0);
				
			} while (cursor.moveToNext());
		}
		cursor.close();
		//String SQL1="INSERT INTO User(bankId) values("+id+")";
		String SQL1="UPDATE User set bankId="+id+" WHERE userId=1;";
		database.execSQL(SQL1);
		//database.execSQL("commit;");
	}
	public Cursor getUserBank(){
		String SQL = "SELECT bankName FROM Bank ba, User u where u.bankId=ba.bankId and u.userId=1;" ;
  		Cursor cursor =database.rawQuery(SQL, null) ;
  		return cursor ;
	}
	
}
