package com.example.atm_booths;

import java.util.ArrayList;
import java.util.Arrays;

import com.example.atm_boots.adapter.CityAdapter;
import com.example.dbhelper.DBhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class CityActivity extends Activity {

	ListView lv;
	EditText inputSearch;
    Context context;
    CityAdapter ca;
    String bank;
    ArrayAdapter<String> adapter;
    ArrayList<String> city_sort = new ArrayList<String>();
    ArrayList<String> city = new ArrayList<String>();
    ArrayList<Integer> boothcount=new ArrayList<Integer>();
    ArrayList<Integer> count_sort=new ArrayList<Integer>();
    
    public static String [] cityNameList={"Barguna","Barisal","Bhola","Jhalokati","Patuakhali","Pirojpur","Bandarban","Brahmanbaria","Chandpur"};
    private static final String DB_NAME = "mydatabase.sqlite";
	private SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city);
        Bundle bundle = getIntent().getExtras();
        bank= bundle.getString("bank");
        DBhelper dbOpenHelper = new DBhelper(this, DB_NAME);
        database = dbOpenHelper.openDataBase();
        
        //Arrays.sort(cityNameList);
        lv=(ListView) findViewById(R.id.listView);
        inputSearch = (EditText) findViewById(R.id.inputSearch);
       // adapter = new ArrayAdapter<String>(this, R.layout.location, R.id.textView1, cityNameList);
        //lv.setAdapter(adapter);
        
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        context=this;
        Cursor myCursor2;
        Cursor myCursor = dbOpenHelper.getAllCities();
        myCursor.moveToFirst();
		if(!myCursor.isAfterLast()) {
			do {
				String name = myCursor.getString(0);
				city.add(name);
				myCursor2 = dbOpenHelper.getBoothCount(bank, name);
		        myCursor2.moveToFirst();
				if(!myCursor2.isAfterLast()) {
					do {
						int n = myCursor2.getInt(0);
						boothcount.add(n);
						
					} while (myCursor2.moveToNext());
				}
				myCursor2.close();
			} while (myCursor.moveToNext());
		}
		myCursor.close();
        ca=new CityAdapter(this, city, bank,boothcount);
        lv.setAdapter(ca);
        
        inputSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int start, int before, int count) {
                // When user changed the Text
            	  int textlength = inputSearch.getText().length();
            	  city_sort.clear();

            	  for (int i = 0; i < city.size(); i++)
            	  {
            	     if (textlength <= city.get(i).length())
            	     {
            	    	 if (inputSearch.getText().toString().equalsIgnoreCase((String) city.get(i).subSequence(0, textlength)))
            	    	 {
            	    		 city_sort.add(city.get(i));
            	    		 count_sort.add(boothcount.get(i));
            	    	 }
            	     	}
            	    }
            	  //if(city_sort.size()==0)
            		//  Toast.makeText(getApplicationContext(),"No City Found", Toast.LENGTH_LONG).show();
            	  lv.setAdapter(new CityAdapter(CityActivity.this, city_sort,bank,count_sort));
            }
            
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
 
        
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
