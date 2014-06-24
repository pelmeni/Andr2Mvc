package com.example.andr2mvc.andr2mvc;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import java.util.Locale;

/**
 * Created by PichuginSI on 23.06.2014.
 */
public class GPSLocationListener implements LocationListener {

    Location location;
    Context ctx;
    public GPSLocationListener(Context ctx){
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000*10, 10, this);

        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000*10, 10, this);

        this.ctx=ctx;
    }
    public Location getLocation(){
        return location;
    }
    @Override
    public void onLocationChanged(Location location) {
        this.location=location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void startMapsIntent(){
        if (location != null) {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f?z=%d",//&q=%f,%f (%s)
                    location.getLatitude(), location.getLongitude(), 300);//, location.getLatitude(), location.getLongitude(), "label");

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

            if (intent.resolveActivity(ctx.getPackageManager()) != null) {
                ctx.startActivity(intent);
            }
        }
    }
}
