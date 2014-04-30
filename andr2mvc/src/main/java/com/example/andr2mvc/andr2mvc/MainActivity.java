package com.example.andr2mvc.andr2mvc;
//test checkin 1
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
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
import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, GenericAsyncTaskCompleteListener<Object,String> {


    static final String TAG = "myLogs";
    //static final int PAGE_COUNT = 10;

    ViewPager pager;

    PagerAdapter pagerAdapter;

    Bundle bag=new Bundle();

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

        getFromSrv=(Button)this.findViewById(R.id.GetFromSrv);

        getFromSrv.setOnClickListener(this);

        pager = (ViewPager) findViewById(R.id.pager);

        new HttpGetTask_GetImagesIds(this).execute("http://muscle-planet.ru:9980/mvcapplication1/home/GetImagesIds");

        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());



        pager.setAdapter(pagerAdapter);

        pager.setOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        Log.d(TAG, "onPageSelected, position = " + position);
                    }

                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
    }


    ImageView img;

    TextView text;

    Button phoneNum;

    Button getFromSrv;

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
        else if(v.getId()==R.id.GetFromSrv){
            new HttpGetTask(img).execute("http://muscle-planet.ru:9980/MvcApplication1/Home/GetImage?id=8");
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



    @Override
    public void onTaskComplete(String source, Object result) {
        if (source == "GetImagesIds") {
            int[] ids = (int[]) result;

            for (int i = 0; i < ids.length; i++) {
                try {
                    new HttpGetTask_GetImageById(this, i).execute("http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageThumb?id=" + ids[i]);
                } catch (Exception ex) {
                }

            }
        }
        else if (source == "GetImageById") {

            dbimage img = (dbimage) result;

            if (result != null) {

                Boolean isFirst=DbImageProvider.Count()==0;

                DbImageProvider.Add(img);

                ArrayList<dbimage> list = DbImageProvider.GetList();

                ((MyFragmentPagerAdapter) pagerAdapter).setImagesIds(list);

                if(isFirst)
                {

                    //pager.setCurrentItem(0,true );

                    //android.app.Fragment f = getFragmentManager().findFragmentById(R.id.RelativeLayout1);
                    //Fragment f=new PageFragment(img);
                    //ImageView i= (ImageView)f.getView().findViewById(R.id.image);
                    //i.setImageBitmap(img.bytes);
                    //FragmentTransaction ft = getFragmentManager().beginTransaction();
                }
                PageFragment pf=getFreeFragment();

                if(pf!=null){
                    ImageView i=(ImageView)pf.getView().findViewById(R.id.image);

                    i.setImageBitmap(img.bytes);
                }





                Log.d("Loading image", "Loading image " + img.id);

            }
        }
    }

    PageFragment getFreeFragment(){

        for(int i=0;i<getSupportFragmentManager().getFragments().size();i++)
        {
            PageFragment pf=(PageFragment)getSupportFragmentManager().getFragments().get(0);
            if(pf.image==null)
                return pf;
        }
        return null;
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

