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
import android.widget.Toast;

import com.example.jeanette.planpenny.Adapters.TaskAdapter;
import com.example.jeanette.planpenny.DAO.TaskDAO;
import com.example.jeanette.planpenny.Objects.Task;
import com.example.jeanette.planpenny.R;

import java.util.List;


public class TasksFragment extends ListFragment implements View.OnClickListener {

    private ListView listViewTask;
    private TaskAdapter mAdapter;

    private List<Task> listTask;
    private ImageButton addTask;
    private TextView emptyList;
    private TaskDAO mTaskDAO;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TasksFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frag = inflater.inflate(R.layout.fragment_tasks, container, false);

        emptyList = (TextView) frag.findViewById(R.id.emptyCategoryTextView);
        listViewTask = (ListView) frag.findViewById(android.R.id.list);

        addTask = (ImageButton) frag.findViewById(R.id.buttonAddCategory);
        addTask.setOnClickListener(this);

        //fill the listview
        mTaskDAO = new TaskDAO(getActivity());
        listTask = mTaskDAO.getAllTasks();
        if(listTask !=null && !listTask.isEmpty()){
            mAdapter = new TaskAdapter(getActivity(),listTask);
            listViewTask.setAdapter(mAdapter);

        }else{
            //If list is empty
            emptyList.setText("No categories yet press add button to create new");
            emptyList.setVisibility(View.VISIBLE);
            listViewTask.setVisibility(View.INVISIBLE);


        }

        mAdapter= new TaskAdapter(getActivity(),listTask);



        // Inflate the layout for this fragment
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
    public void onClick(View v) {
        if(v== addTask){
            Toast.makeText(getActivity(), "Add new task", Toast.LENGTH_SHORT).show();
            addTaskDialog();

        }
    }
    public void addTaskDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose task name");

        final EditText input = new EditText(getActivity());
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText("");
            }
        });
        input.setText("Task");
        input.setSelectAllOnFocus(true);


        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = input.getText().toString();
                mTaskDAO.createTask(title, null,null,null,null,null,null,0,0);
                List<Task> newList = mTaskDAO.getAllTasks();
                mAdapter.updateTaskList(newList);


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
