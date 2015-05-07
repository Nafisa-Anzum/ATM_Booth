package com.example.atm_boots.adapter;

import java.util.ArrayList;

import com.example.atm_booths.BankChoose;
import com.example.atm_booths.CharacterDrawable;
import com.example.atm_booths.R;
import com.example.atm_booths.R.color;
import com.example.atm_booths.R.id;
import com.example.atm_booths.R.layout;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class CustomAdapter extends BaseAdapter{ 
	ArrayList<String> result;
    Context context;
    private final int[] bgColors = new int[] { R.color.Chambray, R.color.CHESTNUTROSE, R.color.WISTERIA, R.color.SANDSTORM};
      private static LayoutInflater inflater=null;
    public CustomAdapter(BankChoose mainActivity, ArrayList prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        context=mainActivity.getActivity();
         inflater = ( LayoutInflater )context.
                 getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
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
        TextView tv;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;        
             rowView = inflater.inflate(R.layout.program_list, null);
             holder.tv=(TextView) rowView.findViewById(R.id.textView1);
             holder.img=(ImageView) rowView.findViewById(R.id.imageView1);       
         holder.tv.setText(result.get(position));
         CharacterDrawable drawable = new CharacterDrawable(result.get(position).charAt(0),0xFF16a085);
         holder.img.setImageDrawable(drawable);          
         
        return rowView;
    }
 
}
