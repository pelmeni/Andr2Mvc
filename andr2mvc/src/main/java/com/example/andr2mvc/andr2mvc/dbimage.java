package com.example.andr2mvc.andr2mvc;

import android.graphics.Bitmap;

/**
 * Created by PichuginSI on 29.04.2014.
 */
public class dbimage {
    public dbimage(int id, Bitmap bytes){
        this.id=id;
        this.bytes=bytes;
    }
    public int id;
    public Bitmap bytes;
}
