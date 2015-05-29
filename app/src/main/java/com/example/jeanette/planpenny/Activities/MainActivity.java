package com.example.jeanette.planpenny.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.jeanette.planpenny.Adapters.FragmentAdapter;
import com.example.jeanette.planpenny.R;

public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private final Handler handler = new Handler();
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private FragmentAdapter adapter;
    TextView main;
    Button logoff;


    //Import fonts from fonts
//    public Typeface RegularFont = Typeface.createFromAsset(getAssets(), "fonts/latoregular.ttf");
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        adapter = new FragmentAdapter((getSupportFragmentManager()));
        main = (TextView) findViewById(R.id.textMainTitle);
        logoff = (Button) findViewById(R.id.buttonLogout);

        logoff.setOnClickListener(this);

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
    }


    @Override
    public void onClick(View v) {
        PreferenceManager.getDefaultSharedPreferences(this).edit()
                .putBoolean("LoggedIn", false)
                .putInt("UserID", 0)
                .commit();
        startActivity(new Intent(this, LoginActivity.class));
    }
}
