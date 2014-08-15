package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.widget.AdapterView.*;


public class tiled_gallery extends Activity implements GenericAsyncTaskCompleteListener<Object,String> {

    private GridView gridView;
    private GridViewAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_tiled);


        HttpGetTask_GetImagesIds t=new HttpGetTask_GetImagesIds(this);
        //t.execute("http://muscle-planet.ru:9980/mvcapplication1/home/GetImagesIds");
        t.execute(DbSettings.get_ApplicationUrl(getApplicationContext())+ "GetImagesIds");
        t=null;


    }
//    OnItemClickListener myOnItemClickListener
//            = new OnItemClickListener(){
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            String prompt = (String)parent.getItemAtPosition(position);
//            Toast.makeText(getApplicationContext(),
//                    prompt,
//                    Toast.LENGTH_LONG).show();
//
//        }};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tiled_gallery, menu);
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

            final ArrayList<ImageItem> imageItems = new ArrayList<ImageItem>();

            for (int i = 0; i < ids.length; i++) {
//                try {
//                    Log.d("log","start image loading task for - "+"http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageThumb?id=" + ids[i]);
//                    new HttpGetTask_GetImageById(this, i).execute("http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageThumb?id=" + ids[i]);
//                } catch (Exception ex) {
//                    Log.d("log",ex.toString());
//                }
                imageItems.add(new ImageItem(ids[i], "Image#" + ids[i]));
            }
            gridView = (GridView) findViewById(R.id.gridView);

            customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, imageItems);

            gridView.setAdapter(customGridAdapter);

            //gridView.setOnItemClickListener(myOnItemClickListener);

            //((MyIntFragmentPagerAdapter) pagerAdapter).setImagesIds(ids);


            Log.d("log", "Loading images count =" + ids.length);

            //pager.setCurrentItem(0);

            //HttpGetTask_GetImageById t=new HttpGetTask_GetImageById(this, ids[0]);
            //t.execute("http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageThumb?id=" + ids[0]);
            //t=null;
            //System.gc();

            return;

        }
    }



}
