package com.example.andr2mvc.andr2mvc;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class PageFragment extends Fragment {

    dbimage image;

    public PageFragment () {

    }

    public PageFragment (dbimage i) {
        image=i;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //View view = inflater.inflate(R.layout.fragment, null);
        View view = inflater.inflate(R.layout.fragment, container, false);

        if(image!=null) {

            ImageView imageView = (ImageView) view.findViewById(R.id.image);

            imageView.setImageBitmap(image.bytes);
        }
        return view;
    }
}
