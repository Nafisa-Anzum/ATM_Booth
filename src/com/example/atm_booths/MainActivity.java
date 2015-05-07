package com.example.atm_booths;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;


public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar bar = getActionBar();
		bar.hide();
		/*bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#16a085")));*/
		Thread background = new Thread() {
            public void run() {
                 
                try {
                    // Thread will sleep for 5 seconds
                    sleep((long) (1.5*1000));
                 // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),UserBank.class);
                    //Intent i=new Intent(getBaseContext(),mapTry.class);
                    startActivity(i);
                    
                    
                    finish();
                     
                } catch (Exception e) {
                 
                }
            
	}
		};
		  background.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
