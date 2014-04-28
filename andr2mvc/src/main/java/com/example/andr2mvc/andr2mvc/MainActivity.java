package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text=(TextView)findViewById(R.id.textView);

        Button b=(Button)this.findViewById(R.id.foto);
        b.setOnClickListener(this);

        img=(ImageView)this.findViewById(R.id.img);

        phoneNum=(Button)this.findViewById(R.id.cmdGetPhoneNumber);
        phoneNum.setOnClickListener(this);
    }
    ImageView img;
    TextView text;
    Button phoneNum;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    int GALLERY_PICTURE=0;
    static int CAMERA_PICTURE=1;
    private Uri imageUri;
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.foto){
            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
            //Toast.makeText(this, Environment.getExternalStorageDirectory().toString(),Toast.LENGTH_LONG).show();
            text.setText(Environment.getExternalStorageDirectory().toString());

            i.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(photo));
            imageUri = Uri.fromFile(photo);
            startActivityForResult(i, CAMERA_PICTURE);

        }
        else if(v.getId()==R.id.cmdGetPhoneNumber){

            TelephonyManager tMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            text.setText("Номер телефона-"+mPhoneNumber);
        }

    }
    public void Send_Simple_Detail_To_Server(String URL, List<NameValuePair> nameValuePairs)

    {
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(URL);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            InputStream is = entity.getContent();

        } catch (ConnectTimeoutException e) {
            Log.e("Timeout Exception: ", e.toString());

        } catch (SocketTimeoutException ste) {
            Log.e("Timeout Exception: ", ste.toString());

        } catch (Exception e) {
            Log.e("HTTP_Execption", "Error in http connection " + e.toString());

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== CAMERA_PICTURE)
                if (resultCode == Activity.RESULT_OK) {

                    Uri selectedImage = imageUri;

                    String s=imageUri.getPath();
                    File f=new File(s);
                    Log.d("Camera", "path-"+imageUri.getPath());
                    Log.d("Camera", "length-"+f.length());

                    img.setImageURI(imageUri);

                    byte[] buf= new byte[0];
                    try {
                        text.setText("try to read file-"+f.getPath());
                        //buf = IOUtil.readFile(f);
                        buf=IOUtil.getByteArrayFromImage(f.getPath());
                        text.setText("ok1");
                        String imgString = Base64.encodeToString(buf, Base64.NO_WRAP);

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                        nameValuePairs.add(new BasicNameValuePair("obj", imgString));

                        text.setText(f.getName());

                       // Send_Simple_Detail_To_Server("http://192.168.1.229/mvcapplication1/home/AddImage/",nameValuePairs);
                        //postData();
                        HttpPostTask t=new HttpPostTask();


                        //t.execute("http://192.168.1.229/mvcapplication1/home/AddImage",nameValuePairs);
                        t.execute("http://pichuginsergey.no-ip.biz:9980/mvcapplication1/home/AddImage",nameValuePairs);


                        Log.d("Camera", "base64-" + imgString);
                    } catch (IOException e) {
                        text.setText(e.getMessage());
                        e.printStackTrace();
                    }



//                    getContentResolver().notifyChange(selectedImage, null);
//                    //ImageView imageView = (ImageView) findViewById(R.id.ImageView);
//                    ContentResolver cr = getContentResolver();
//                    Bitmap bitmap;
//                    try {
//                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
//
//                        //imageView.setImageBitmap(bitmap);
//                        Toast.makeText(this, selectedImage.toString(),Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
//                                .show();
//                        Log.e("Camera", e.toString());
//                    }
                }
        }
    public void postData() {
        // Create a new HttpClient and Post Header
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://192.168.1.229/mvcapplication1/home/AddImage/obj=0");

        try {
            // Add your data
            //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            //nameValuePairs.add(new BasicNameValuePair("obj", "12345"));
            //nameValuePairs.add(new BasicNameValuePair("stringdata", "AndDev is Cool!"));
            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
        } catch (IOException e) {
            // TODO Auto-generated catch block
        }
    }

//    public void Image_Selecting_Task(Intent data) {
//        try {
//            Uri uri = data.getData();
//            if (uri != null) {
//                // User had pick an image.
//                Cursor cursor = getContentResolver().query(uri, new String[] {
//                        android.provider.MediaStore.Images.ImageColumns.DATA
//                }, null, null, null);
//                cursor.moveToFirst();
//                // Link to the image
//                final String imageFilePath = cursor.getString(0);
//
//                //Assign string path to File
//                File Default_DIR = new File(imageFilePath);
//
//                // Create new dir MY_IMAGES_DIR if not created and copy image into that dir and store that image path in valid_photo
//                Create_MY_IMAGES_DIR();
//
//                // Copy your image
//                Utility.copyFile(Utility.Default_DIR, Utility.MY_IMG_DIR);
//
//                // Get new image path and decode it
//                Bitmap b = Utility.decodeFile(Utility.Paste_Target_Location);
//
//                // use new copied path and use anywhere
//                String valid_photo = Utility.Paste_Target_Location.toString();
//                b = Bitmap.createScaledBitmap(b, 150, 150, true);
//
//                //set your selected image in image view
//                user_photo.setImageBitmap(b);
//                cursor.close();
//
//            } else {
//                Toast toast = Toast.makeText(this, "Sorry!!! You haven't selecet any image.", Toast.LENGTH_LONG);
//                toast.show();
//            }
//        } catch (Exception e) {
//            // you get this when you will not select any single image
//            Log.e("onActivityResult", "" + e);
//
//        }
//    }
}

