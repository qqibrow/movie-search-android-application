package com.fedorvlasov.lazylist;

import com.fedorvlasov.*;
import com.fedorvlasov.lazylist.SessionEvents.AuthListener;
import com.fedorvlasov.lazylist.SessionEvents.LogoutListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes.Name;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.android.Facebook;
import com.facebook.widget.*;
import com.facebook.widget.WebDialog.OnCompleteListener;

public class MainActivity extends FacebookActivity  {
    
    private static final int DIALOG_ALERT = 0;
	ListView list;
    LazyAdapter adapter;  
    String url = "http://cs-server.usc.edu:11104/examples/servlet/HelloWorldExample?title=batman&mediaType=feature";
	Context context;
	ArrayList<MovieDetails> movies;
	ArrayList<Map<String, Object>> maplist;
    int currentItem;
	boolean isLogined = false;
	
    //facebook   
    public static final String APP_ID = "298559216912822";
//    Facebook facebook = new Facebook("298559216912822");
  //  AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
	//Session.StatusCallback statusCallback;
    
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        context = this;
        movies = fetchMovieFromUrl(url);
        list=(ListView)findViewById(R.id.list);
     //   statusCallback = new SessionStatusCallback();
        
        adapter=new LazyAdapter(this, movies);
        list.setAdapter(adapter);
   
        list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			//	System.out.println("hello");
				currentItem = (int)arg3;
				 showDialog(DIALOG_ALERT);
				
        Button b=(Button)findViewById(R.id.button1);
        b.setOnClickListener(listener);
    }
        });
     //   this.openSession();
        
        
        
        /*
        //init facebook
     // Get the active Session, if it exists
        Session session = Session.getActiveSession();
        // If no active session is found, check if you can
        // find a cached session.
        if (session == null) {
            if (savedInstanceState != null) {
                // Restore any session saved in the state
                session = Session.restoreSession(this, 
                              null, statusCallback, savedInstanceState);
            }   
            // Check if there was any session restored
            if (session == null) {
                // Instantiate a new Session 
                session = new Session(this);
            }   
            // Set the active session
            Session.setActiveSession(session);
            // Check if a cached token is available
            if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
                // If a cached token found, open the session
                // and call the session state changed callback.
                // The opened session will have the same permissions
                // that it was cached with.
                session.openForRead(new Session.OpenRequest(this)
                                               .setCallback(statusCallback));
                
               
            }   
        }
        onClickFacebookLogin();
        */
    }
    
    
    public void login()
    {
    	this.openSession();
    }
    
    
    @Override
    protected void onSessionStateChange(SessionState state, Exception exception) {
      // user has either logged in or not ...
      if (state.isOpened()) {
        // make request to the /me API
//        Request request = Request.newMeRequest(
//          this.getSession(),
//          new Request.GraphUserCallback() {
//            // callback after Graph API response with user object
//            @Override
//            public void onCompleted(GraphUser user, Response response) {
//              if (user != null) {
//                TextView welcome = (TextView) findViewById(R.id.welcome);
//                welcome.setText("Hello " + user.getName() + "!");
//              }
//            }
//          }
//        );
//        Request.executeBatchAsync(request);
      }
    }
    
    /*
    private class SessionStatusCallback implements Session.StatusCallback {
        @Override
        public void call(Session session, SessionState state, Exception exception) {
            onSessionStateChange( state,  exception);
        }
    }
 */
//    protected void onSessionStateChange(SessionState state, Exception exception) {
//    	if (state.isOpened()) {
//            System.out.println("Hello from session open");
//        } else if (state.isClosed()) {
//        	 System.out.println("Hello from session close");
//        }
//    	
//    	
//    }
    
 /*   
    protected Dialog onCreateDialog(int id){
    	
    	MovieDetails movie = movies.get(currentItem);
    	Builder builder = new AlertDialog.Builder(context);
    	builder.setView(R.layout.detail);
	
    	return builder.create();
    	
    }
  */
			
		@Override
		protected Dialog onCreateDialog(int id) {
			MovieDetails movie = movies.get(currentItem);
		  switch (id) {
		    case DIALOG_ALERT:
		    // Create out AlterDialog
		    	
	    	LayoutInflater inflater = getLayoutInflater();
	    	View dialoglayout = inflater.inflate(R.layout.detail, null);
	    	
	    	Button postButton = (Button)dialoglayout.findViewById(R.id.postButton);
	    	TextView nameText = (TextView)dialoglayout.findViewById(R.id.name);
	    	ImageView imgView = (ImageView)dialoglayout.findViewById(R.id.detailImage);
	    	
	    	
	    	
	    	
	    	
	    	adapter.imageLoader.DisplayImage(movie.getImageUrl(), imgView);
	    	nameText.setText(movie.getTitle());
	    	postButton.setOnClickListener(postfacebooklistener);
	    	
	    	
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setView(dialoglayout);
	    	
		//    builder.setMessage("This will end the activity");
		//    builder.setCancelable(true);
		 //   builder.setPositiveButton("I agree", new OkOnClickListener());
		    builder.setNegativeButton("No, no", new CancelOnClickListener());
		   // AlertDialog dialog = builder.create();
		  //  dialog.show();
	    	return builder.show();
		   }
		  return null;
		//  return super.onCreateDialog(id);
		}

		private final class CancelOnClickListener implements
		  DialogInterface.OnClickListener {
		  public void onClick(DialogInterface dialog, int which) {
		  Toast.makeText(getApplicationContext(), "Activity will continue",
		    Toast.LENGTH_LONG).show();
		  }
		}
		/*
		private final class OkOnClickListener implements
		    DialogInterface.OnClickListener {
		  public void onClick(DialogInterface dialog, int which) {
			  MainActivity.this.finish();
		  }
		} 		
	*/		
    private ArrayList<MovieDetails> fetchMovieFromUrl(String url)
    {
  	  
  	  ArrayList<MovieDetails> movies = new ArrayList<MovieDetails>();
  	  String jsonString = "";
  	  
  	  try {
            URL twitter = new URL(url);
            URLConnection tc = twitter.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    tc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
          	  jsonString = jsonString + line;
            }
            JSONObject  resultsObj = new JSONObject (jsonString);
            JSONArray jsonArray = resultsObj.getJSONObject("results").getJSONArray("result");
            System.out.println("Number of entries " + jsonArray.length());
          
            for (int i = 0; i < jsonArray.length(); i++) {
              JSONObject jsonObject = jsonArray.getJSONObject(i);
              System.out.println(jsonObject.getString("year"));
              
              
              // get the image
         //     InputStream is = fetch(jsonObject.getString("cover"));
         //     Drawable imgDrawable = Drawable.createFromStream(is, "src");
           //   Bitmap bitmap = getImageBitmap(jsonObject.getString("cover"));
              
              movies.add(new MovieDetails(jsonObject.getString("cover"), jsonObject.getString("title"), 
              		jsonObject.getString("year"), jsonObject.getString("director"), 
              		jsonObject.getString("rating"), jsonObject.getString("details"), null));
            }
            
        } catch (MalformedURLException e) {
           
            e.printStackTrace();
        } catch (IOException e) {
           
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }
  	  
  	  
  	  return movies;
    }
    @Override
    public void onDestroy()
    {
        list.setAdapter(null);
        super.onDestroy();
    }
    
    public OnClickListener listener=new OnClickListener(){
        @Override
        public void onClick(View arg0) {
            adapter.imageLoader.clearCache();
            adapter.notifyDataSetChanged();
        }
    };
    
    public OnClickListener postfacebooklistener =new OnClickListener(){
        @Override
        public void onClick(View arg0) {
        	// post to facebook
        	
        if(!isLogined)
        {
        	login();
        	isLogined = true;
        }      	
    	publishFeedDialog();         
        }
    };
 
    private void publishFeedDialog() {
    	// Session.openActiveSession(this, true, statusCallback);
    	Session session = Session.getActiveSession();
    	if(session.isClosed())
    	{
    		System.out.println("session is closed");
    	}
    	
    	MovieDetails movie = movies.get(currentItem);
        Bundle params = new Bundle();
        params.putString("name", movie.getTitle());
        params.putString("caption", "I am intertested in this movie/series/game");
        params.putString("description", movie.getTitle() +" released in " + movie.getYear() +" has a rating of " + movie.getRating());
        params.putString("link",movie.getDetailURL());
        params.putString("picture", movie.getImageUrl());
        String jsonstr = "{\"look at user reviews\" : {\"text\": 'here',\"href\": " + "'" + movie.getDetailURL() +  "review" + "'" + "}}";
     //   JSONObject obj = new JSONObject(jsonstr);
        
      //  params.putString("properties", "");
        
        params.putString("properties", jsonstr);
        
        WebDialog feedDialog = (
            new WebDialog.FeedDialogBuilder(context,
                Session.getActiveSession(),
                params))
            .setOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(Bundle values,
                    FacebookException error) {
                    // When the story is posted, echo the success
                    // and the post Id.
                    final String postId = values.getString("post_id");
                    if (postId != null) {
                        Toast.makeText(context,
                            "Posted story, id: "+postId,
                        Toast.LENGTH_SHORT).show();
                    }
                }

            })
            .build();
        feedDialog.show();
    }
   
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        // Your existing onSaveInstanceState code
//
//        // Save the Facebook session in the Bundle
//        Session session = Session.getActiveSession();
//        Session.saveSession(session, outState);
//    }
//    
//    
//    @Override
//    public void onStart() {
//        super.onStart();
//
//        System.out.println("I am on onStart");
//        // Your existing onStart code.
//
//        Session.getActiveSession().addCallback(statusCallback);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//
//        // Your existing onStop code.
//        System.out.println("I am on onStop");
//        Session.getActiveSession().removeCallback(statusCallback);
//    }
    
    
//    private void onClickFacebookLogin() {
//        Session session = Session.getActiveSession();
//        if (!session.isOpened() && !session.isClosed()) {
//            session.openForPublish(
//                    new Session.OpenRequest(this)
//                    .setCallback(statusCallback)
//                    .setPermissions(Arrays.asList("publish_stream"))
//            );
//        } else {
//            Session.openActiveSession(this, true, statusCallback);
//        }
//    }
//
//    private void onClickFacebookLogout() {
//        Session session = Session.getActiveSession();
//        session.closeAndClearTokenInformation();
//    }
}