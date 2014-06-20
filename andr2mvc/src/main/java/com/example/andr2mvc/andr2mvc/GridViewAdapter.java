package com.example.andr2mvc.andr2mvc;

import java.io.File;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class GridViewAdapter extends ArrayAdapter<ImageItem> {
	private Context context;
	private int layoutResourceId;
	private ArrayList<ImageItem> data = new ArrayList<ImageItem>();

	public GridViewAdapter(Context context, int layoutResourceId,
			ArrayList<ImageItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ViewHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ViewHolder();
			holder.imageTitle = (TextView) row.findViewById(R.id.text);
			holder.image = (ImageView) row.findViewById(R.id.image);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}

		ImageItem item = data.get(position);
		holder.imageTitle.setText(item.getTitle());

        holder.image.setImageBitmap(item.getImage());

        String filePath= Environment.getExternalStorageDirectory()+"/Andr2Mvc/" +  item.getId()+"_icon.jpg";

        File file = new File(filePath);

        if(file.exists())
        {
            Bitmap b=BitmapFactory.decodeFile(filePath);
            holder.image.setImageBitmap(b);
        }
        else {
            HttpGetTask_GetImageById2 t = new HttpGetTask_GetImageById2(holder.image, item.getId());
            t.execute("http://muscle-planet.ru:9980/MvcApplication1/Home/GetImageIcon?id=" + item.getId());
            t = null;
            System.gc();
        }
		return row;
	}



    static class ViewHolder {
		TextView imageTitle;
		ImageView image;
	}
}