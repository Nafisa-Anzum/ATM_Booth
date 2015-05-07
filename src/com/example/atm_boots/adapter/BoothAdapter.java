package com.example.atm_boots.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.atm_booths.BoothInfoPage;
import com.example.atm_booths.PlaceActivity;
import com.example.atm_booths.R;
import com.example.atm_booths.mapView;
public class BoothAdapter extends BaseAdapter{ 
	ArrayList<String>result;
    Context context;
    
 int [] viewId;
      private static LayoutInflater inflater=null;
    public BoothAdapter(PlaceActivity mainActivity, ArrayList<String> prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public BoothAdapter(mapView mainActivity, ArrayList<String> prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity.getActivity();
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    /*CityAdapter(CityActivity mainActivity, ArrayList<String> text)
    { 
    
	     result = new String[text.size()];
	     context=mainActivity;
	     for(int i=0;i<text.size();i++)
	     {
	    	 result[i] = text.get(i);
	     }

    }*/

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.size();
    }
 
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }
 
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
 
    public class Holder
    {
        TextView text;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;        
             rowView = inflater.inflate(R.layout.booth_list, null);
             holder.text=(TextView) rowView.findViewById(R.id.textView1);
            
         holder.text.setText(result.get(position));
         rowView.setOnClickListener(new OnClickListener() {         
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	
            	Intent i = new Intent(context, BoothInfoPage.class);
            	Bundle bundle = new Bundle();
            	  //Add your data to bundle
            	  bundle.putString("city", result.get(position));  
            	  //Add the bundle to the intent
            	  i.putExtras(bundle);
            	context.startActivity(i);
            	
            }
        });  
        return rowView;
    }
 
}
