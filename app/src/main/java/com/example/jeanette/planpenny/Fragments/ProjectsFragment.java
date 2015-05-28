package com.example.jeanette.planpenny.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jeanette.planpenny.Adapters.ProjectAdapter;
import com.example.jeanette.planpenny.DAO.ProjectDAO;
import com.example.jeanette.planpenny.Objects.Project;
import com.example.jeanette.planpenny.R;

import java.util.List;


public class ProjectsFragment extends ListFragment implements View.OnClickListener {

    public static final String TAG ="ProjectListFragment";


    private ListView listViewProject;

    ProjectAdapter mAdapter;

    ImageButton addProject;
    private TextView emptyList;


    private List<Project> listProject;
    private ProjectDAO mProjectDAO;




    public ProjectsFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag =inflater.inflate(R.layout.fragment_projects_list, container, false);


        emptyList = (TextView) frag.findViewById(R.id.empty);

        listViewProject = (ListView) frag.findViewById(android.R.id.list);

        addProject = (ImageButton) frag.findViewById(R.id.buttonAddProject);
        addProject.setOnClickListener(this);

        mAdapter = new ProjectAdapter(getActivity(),listProject);
        listViewProject.setAdapter(mAdapter);

        //fill the listview
        mProjectDAO = new ProjectDAO(getActivity());
        listProject = mProjectDAO.getAllProjects();
        if(listProject !=null && !listProject.isEmpty()){
            mAdapter = new ProjectAdapter(getActivity(),listProject);
            listViewProject.setAdapter(mAdapter);

        }else{
            //If list is empty
            emptyList.setText("No projects yet press add button to create new");
            listViewProject.setVisibility(View.GONE);
        }

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

    @Override
    public void onResume(){
        super.onResume();
        listProject.clear();
        listProject.addAll(mProjectDAO.getAllProjects());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if(v== addProject){

            addProjectDialog();

        }
    }

    public void addProjectDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose project name");

        final EditText input = new EditText(getActivity());
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });
        input.setText("Project");
        input.setSelectAllOnFocus(true);


        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = input.getText().toString();
                mProjectDAO.createProject(title);
                List<Project> newList = mProjectDAO.getAllProjects();
                mAdapter.updateProjectList(newList);

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void changeProjectDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change project name");

        final EditText input = new EditText(getActivity());
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });
        input.setText("Project");
        input.setSelectAllOnFocus(true);


        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String title = input.getText().toString();
                Project project = mAdapter.getItem(position);
                project.setProjectname(title);
                mAdapter.notifyDataSetChanged();


            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }




}
