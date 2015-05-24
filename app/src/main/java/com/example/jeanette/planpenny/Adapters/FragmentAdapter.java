package com.example.jeanette.planpenny.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.jeanette.planpenny.Fragments.CalendarFragment;
import com.example.jeanette.planpenny.Fragments.CategoriesFragment;
import com.example.jeanette.planpenny.Fragments.ClipBoardFragment;
import com.example.jeanette.planpenny.Fragments.ProjectsFragment;
import com.example.jeanette.planpenny.Fragments.TasksFragment;

/**
 * Created by Jeanette on 07-05-2015.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public int pos;
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment frag = new Fragment();
        switch(position){
            case 0:
                pos=0;
                frag = new TasksFragment();
                break;
            case 1:
                pos=1;
                frag = new ProjectsFragment() ;
                break;
            case 2:
                pos=2;
                frag = new CalendarFragment();
                break;
            case 3:
                pos=3;
                frag = new ClipBoardFragment();
                break;
            case 4:
                pos=4;
                frag = new CategoriesFragment();
                break;

        }

        return frag;

    }




    @Override
    public int getCount() {

        return 5;
    }

    public int getPos(){
        return pos;
    }
    @Override
    public CharSequence getPageTitle(int position){
        String title = "";
        switch(position){
            case 0:
                title = "Tasks";

                break;
            case 1:
                title = "Projects";
                break;
            case 2:
                title = "Calendar";
                break;
            case 3:
                title = "Clip Board";
                break;
            case 4:
                title = "Categories";
                break;
        }
        return title;
    }
}