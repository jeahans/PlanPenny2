package com.example.jeanette.planpenny.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jeanette.planpenny.R;


public class LoginActivity extends Activity implements View.OnClickListener {

    EditText editEmail, editPass;
    Button OkButton, createUserButton;
    TextView textLogIn, textEmail, textPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textLogIn = (TextView) findViewById(R.id.textLogIn);
        textEmail = (TextView) findViewById(R.id.textEmail);
        textPass = (TextView) findViewById(R.id.textPass);

        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editPass = (EditText) findViewById(R.id.editTextPass);

        OkButton = (Button) findViewById(R.id.buttonOK);
        createUserButton = (Button) findViewById(R.id.buttonCreateUser);

        OkButton.setOnClickListener(this);
        createUserButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v==OkButton){
            //User logs in and gets userid returned
            int userID=1;

            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putBoolean("LoggedIn", true)
                    .putInt("UserID", userID)
                    .commit();
            startActivity(new Intent(this, MainActivity.class));


        } else if(v == createUserButton){
            startActivity(new Intent (this, CreateUserActivity.class));
        }

    }
}
