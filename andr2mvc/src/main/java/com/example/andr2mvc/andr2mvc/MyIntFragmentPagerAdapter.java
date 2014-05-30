package com.example.andr2mvc.andr2mvc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyIntFragmentPagerAdapter extends FragmentStatePagerAdapter {


        public MyIntFragmentPagerAdapter (FragmentManager fm) {
            super(fm);

        }

        public void setImagesIds(int[] ids)
        {

            mCount =ids == null ? 0 : ids.length;

            super.notifyDataSetChanged();

        }

        private int mCount=4;

        @Override
        public Fragment getItem(int position) {
            //dbimage i=DbImageProvider.GetById(position);
            //if(i!=null)
                PageFragment pf= new PageFragment();

            Bundle b=new Bundle();
            b.putInt("pos",position);
            pf.setArguments(b);
            return pf;
            //else return  new PageFragment();
        }

        @Override
        public int getCount() {
            return mCount;
        }

    }


