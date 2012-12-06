package com.fedorvlasov.lazylist;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class FormView extends Activity {

	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_form_view, menu);
		return true;
	}
	*/
	
	final String servletUrl = "http://cs-server.usc.edu:11104/examples/servlet/HelloWorldExample?";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_form_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_form_view, menu);
		return true;
	}
	public void onMovieClicked(View button)
	{
		final EditText nameField = (EditText) findViewById(R.id.EditMovieName);
		String name = nameField.getText().toString();
		final Spinner feedbackSpinner = (Spinner) findViewById(R.id.spinner1);
		String feedbackType = feedbackSpinner.getSelectedItem().toString();
		//System.out.println(name + "   " + feedbackType);
		
		if(name.equals(""))
		{
			 Toast.makeText(this,
                     "Sorry, the input cannot be empty",
                 Toast.LENGTH_SHORT).show();
			return;
		}
		
		
		String value;
		if(feedbackType.equals("All Type"))
		{
			value = "feature,tv_series,game";
		}
		else if(feedbackType.equals("Video Game"))
		{
			value = "game";
		}
		else if(feedbackType.equals("TV Series"))
		{
			value = "tv_series";
		}
		else {
			value = "feature";
		}
		
		String urlString = servletUrl + "title=" + name + "&mediaType=" + value;
		
	//	String urlString = "http://cs-server.usc.edu:11104/examples/servlet/HelloWorldExample?title=batman&mediaType=feature";
		
		Intent myIntent = new Intent(this, MainActivity.class);
		myIntent.putExtra("url",urlString);
		startActivity(myIntent);
		
	}

	

}
