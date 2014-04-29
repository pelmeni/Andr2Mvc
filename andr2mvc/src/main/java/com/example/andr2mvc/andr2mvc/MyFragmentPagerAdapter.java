package com.example.andr2mvc.andr2mvc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by PichuginSI on 29.04.2014.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public void setImagesIds(ArrayList<dbimage> ids)
    {
        //bag.putIntArray("images_ids",ids);

        mCount =ids == null ? 0 : ids.size();

        super.notifyDataSetChanged();
    }

    private int mCount=4;

    @Override
    public Fragment getItem(int position) {
        dbimage i=DbImageProvider.GetById(position);
        if(i!=null)
            return new PageFragment(i);
        else return  new PageFragment();
    }

    @Override
    public int getCount() {
        return mCount;
    }

}
