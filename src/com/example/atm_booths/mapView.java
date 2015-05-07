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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.atm_boots.adapter.BoothAdapter;
import com.example.dbhelper.DBhelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class mapView extends Fragment implements OnClickListener{
	
	ImageView img;
	BoothAdapter ca;
	String userBank;
//	double latitude ;
//    double longitude ; 
	static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);
    private GoogleMap map;
    private MapView mapView;
	ArrayList<String> ct = new ArrayList<String>();
	private static final String DB_NAME = "mydatabase.sqlite";
	private SQLiteDatabase database;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
        View rootView = inflater.inflate(R.layout.mapview2, container, false);
        mapView=(MapView) rootView.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        map = mapView.getMap();
        DBhelper dbOpenHelper = new DBhelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        Cursor myCursor;
        myCursor = dbOpenHelper.getAllBooths();
        myCursor.moveToFirst();
		if(!myCursor.isAfterLast()) {
			do {
				String id= myCursor.getString(0);
				String n1= myCursor.getString(4);
				String n2= myCursor.getString(5);
				String name= myCursor.getString(3);
				
				double latitude = Double.parseDouble(n1);
			    double longitude = Double.parseDouble(n2);
			    MarkerOptions marker = new MarkerOptions().position(
		                new LatLng(latitude, longitude)).title(id);
			    marker.icon(BitmapDescriptorFactory
		                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
		
		        // adding marker
	        map.addMarker(marker);
				
				
			} while (myCursor.moveToNext());
		}
		myCursor.close();
        // latitude and longitude
//        double latitude = 23.806867; 
//        double longitude = 90.405250;
//        
//       
//        // create marker
//        for(int i=0;i<5;i++){
//        MarkerOptions marker = new MarkerOptions().position(
//                new LatLng(latitude+(.001*i), longitude-(.005*i))).title("Hello Maps");
//
//        // Changing marker icon
//        marker.icon(BitmapDescriptorFactory
//                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
//
//        // adding marker
//        map.addMarker(marker);
//        }
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(23.729888, 90.376754)).zoom(12).build();
        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
        map.setOnMarkerClickListener(new OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
               // if(arg0.getTitle().equals("MyHome")) // if marker source is clicked
                     // display toast
                	//Toast.makeText(mapView.getContext(),arg0.getTitle(), Toast.LENGTH_SHORT).show();
                	Intent i=new Intent(getActivity(),BoothInfoPage.class);
                	Bundle bundle = new Bundle();   
                    bundle.putString( "id",arg0.getTitle());
                    i.putExtras(bundle); 
                    startActivity(i);
                return true;
            }

        });       
        /* img= (ImageView)  rootView.findViewById(R.id.imageView1);
         img.setOnClickListener(this);*/
        /*ListView lv = (ListView) rootView.findViewById(R.id.listView);
        DBhelper dbOpenHelper = new DBhelper(this.getActivity(), DB_NAME);
        database = dbOpenHelper.openDataBase();
        Cursor myCursor;
        Cursor myCursor2 = dbOpenHelper.getUserBank();
        myCursor2.moveToFirst();
		if(!myCursor2.isAfterLast()) {
			do {
				userBank= myCursor2.getString(0);
			} while (myCursor2.moveToNext());
		}
		myCursor2.close();
        myCursor = dbOpenHelper.getUserBooths(userBank);
        myCursor.moveToFirst();
		if(!myCursor.isAfterLast()) {
			do {
				String name = myCursor.getString(0);
				ct.add(name);
			} while (myCursor.moveToNext());
		}
		myCursor.close();
        ca=new BoothAdapter(this, ct);
        lv.setAdapter(ca);
        map = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();

    Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG)
                    .title("Hamburg"));
                Marker kiel = map.addMarker(new MarkerOptions()
                    .position(KIEL)
                    .title("Kiel")
                    .snippet("Kiel is cool")
                    .icon(BitmapDescriptorFactory
                        .fromResource(R.drawable.ic_launcher)));

                // Move the camera instantly to hamburg with a zoom of 15.
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));

                // Zoom in, animating the camera.
                map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);*/

        // Perform any camera updates here
        return rootView;
    }
	
	/*@Override
	public void onResume() {
	    super.onResume();
	    mMapView.onResume();
	}

	@Override
	public void onPause() {
	    super.onPause();
	    mMapView.onPause();
	}*/

	/*@Override
	public void onDestroyView() {
	    super.onDestroyView();
	    if (mMap != null) {
	        getFragmentManager()
	                .beginTransaction()
	                .remove(getFragmentManager().findFragmentById(R.id.map))
	                .commit();
	    }
	}*/

	/*@Override
	public void onLowMemory() {
	    super.onLowMemory();
	    mMapView.onLowMemory();
	}*/
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

	/*@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent(getActivity(),BoothInfoPage.class);
        startActivity(i);
		
	}*/

}
