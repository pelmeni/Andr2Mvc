package com.example.andr2mvc.andr2mvc;

import android.graphics.Bitmap;

/**
 * @author javatechig {@link http://javatechig.com}
 * 
 */
public class ImageItem {
	private Bitmap image;
	private String title;
    private int id;

	public ImageItem(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
    public ImageItem(Bitmap image, String title) {
        super();
        this.image = image;
        this.title = title;
    }
	public Bitmap getImage() {

        return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public int getId(){
        return id;
    }
}
