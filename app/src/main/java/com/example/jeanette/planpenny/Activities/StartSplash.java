package com.example.jeanette.planpenny.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.jeanette.planpenny.R;

public class StartSplash extends Activity implements Runnable {

    Handler handler = new Handler();
    static StartSplash aktivitetDerVisesNu=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_splash);


        if(savedInstanceState==null){
            handler.postDelayed(this,3000);
        }
        aktivitetDerVisesNu=this;
    }

    public void run(){
        Boolean loggedIn = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean("LoggedIn", false);
        int userID = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("UserID", 0);
        if ((loggedIn )){

            //Opdater SQLite database med MySQL database
            startActivity(new Intent(this, MainActivity.class));

        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }

        aktivitetDerVisesNu.finish();
        aktivitetDerVisesNu=null;

    }

    public void finish(){
        super.finish();
        handler.removeCallbacks(this);

    }
}
