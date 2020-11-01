package com.rs.doorbell.AdapterUtil;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rs.doorbell.ObjectUtil.PagerTabObject;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class HomeTabPager extends FragmentStatePagerAdapter {
    private ArrayList<PagerTabObject> fragmentArrayList = new ArrayList<>();

    public HomeTabPager(FragmentManager fm, ArrayList<PagerTabObject> fragmentArrayList) {
        super(fm);
        this.fragmentArrayList = fragmentArrayList;
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentArrayList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentArrayList.get(position).getTitle();
    }


}

