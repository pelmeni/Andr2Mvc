package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpPostTask extends AsyncTask {

    public Activity activity;
    Context context;
    public AsyncTaskCompleteListener callback;
    ArrayList URL_Params = new ArrayList();
    //Function fun = new Function();
    ProgressDialog Loader_Dialog;
    //Custom_alert_DialogClass cad;

//    public Background_Task() {
//        this.URL_Params = URL_Params;
//
//        this.activity = act;
//        this.callback = (AsyncTaskCompleteListener) act;
//    }
    public void postData(String url,List<NameValuePair> nameValuePairs) {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url/*"http://192.168.1.229/mvcapplication1/home/AddImage/obj=0"*/);

        try {
            // Add your data
            //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //nameValuePairs.add(new BasicNameValuePair("obj", "12345"));
            //nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        }



       catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
        } catch (IOException e) {
//            // TODO Auto-generated catch block
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        postData(objects[0].toString(),(ArrayList)objects[1]);
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

//    @Override
//    protected void onPostExecute(String result) {
//        super.onPostExecute(result);
//        // check is dialog open ? THEN HIDE DIALOG
//        try {
//            Log.i("onPOST", "DONE");
//            Log.i("onPOST", "" + result);
//
//            callback.onTaskComplete(result);
//        } catch (Exception e) {
//            Log.e("onPost", "" + e);
//            // TODO: handle exception
//        }
//
//    }

}