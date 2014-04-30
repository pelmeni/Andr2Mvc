package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class HttpGetTask extends AsyncTask<String,Void,Bitmap> {

    public Activity activity;



    ImageView _img;


    public HttpGetTask(ImageView img)
    {
        //this.activity = act;
        //this.callback = (AsyncTaskCompleteListener) act;

        _img=img;
    }
    public String GetData(String url) {
    // Create a new HttpClient and Post Header
    HttpClient httpclient = new DefaultHttpClient();
    HttpPost httppost = new HttpPost(url);
    HttpGet httpget=new HttpGet(url);

    try {

        HttpResponse response = httpclient.execute(httpget);

        //String responseString = new BasicResponseHandler().handleResponse(response);

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

    protected void onPostExecute(Bitmap result) {
        _img.setImageBitmap(result);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String result= GetData(urls[0]);

        byte[] buf= Base64.decode(result, Base64.NO_WRAP);

        Bitmap bm = BitmapFactory.decodeByteArray(buf, 0,buf.length);

//        try {
//            InputStream in = new java.net.URL(urldisplay).openStream();
//            mIcon11 = BitmapFactory.decodeStream(in);
//        } catch (Exception e) {
//            Log.e("Error", e.getMessage());
//            e.printStackTrace();
//        }
        return bm;
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