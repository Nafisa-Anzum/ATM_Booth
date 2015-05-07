package com.example.atm_booths;

import java.util.ArrayList;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.atm_boots.adapter.CustomAdapter;
import com.example.dbhelper.DBhelper;

public class BankChoose extends Fragment implements OnClickListener{
	
	private static final String DB_NAME = "mydatabase.sqlite";
	private SQLiteDatabase database;
	ListView lv;
	//public static String [] prgmNameList={"DBBL","AB Bank","Trust Bank","BRAC Bank Limited","Eastern Bank Limited","Farmers Bank Limited","IFIC Bank Limited","Mutual Trust Bank Limited"};
	private ArrayList<String> banks;   
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.banklist, container, false);
        
        lv=(ListView) rootView.findViewById(R.id.listView1);
        DBhelper dbOpenHelper = new DBhelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        banks = new ArrayList<String>();
        Cursor myCursor = dbOpenHelper.getAllBanks();
        myCursor.moveToFirst();
		if(!myCursor.isAfterLast()) {
			do {
				String name = myCursor.getString(1);
				banks.add(name);
			} while (myCursor.moveToNext());
		}
		myCursor.close();
        lv.setAdapter(new CustomAdapter(this, banks));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

    		@Override
    		public void onItemClick(AdapterView<?> parent, View view, int position,
    				long id) {
    			
    			Intent i = new Intent(getActivity(), CityActivity.class);
    			Bundle bundle = new Bundle();
          	  bundle.putString("bank", banks.get(position));  
          	  i.putExtras(bundle);
          	  startActivity(i);
    			
    			
    		}
                      });
        return rootView;
    }

	@Override
	public void onClick(View V) {
		
		// TODO Auto-generated method stub
		
					
					
		
	}

}


