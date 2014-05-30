package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpGetTask_GetImagesIds extends AsyncTask<String,Void,String> {

    GenericAsyncTaskCompleteListener<Object,String> callback;

    public HttpGetTask_GetImagesIds(GenericAsyncTaskCompleteListener<Object,String> callback)
    {

        this.callback = callback;
    }

    public String GetData(String url)
    {
        HttpClient httpclient = new DefaultHttpClient();

        HttpGet httpget=new HttpGet(url);

    try {

        HttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();

        BufferedReader r = new BufferedReader(new InputStreamReader(entity.getContent()));

        StringBuilder total = new StringBuilder();

        String line = null;

        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        //is.close();

        return total.toString();
    }

    catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
    } catch (IOException e) {
//            // TODO Auto-generated catch block
    }
        return null;
}


    protected void onPostExecute(String result) {

        if(result!=null) {
            String[] l = TextUtils.split(result.toString(), ",");

            int[] convert = ArrayUtil.convert(l);

            callback.onTaskComplete("GetImagesIds", convert);
        }
    }

    @Override
    protected String doInBackground(String... urls) {
        return GetData(urls[0]);
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
//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        // check is dialog open ? THEN HIDE DIALOG
//        try {
//            Log.i("onPOST", "DONE");
//            Log.i("onPOST", "" + result);
//
//            callback.onTaskComplete(result);
//
//        } catch (Exception e) {
//            Log.e("onPost", "" + e);
//            // TODO: handle exception
//        }
//
//    }
//    @Override
//    protected String doInBackground(String... web_url) {
//        try {
//            // this will send req in post
//            // here [0] mean URL & passing params
//            Log.i("onDO", "call");
//
//            fun.Send_Simple_Detail_To_Server(web_url[0], URL_Params);
//
//            Log.i("onDO", "SEND");
//
//            // this will get stream response
//            fun.Buffer_Response();
//            Log.i("onDO", "GET RES");
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            Log.e("onDo", "" + e);
//        }
//
//        return fun.Buffer_String_Response;
//    }



}