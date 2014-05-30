package com.example.andr2mvc.andr2mvc;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class GallaryActivity extends ActionBarActivity implements View.OnClickListener, GenericAsyncTaskCompleteListener<Object,String>
{

    ViewPager pager;

    PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallary);


        pager = (ViewPager) findViewById(R.id.pager);

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(pagerAdapter);

        new HttpGetTask_GetImagesIds(this).execute("http://muscle-planet.ru:9980/mvcapplication1/home/GetImagesIds");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gallary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTaskComplete(String source, Object result) {
        if (source == "GetImagesIds") {
            int[] ids = (int[]) result;

            for (int i = 0; i < ids.length; i++) {
                try {
                    Log.d("log","start image loading task for - "+"http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageThumb?id=" + ids[i]);
                    new HttpGetTask_GetImageById(this, i).execute("http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageThumb?id=" + ids[i]);
                } catch (Exception ex) {
                    Log.d("log",ex.toString());
                }

            }
//            if (DbImageProvider.Count() > 0) {
//                PageFragment pf = getFreeFragment();
//
//                if (pf != null) {
//                    ImageView i = (ImageView) pf.getView().findViewById(R.id.image);
//
//                    dbimage img=DbImageProvider.GetList().get(0);
//
//                    if(img!=null)
//                        i.setImageBitmap( img.bytes);
//                }
//
//            }
        }
        else if (source == "GetImageById") {

            dbimage img = (dbimage) result;

            if (result != null) {

                Boolean isFirst=DbImageProvider.Count()==0;

                DbImageProvider.Add(img);

                ArrayList<dbimage> list = DbImageProvider.GetList();

                ((MyFragmentPagerAdapter) pagerAdapter).setImagesIds(list);

                if(isFirst)
                {

                    //pager.setCurrentItem(0,true );

                    //android.app.Fragment f = getFragmentManager().findFragmentById(R.id.RelativeLayout1);
                    //Fragment f=new PageFragment(img);
                    //ImageView i= (ImageView)f.getView().findViewById(R.id.image);
                    //i.setImageBitmap(img.bytes);
                    //FragmentTransaction ft = getFragmentManager().beginTransaction();
                }
                //PageFragment pf=getFreeFragment();

                //if(pf!=null){
                //    ImageView i=(ImageView)pf.getView().findViewById(R.id.image);

                //i.setImageBitmap(img.bytes);
                //}





                Log.d("Loading image", "Loading image " + img.id);

            }
        }
    }

    @Override
    public void onClick(View view) {

    }
}
