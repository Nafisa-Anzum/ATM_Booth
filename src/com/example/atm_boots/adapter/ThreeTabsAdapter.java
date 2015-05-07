package com.example.atm_boots.adapter;
import com.example.atm_booths.ReviewFragment;
import com.example.atm_booths.SecurityFragment;
import com.example.atm_booths.boothDetailsFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
 
public class ThreeTabsAdapter extends FragmentPagerAdapter {
 
    public ThreeTabsAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new boothDetailsFragment();
        case 1:
            // Top Rated fragment activity
            return new ReviewFragment();
        case 2:
            // Games fragment activity
            return new SecurityFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}
