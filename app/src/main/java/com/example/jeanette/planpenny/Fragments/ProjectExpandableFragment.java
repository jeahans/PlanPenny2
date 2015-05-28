package com.example.jeanette.planpenny.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jeanette.planpenny.Adapters.ProjectExpandableAdapter;
import com.example.jeanette.planpenny.DAO.ProjectDAO;
import com.example.jeanette.planpenny.DAO.TaskDAO;
import com.example.jeanette.planpenny.Objects.Project;
import com.example.jeanette.planpenny.Objects.Task;
import com.example.jeanette.planpenny.R;

import java.util.List;


public class ProjectExpandableFragment extends Fragment implements View.OnClickListener {
    private ExpandableListView listViewProject;

    ProjectExpandableAdapter mAdapter;
    private LayoutInflater inflater;

    ImageButton addProject;
    private TextView emptyList;


    private List<Project> listProject;
    private ProjectDAO mProjectDAO;

    private List<Task> listTask;
    private TaskDAO mTaskDao;



    public ProjectExpandableFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag =inflater.inflate(R.layout.fragment_task_item_list, container, false);


        emptyList = (TextView) frag.findViewById(R.id.empty);

        listViewProject = (ExpandableListView) frag.findViewById(R.id.list);

        addProject = (ImageButton) frag.findViewById(R.id.buttonAddProject);
        addProject.setOnClickListener(this);

        mAdapter = new ProjectExpandableAdapter(getActivity(),listProject,inflater);
        listViewProject.setAdapter(mAdapter);

        //fill the listview
        mProjectDAO = new ProjectDAO(getActivity());
        listProject = mProjectDAO.getAllProjects();
        if(listProject !=null && !listProject.isEmpty()){
            mAdapter = new ProjectExpandableAdapter(getActivity(),listProject,inflater);
            listViewProject.setAdapter(mAdapter);

        }else{
            //If list is empty
            emptyList.setText("No projects yet press add button to create new");
            listViewProject.setVisibility(View.GONE);
        }


        return frag;
    }







    @Override
    public void onClick(View view) {
        addProjectDialog();
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
}
