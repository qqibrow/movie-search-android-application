package com.fedorvlasov.lazylist;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class MovieDetails {
	private String title;
	private String imageUrl;
	private String year;
	private String director;
	private String rating;
	private String detailURL;
	private Bitmap image;
	
	public MovieDetails(String image, String title, String year, String director, String rating, String detailURL, Bitmap im)
	{
		this.title = title;
		this.imageUrl = image;
		this.year = director;
		this.rating = rating;
		this.director = director;
		this.detailURL = detailURL;
		this.image = im;
	}
	
	String getTitle() {
		return title;
	}
	void setTitle(String title) {
		this.title = title;
	}
	String getImageUrl() {
		return imageUrl;
	}
	void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	String getYear() {
		return year;
	}
	void setYear(String year) {
		this.year = year;
	}
	String getDirector() {
		return director;
	}
	void setDirector(String director) {
		this.director = director;
	}
	String getRating() {
		return rating;
	}
	void setRating(String rating) {
		this.rating = rating;
	}
	String getDetailURL() {
		return detailURL;
	}
	void setDetailURL(String detailURL) {
		this.detailURL = detailURL;
	}
	
	Bitmap getImage()
	{
		return image;
	}
	
}
