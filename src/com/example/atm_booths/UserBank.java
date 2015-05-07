package com.example.atm_booths;

import java.util.ArrayList;
import java.util.List;

import com.example.dbhelper.DBhelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class UserBank extends Activity implements OnClickListener{
	 Spinner spinner1;
	 private static final String DB_NAME = "mydatabase.sqlite";
		private SQLiteDatabase database;
		 DBhelper dbOpenHelper;
	Button b;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.userbank);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        dbOpenHelper = new DBhelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        List<String> list = new ArrayList<String>();
        Cursor myCursor = dbOpenHelper.getAllBanks();
        myCursor.moveToFirst();
		if(!myCursor.isAfterLast()) {
			do {
				String name = myCursor.getString(1);
				list.add(name);
			} while (myCursor.moveToNext());
		}
		myCursor.close();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                     (this, android.R.layout.simple_spinner_item,list);
                      
        dataAdapter.setDropDownViewResource
                     (android.R.layout.simple_spinner_dropdown_item);
                      
        spinner1.setAdapter(dataAdapter);
        addListenerOnButton();
        b= (Button) findViewById(R.id.button1);
       // b.setOnClickListener(this);
	}
	public void addListenerOnButton() {
		 
        spinner1 = (Spinner) findViewById(R.id.spinner1);
         
        Button btnSubmit = (Button) findViewById(R.id.button1);
 
        btnSubmit.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View v) {
 
//                Toast.makeText(UserBank.this,
//                        "On Button Click : " + 
//                        "\n" + String.valueOf(spinner1.getSelectedItem()) ,
//                        Toast.LENGTH_LONG).show();
                String s=String.valueOf(spinner1.getSelectedItem());
                dbOpenHelper.addUserBank(s);
                Intent i = new Intent(getApplicationContext(), HomeTab.class);
                Bundle bundle = new Bundle();
				bundle.putString("userBank",s);
				i.putExtras(bundle);
        		startActivity(i);
        		finish();
            }
 
        });
 
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i = new Intent(this, HomeTab.class);
		startActivity(i);
		
	}

}
