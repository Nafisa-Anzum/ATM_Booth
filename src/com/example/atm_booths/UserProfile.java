package com.example.atm_booths;

import java.util.ArrayList;

import com.example.atm_boots.adapter.UserBankAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class UserProfile extends Activity{
	
	ListView lv;
    Context context;
    UserBankAdapter userBankAdapter;
    ArrayAdapter<String> adapter;
    
    public static String [] bankNameList={"DBBL","AB Bank","Trust Bank","BRAC Bank Limited", "IFIC Bank Limited","Mutual Trust Bank Limited"};
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        lv=(ListView) findViewById(R.id.userBankList);
        context=this; 
        userBankAdapter=new UserBankAdapter(this, bankNameList);
        lv.setAdapter(userBankAdapter);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
		 MenuInflater inflater = getMenuInflater();
    	 inflater.inflate(R.menu.user_profile, menu);
    	 return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
                
            case R.id.action_edit:
            	Intent i = new Intent(this, EditProfile.class);
        		startActivity(i);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
