package com.example.andr2mvc.andr2mvc;

/**
 * Created by pichugin on 26.04.2014.
 */
public interface AsyncTaskCompleteListener {
    // it will call when background process finish
    public void onTaskComplete(String result);
}
