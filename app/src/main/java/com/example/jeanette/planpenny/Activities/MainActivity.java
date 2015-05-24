package com.example.jeanette.planpenny.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.example.jeanette.planpenny.Adapters.FragmentAdapter;

public class MainActivity extends FragmentActivity {


    private final Handler handler = new Handler();
    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private FragmentAdapter adapter;
    TextView main;
    FragmentAdapter mAdapter;


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

        pager.setAdapter(adapter);
        tabs.setViewPager(pager);
    }


    public static class SettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
        }
    }

}
