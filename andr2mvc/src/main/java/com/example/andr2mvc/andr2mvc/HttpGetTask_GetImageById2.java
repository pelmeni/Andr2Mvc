package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class HttpGetTask_GetImageById2 extends AsyncTask<String,Void,dbimage> {

    public Activity activity;

    int imageId;

    ImageView image;

    private final Lock lock = new ReentrantLock();

    public HttpGetTask_GetImageById2(ImageView image, int id)
    {
        this.imageId=id;
        this.image = image;
    }
    String GetData(String url) {
    // Create a new HttpClient and Post Header
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

        return total.toString();
    }

    catch (ClientProtocolException e) {
        Log.e("http_get_image_by_id",e.getMessage());
    } catch (IOException e) {
        Log.e("http_get_image_by_id",e.getMessage());
    }
        catch (Exception ex) {
            Log.e("http_get_image_by_id",ex.getMessage());
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

    protected void onPostExecute(dbimage result) {

        if(result!=null) {
            String filePath= Environment.getExternalStorageDirectory()+"/Andr2Mvc/" +  result.id+"_icon.jpg";
            File dir= new File( Environment.getExternalStorageDirectory()+"/Andr2Mvc/");

            if(!dir.exists())
                dir.mkdir();

            File file = new File(filePath);

            if(!file.exists())
            {
                IOUtil.StoreImage(result.bytes,"Andr2Mvc",result.id+"_icon.jpg");
            }
            Bitmap b=BitmapFactory.decodeFile(filePath);
            this.image.setImageBitmap(b);





        }
    }

    @Override
    protected dbimage doInBackground(String... urls) {

        //if(lock.tryLock()){
            try {
                String result = GetData(urls[0]);

                byte[] buf = Base64.decode(result, Base64.NO_WRAP);

                Bitmap bm = BitmapFactory.decodeByteArray(buf, 0, buf.length);



                buf=null;

                return new dbimage(imageId, bm);
            }
            catch (Exception ex){
                if(ex!=null)
                Log.d("HttpGetTask_GetImageById",ex.getMessage());
            }
            finally{
                //lock.unlock();
            }
        //}
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