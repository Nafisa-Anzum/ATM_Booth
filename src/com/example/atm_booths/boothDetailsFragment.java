package com.example.atm_booths;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dbhelper.DBhelper;
import com.google.android.gms.games.multiplayer.realtime.WaitingRoomListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class boothDetailsFragment extends Fragment{
	
	private GoogleMap map;
    private MapView mapView;
    double latitude;
    double longitude;
    private static final String DB_NAME = "mydatabase.sqlite";
	private SQLiteDatabase database;
//	@Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
// 
//        View rootView = inflater.inflate(R.layout.boothdetails, container, false);
//        mapView=(MapView) rootView.findViewById(R.id.mapView);
//        Bundle bundle = getActivity().getIntent().getExtras();
//        String Idname = bundle.getString("id"); 
//        int id= Integer.parseInt(Idname);
//        mapView.onCreate(savedInstanceState);
//        mapView.onResume();
//        try {
//            MapsInitializer.initialize(getActivity().getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        map = mapView.getMap();
//        DBhelper dbOpenHelper = new DBhelper(this.getActivity(), DB_NAME);
//        database = dbOpenHelper.openDataBase();
//        Cursor myCursor;
//        myCursor = dbOpenHelper.getOnlyBooth(id);
//        myCursor.moveToFirst();
//        
//		if(!myCursor.isAfterLast()) {
//			do {
//				
//				String n1= myCursor.getString(4);
//				String n2= myCursor.getString(5);
//				String name= myCursor.getString(3);
//				
//				latitude = Double.parseDouble(n1);
//			    longitude = Double.parseDouble(n2);
//			    Log.d(n1, "latitude");
//			    MarkerOptions marker = new MarkerOptions().position(
//		                new LatLng(latitude,longitude)).title(name);
//			    System.out.println(latitude+" "+longitude);
//			    Toast.makeText(mapView.getContext(),latitude+" "+longitude, Toast.LENGTH_SHORT).show();
//			    marker.icon(BitmapDescriptorFactory
//		                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
//		
//		        // adding marker
//	        map.addMarker(marker);
//			} while (myCursor.moveToNext());
//		}
//		myCursor.close();
//		CameraPosition cameraPosition = new CameraPosition.Builder()
//        .target(new LatLng(latitude,longitude)).zoom(12).build();
//		map.animateCamera(CameraUpdateFactory
//        .newCameraPosition(cameraPosition));
//        return rootView;
//    }
	private  LatLng LOWER_MANHATTAN = new LatLng(23.729888, 90.376754);
	
	private static final LatLng BROOKLYN_BRIDGE = new LatLng(23.791789, 90.425162);
	private static LatLng WALL_STREET = new LatLng(23.745601, 90.382934);

	GoogleMap googleMap;
	final String TAG = "PathGoogleMapActivity";
	
	TextView tvDistanceDuration;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
          Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
//		View rootView = inflater.inflate(R.layout.mapview, container, false);
//     mapView=(MapView) rootView.findViewById(R.id.mapView);
     // mapView.onCreate(savedInstanceState);
//		setContentView(R.layout.mapview);
//		SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
//				.findFragmentById(R.id.mapView);
		View rootView = inflater.inflate(R.layout.mapview, container, false);
      MapView mapView = (MapView) rootView.findViewById(R.id.mapView);
      TextView timeShow= (TextView) rootView.findViewById(R.id.time);
      tvDistanceDuration = (TextView) rootView.findViewById(R.id.tv_distance_time);
     mapView.onCreate(savedInstanceState);
     Bundle bundle = getActivity().getIntent().getExtras();
   String Idname = bundle.getString("id"); 
   int id= Integer.parseInt(Idname);
     mapView.onResume();
      try {
         MapsInitializer.initialize(getActivity().getApplicationContext());
     } catch (Exception e) {
         e.printStackTrace();
     }
     //map = mapView.getMap();
		googleMap = mapView.getMap();

		MarkerOptions options = new MarkerOptions();
		options.position(LOWER_MANHATTAN);
		options.position(BROOKLYN_BRIDGE);
		options.position(WALL_STREET);
		//googleMap.addMarker(options);
		DBhelper dbOpenHelper = new DBhelper(this.getActivity(), DB_NAME);
      database = dbOpenHelper.openDataBase();
      Cursor myCursor;
      myCursor = dbOpenHelper.getOnlyBooth(id);
      myCursor.moveToFirst();
		if(!myCursor.isAfterLast()) {
			do {
			//	String id= myCursor.getString(0);
			String n1= myCursor.getString(4);
			String n2= myCursor.getString(5);
			String name= myCursor.getString(3);
			
			double latitude = Double.parseDouble(n1);
		    double longitude = Double.parseDouble(n2);
		    WALL_STREET = new LatLng(latitude, longitude);
		    
		    String dist = "";
            String dur = "";
		    Location locationA = new Location("point A");  

		    locationA.setLatitude(LOWER_MANHATTAN.latitude);  
		    locationA.setLongitude(LOWER_MANHATTAN.longitude);  

		    Location locationB = new Location("point B");  

		    locationB.setLatitude(WALL_STREET.latitude);  
		    locationB.setLongitude(WALL_STREET.longitude);  

		    float distance = locationA.distanceTo(locationB)/1000;
		    double duration = distance/.355;
		    Double dur2= distance/.0150;
		    int min,hr,sec,min2,hr2;
		    hr2= (int) (duration/60);
		    min2= (int) (duration - (hr2*60));
		    hr=(int) (dur2/60);
		    min=(int) (dur2-(60*hr));
		    dist=String.valueOf(distance);
		   // dist.format("%.2f");
		    dur=String.valueOf(duration);
		    //dur.format("%.2f");
		    MarkerOptions marker = new MarkerOptions().position(
	                new LatLng(latitude, longitude)).title(name);
		    marker.icon(BitmapDescriptorFactory
	                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
	
		    tvDistanceDuration.setText("Distance: "+dist + " km");// + "\nDuration: "+duration + "\tmin" );
	        timeShow.setText("Duration:\n\tBy Car: "+hr2+" hr\t"+min2+" min\n\tBy Foot: "+hr+" hr\t"+min+" min");
		    // adding marker
		    googleMap.addMarker(marker);
			
			
		} while (myCursor.moveToNext());
	}
	myCursor.close();
		String url = getMapsApiDirectionsUrl();
		ReadTask downloadTask = new ReadTask();
		downloadTask.execute(url);

//		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BROOKLYN_BRIDGE,
//				13));
		CameraPosition cameraPosition = new CameraPosition.Builder()
      .target(new LatLng(23.729888, 90.376754)).zoom(12).build();
googleMap.animateCamera(CameraUpdateFactory
      .newCameraPosition(cameraPosition));
		addMarkers();
		return rootView;

	}

	private String getMapsApiDirectionsUrl() {
		String waypoints = "waypoints=optimize:true|"
				+ LOWER_MANHATTAN.latitude + "," + LOWER_MANHATTAN.longitude
				+ "|" + "|" + WALL_STREET.latitude + ","
				+ WALL_STREET.longitude;

		String sensor = "sensor=false";
		String params = waypoints + "&" + sensor;
		String output = "json";
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + params;
		return url;
	}

	private void addMarkers() {
		if (googleMap != null) {
//			googleMap.addMarker(new MarkerOptions().position(BROOKLYN_BRIDGE)
//					.title("First Point"));
			googleMap.addMarker(new MarkerOptions().position(LOWER_MANHATTAN)
					.title("Your Location"));
//			googleMap.addMarker(new MarkerOptions().position(WALL_STREET)
//					.title("Third Point"));
		}
	}

	private class ReadTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... url) {
			String data = "";
			try {
				HttpConnection http = new HttpConnection();
				data = http.readUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			new ParserTask().execute(result);
		}
	}

	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				PathJSONParser parser = new PathJSONParser();
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;

			
			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);
					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				polyLineOptions.addAll(points);
				polyLineOptions.width(5);
				polyLineOptions.color(Color.GREEN);
			}

			googleMap.addPolyline(polyLineOptions);
		}
	}


}
