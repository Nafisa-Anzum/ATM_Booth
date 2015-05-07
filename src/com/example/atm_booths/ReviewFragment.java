package com.example.atm_booths;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.atm_boots.adapter.ReviewAdapter;
import com.example.dbhelper.DBhelper;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReviewFragment extends Fragment{
	ListView lv;
	TextView t1;
	TextView t2;
	public static int [] userImages={R.drawable.user_imge,R.drawable.user_imge};
	public static String [] userList={"Farzana Ali","Pias Rahman"};
	public static String [] reviewList={"The machine works well","I couldn't withdraw required amount >:O Not good"};    
	private static final String DB_NAME = "mydatabase.sqlite";
	private SQLiteDatabase database;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.review, container, false);
        lv=(ListView) rootView.findViewById(R.id.listView1);
        t1= (TextView) rootView.findViewById(R.id.textview1);
        t2= (TextView) rootView.findViewById(R.id.textview2);
        Bundle bundle = getActivity().getIntent().getExtras();
        String Idname = bundle.getString("id"); 
        int id= Integer.parseInt(Idname);
        DBhelper dbOpenHelper = new DBhelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        Cursor myCursor;
        myCursor = dbOpenHelper.getBankBooth(id);
        myCursor.moveToFirst();
        
		if(!myCursor.isAfterLast()) {
			do {
				
				String n1= myCursor.getString(0);
				String n2= myCursor.getString(1);
				t1.setText(n2+" ATM Booth");
				t2.setText("Address: "+n1);
				
			} while (myCursor.moveToNext());
		}
		myCursor.close();
        
        
        lv.setAdapter(new ReviewAdapter(this, reviewList, userList,  userImages));
        return rootView;
    }

}

