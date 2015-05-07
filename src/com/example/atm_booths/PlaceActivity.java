package com.example.atm_booths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.atm_boots.adapter.BoothAdapter;
import com.example.atm_boots.adapter.ExpandableListAdapter;
import com.example.dbhelper.DBhelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PlaceActivity extends Activity implements OnClickListener {

	private GoogleMap map;
	private MapView mapView;
	ExpandableListAdapter listAdapter;
	ExpandableListView expListView;
	ListView lv;
	String city;
	String bank;
	String place;
	List<String> listDataHeader;
	Context context;
    BoothAdapter ca;
    ArrayAdapter<String> adapter;
    ArrayList<String> ct = new ArrayList<String>();
	private static final String DB_NAME = "mydatabase.sqlite";
	private SQLiteDatabase database;
	HashMap<String, List<String>> listDataChild;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.places);
		Bundle bundle = getIntent().getExtras();
		DBhelper dbOpenHelper = new DBhelper(this, DB_NAME);
		database = dbOpenHelper.openDataBase();
		double latitude =0;
	    double longitude=0;
	    city = bundle.getString("city"); 
	    bank= bundle.getString("bank"); 
	    place= bundle.getString("place"); 
	    mapView=(MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map = mapView.getMap();
		// get the listview
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		lv=(ListView) findViewById(R.id.listView);
		// preparing list data
		prepareListData();
		Cursor myCursor;
		context=this;
		
		if(place==null){ 
			myCursor = dbOpenHelper.getBooths(bank,city);
		}
        else myCursor = dbOpenHelper.getBoothbyPlace("DBBL",city,place);
        myCursor.moveToFirst();
		if(!myCursor.isAfterLast()) {
			do {
				String id= myCursor.getString(0);
				String name = myCursor.getString(1);
				String n1= myCursor.getString(2);
				String n2= myCursor.getString(3);
				//Toast.makeText(this,n1+" "+n2, Toast.LENGTH_SHORT).show();
				latitude = Double.parseDouble(n1);
			    longitude = Double.parseDouble(n2);
			    MarkerOptions marker = new MarkerOptions().position(
		                new LatLng(latitude, longitude)).title(id);
			    marker.icon(BitmapDescriptorFactory
		                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		
		        // adding marker
	        map.addMarker(marker);
				//ct.add(name);
			} while (myCursor.moveToNext());
		}
		myCursor.close();
		CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(latitude, longitude)).zoom(12).build();
		map.animateCamera(CameraUpdateFactory
        .newCameraPosition(cameraPosition));
		map.setOnMarkerClickListener(new OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
               // if(arg0.getTitle().equals("MyHome")) // if marker source is clicked
                     // display toast
                	//Toast.makeText(mapView.getContext(),arg0.getTitle(), Toast.LENGTH_SHORT).show();
                	Intent i=new Intent(context,BoothInfoPage.class);
                	Bundle bundle = new Bundle();   
                    bundle.putString( "id",arg0.getTitle());
                    i.putExtras(bundle); 
                    startActivity(i);
                return true;
            }
        });
//        ca=new BoothAdapter(this, ct);
//        lv.setAdapter(ca);

		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

		// setting list adapter
		expListView.setAdapter(listAdapter);

		// Listview Group click listener
		expListView.setOnGroupClickListener(new OnGroupClickListener() {

			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// Toast.makeText(getApplicationContext(),
				// "Group Clicked " + listDataHeader.get(groupPosition),
				// Toast.LENGTH_SHORT).show();
				return false;
			}
		});

		// Listview Group expanded listener
		expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
//				Toast.makeText(getApplicationContext(),
//						listDataHeader.get(groupPosition) + " Expanded",
//						Toast.LENGTH_SHORT).show();
			}
		});

		// Listview Group collasped listener
		expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(getApplicationContext(),
						listDataHeader.get(groupPosition) + " Collapsed",
						Toast.LENGTH_SHORT).show();

			}
		});
		
		// Listview on child click listener
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				String item = parent.getExpandableListAdapter().getChild(groupPosition, childPosition).toString();
				Bundle bundle = new Bundle();
				bundle.putString("city",city); 
          	  	bundle.putString("bank", bank); 
          	  	bundle.putString("place", item); 
				Intent i = new Intent(getApplicationContext(), PlaceActivity.class);
		        i.putExtras(bundle);
		        startActivity(i);
		        finish();
				
				return false;
			}
		});
	}

	/*
	 * Preparing the list data
	 */
	private void prepareListData() {
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();

		// Adding child data
		listDataHeader.add("Places");
		 DBhelper dbOpenHelper = new DBhelper(this, DB_NAME);
	        database = dbOpenHelper.openDataBase();
	        List<String> place = new ArrayList<String>();
		// Adding child data
		 Cursor myCursor = dbOpenHelper.getPlaces(city);
		 myCursor.moveToFirst();
			if(!myCursor.isAfterLast()) {
				do {
					String name = myCursor.getString(0);
					place.add(name);
				} while (myCursor.moveToNext());
			}
			myCursor.close();

		listDataChild.put(listDataHeader.get(0), place); // Header, Child data
	}

	@Override
	public void onClick(View arg0) {
		
		Intent i=new Intent(this,BoothInfoPage.class);
        startActivity(i);
		
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

