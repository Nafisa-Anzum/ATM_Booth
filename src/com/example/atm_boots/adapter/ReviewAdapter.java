package com.example.atm_boots.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.atm_booths.R;
import com.example.atm_booths.ReviewFragment;

public class ReviewAdapter extends BaseAdapter{
	String [] result;
	String [] user;
    Context context;
    int [] imageId;
    private static LayoutInflater inflater=null;
    public ReviewAdapter(ReviewFragment mainActivity, String[] prgmNameList, String[] userNameList, int[] userImage) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        user=userNameList;
        imageId=userImage;
        context=mainActivity.getActivity();
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
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
        TextView user;
        ImageView img;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;        
             rowView = inflater.inflate(R.layout.review_list, null);
             holder.user=(TextView) rowView.findViewById(R.id.userName);
             holder.tv=(TextView) rowView.findViewById(R.id.textView1);
             holder.img=(ImageView) rowView.findViewById(R.id.imageView1);       
             holder.user.setText(user[position]);
             holder.tv.setText(result[position]);
             holder.img.setImageResource(imageId[position]);         
         
        return rowView;
    }
}
