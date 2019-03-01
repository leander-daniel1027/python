package com.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by ubuntu on 6/11/17.
 */

public class TabLayoutAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> listFrag=new ArrayList<>();
    private ArrayList<String> listTitle=new ArrayList<>();

    public TabLayoutAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment, String title){
        listFrag.add(fragment);
        listTitle.add(title);
    }

    @Override
    public Fragment getItem(int position) {


        return listFrag.get(position);
    }

    @Override
    public int getCount() {
        return listFrag.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }
}

