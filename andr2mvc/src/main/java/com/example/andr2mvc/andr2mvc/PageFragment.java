package com.example.andr2mvc.andr2mvc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class PageFragment extends Fragment implements GenericAsyncTaskCompleteListener<Object,String> {

    dbimage image;

    public PageFragment () {

    }

//    public PageFragment (dbimage i) {
//        image=i;
//    }
//    public PageFragment (int i) {
//        image=new dbimage(i,null);
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
ImageView imageV;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment, null);
        //View view = inflater.inflate(R.layout.fragment, container, false);

        if(image!=null) {

            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            imageView.setImageBitmap(image.bytes);
        }

        TextView textView = (TextView) view.findViewById(R.id.text1);
        imageV=(ImageView)view.findViewById(R.id.image1);
        int pos=getArguments().getInt("pos");

        int[]ids=(int[])getArguments().getIntArray("array_img");

        String s=String.valueOf(ids[pos]);

        textView.setText(s);

        HttpGetTask_GetImageById t=new HttpGetTask_GetImageById(this, ids[pos]);
        t.execute("http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageThumb?id=" + s);
        t=null;
        System.gc();


        return view;
    }

    @Override
    public void onTaskComplete(String source, Object result) {
        if (source == "GetImageById") {

            dbimage img = (dbimage) result;

            if (result != null) {

                //DbImageProvider.Add(img);

                if(imageV!=null)
                {

                    Bitmap bm=BitmapFactory.decodeResource(getResources(),R.id.image1);

                    imageV.setImageBitmap(img.bytes);

                    if(bm!=null) {
                        bm.recycle();
                    }


                    Log.d("Loading image", "Loading image " + img.id);

                }
            }
        }
    }
}
