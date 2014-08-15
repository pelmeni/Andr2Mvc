package com.example.andr2mvc.andr2mvc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by pichuginsi on 13.08.2014.
 */
public class DbSettings {
    public static String getPropertyValue(Context ctx,String name){

        PropertiesDbAdapter dba=new PropertiesDbAdapter(ctx);

        dba.open();

        String retVal= dba.getPropertyValue(name);

        dba.close();

        return retVal;

    }
    public static String get_ApplicationUrl(Context ctx){
        return getPropertyValue(ctx,"ApplicationUrl");
    }
}
