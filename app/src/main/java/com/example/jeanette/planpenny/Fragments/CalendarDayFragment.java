package com.example.jeanette.planpenny.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.jeanette.planpenny.R;

package com.example.jeanette.planpenny.Fragments;

import android.app.Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.Fragment;

import com.example.jeanette.planpenny.R;



public class CalendarDayFragment extends Fragment implements View.OnClickListener {

    ListView day, previousDay, nextDay;


    public CalendarDayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_calendar_day,container,false);

        /*
        day = (ListView) frag.findViewById(R.id.currentMonth);
        previousDay =(ListView) frag.findViewById(R.id.previousDay);
        nextDay =(ListView) frag.findViewById(R.id.nextDay);

        previousDay.setOnClickListener(this);
        nextDay.setOnClickListener(this);*/


        return frag;


    }




    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onClick(View v){
        if(v== previousDay){
            //Vælg dagen før den valgte
        }else if (v==nextDay){
            //Vælg dagen efter den valgte

        }

    }

}
