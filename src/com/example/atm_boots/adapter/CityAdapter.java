package com.example.atm_boots.adapter;
import java.util.ArrayList;

import com.example.atm_booths.CityActivity;
import com.example.atm_booths.PlaceActivity;
import com.example.atm_booths.R;
import com.example.atm_booths.R.id;
import com.example.atm_booths.R.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class CityAdapter extends BaseAdapter{ 
	ArrayList<String>result;
	ArrayList<Integer>boothcount;
    Context context;
    String bank;
 int [] viewId;
      private static LayoutInflater inflater=null;
    public CityAdapter(CityActivity mainActivity, ArrayList<String> prgmNameList, String b,ArrayList<Integer>cnt) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity;
        bank=b;
        boothcount=cnt;
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
        TextView count;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;        
             rowView = inflater.inflate(R.layout.location, null);
             holder.text=(TextView) rowView.findViewById(R.id.textView1);
             holder.count=(TextView) rowView.findViewById(R.id.item_counter);
         
         
         holder.count.setText(boothcount.get(position).toString());
         holder.text.setText(result.get(position));
         rowView.setOnClickListener(new OnClickListener() {         
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            	
            	Intent i = new Intent(context, PlaceActivity.class);
            	Bundle bundle = new Bundle();
            	  //Add your data to bundle
            	  bundle.putString("city", result.get(position)); 
            	  bundle.putString("bank", bank); 
            	  bundle.putString("place", null); 
            	  i.putExtras(bundle);
            	context.startActivity(i);
            }
        });  
        return rowView;
    }
 
}