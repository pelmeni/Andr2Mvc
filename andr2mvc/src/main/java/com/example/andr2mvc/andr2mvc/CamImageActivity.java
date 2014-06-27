package com.example.andr2mvc.andr2mvc;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.net.URI;
import java.util.Locale;


public class CamImageActivity extends ActionBarActivity  {

    ImageView img;
    String latitude;
    String longtitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam_image);

        img=(ImageView)this.findViewById(R.id.img);

        Intent intent = getIntent();

        String imageUriStr=intent.getStringExtra("image");

        latitude=intent.getStringExtra("latitude");

        longtitude=intent.getStringExtra("longtitude");

        Uri imageUri= Uri.parse(imageUriStr);

        img.setImageURI(imageUri);

        img.setScaleType(ImageView.ScaleType.FIT_XY);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cam_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.location) {



                String uri = String.format(Locale.ENGLISH, "geo:%s,%s?z=%d",//&q=%f,%f (%s)
                        latitude, longtitude, 300);//, location.getLatitude(), location.getLongitude(), "label");

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            return true;
            }
        return super.onOptionsItemSelected(item);
    }


}
