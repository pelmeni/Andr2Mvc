package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpGetTask_GetQueryRegistrationResult extends AsyncTask {

    private final Lock lock = new ReentrantLock();

    AsyncTaskCompleteListener callback;

    public HttpGetTask_GetQueryRegistrationResult(AsyncTaskCompleteListener callback)
    {
        this.callback=callback;

    }

    @Override
    protected Object doInBackground(Object[] params) {

        List<NameValuePair> pairs=(List<NameValuePair>)params[1];

        String paramString = URLEncodedUtils.format(pairs, "utf-8");

        HttpGet httpget = new HttpGet(params[0].toString()+"?"+paramString);

     try {
        HttpClient httpclient = new DefaultHttpClient();

        HttpResponse response = httpclient.execute(httpget);

         HttpEntity entity = response.getEntity();

         BufferedReader r = new BufferedReader(new InputStreamReader(entity.getContent()));

         StringBuilder ret = new StringBuilder();

         String line = null;

         while ((line = r.readLine()) != null) {
             ret.append(line);
         }

         if(callback!=null)
             callback.onTaskComplete(ret.toString());

         return ret.toString();
    }
    catch (Exception ex){
        try {
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        return null;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {

            Log.i("onPre", "call");
        } catch (Exception e) {
            // TODO: handle exception
            Log.e("onPre", "" + e);
        }

    }



}