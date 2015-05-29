package com.example.jeanette.planpenny.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jeanette.planpenny.DAO.CategoryDAO;
import com.example.jeanette.planpenny.DAO.ProjectDAO;
import com.example.jeanette.planpenny.DAO.TaskDAO;
import com.example.jeanette.planpenny.Objects.Category;
import com.example.jeanette.planpenny.Objects.Project;
import com.example.jeanette.planpenny.Objects.Task;
import com.example.jeanette.planpenny.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskAppend extends Activity implements View.OnClickListener {

    private static final String TAG = "TASKAPPEND";
    private TaskDAO taskDAO;
    int taskid;

    Button btnDelete, btnAddProject, btnAddCategory;
    EditText startdate,starttime, enddate, endtime,ttc,alertdate, alerttime,description;
    TextView taskname;

    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_append);

        taskDAO = new TaskDAO(this);

        taskname = (TextView) findViewById(R.id.taskName);

        String tName = "test";
        //String tName = taskDAO.getTaskByID(taskid).getTaskname();
        taskname.setText(tName);
        Log.d(TAG,"Taskname: "+tName);

        startdate = (EditText) findViewById(R.id.start_date_tw);
        starttime = (EditText) findViewById(R.id.start_time);
        enddate = (EditText) findViewById(R.id.end_date_tw);
        endtime = (EditText) findViewById(R.id.end_time);
        ttc = (EditText) findViewById(R.id.ttc_tw);
        alertdate = (EditText) findViewById(R.id.alert_date_tw);
        alerttime = (EditText) findViewById(R.id.alert_hours);
        description = (EditText) findViewById(R.id.content_description_textView);

        btnDelete = (Button) findViewById(R.id.delete_button);
        btnAddProject = (Button) findViewById(R.id.add_to_project_btn);
        btnAddCategory = (Button) findViewById(R.id.add_category_btn);

        startdate.setOnClickListener(this);
        starttime.setOnClickListener(this);
        enddate.setOnClickListener(this);
        endtime.setOnClickListener(this);
        ttc.setOnClickListener(this);
        alertdate.setOnClickListener(this);
        alerttime.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnAddProject.setOnClickListener(this);
        btnAddCategory.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_append, menu);
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
        if (v == btnDelete) {
            taskDAO = new TaskDAO(this);
            Task task = taskDAO.getTaskByID(taskid);
            int tid = taskid;
            String name =task.getTaskname();


            taskDAO.updateTask(tid,name,ttc.getText().toString(),null,
                    startdate.getText()+""+starttime.getText()
                    ,enddate.getText()+""+endtime.getText(),null,
                    description.getText().toString(),Long.valueOf(null),Long.valueOf(null));
        } else if (v == btnAddProject) {
            addProject();
        } else if (v == btnAddCategory) {
            addCategory();
        } else if(v== startdate){

        }else if(v==starttime){

        }else if(v==enddate){

        }else if (v==endtime){

        }else if (v==ttc){


        }else if (v==alertdate){

        }else if (v== alerttime){


        }

    }



    public void addProject() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ProjectDAO projectDAO = new ProjectDAO(this);
        final List<Project> projects = projectDAO.getAllProjects();
        String[] projectnames = new String[projects.size()];
        for (int i = 0; i < projects.size(); i++) {  // i indexes each element successively.
            projectnames[i]=projects.get(i).getProjectname();
        }

        builder.setTitle("Pick a project")
                .setItems(projectnames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        taskDAO = new TaskDAO(getApplicationContext());
                        int projectid = projects.get(which).getProjectID();
                        taskDAO.addProjectToTask(taskid,Long.valueOf(projectid));
                    }
                });
        builder.show();

    }

    public void addCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CategoryDAO categoryDAO = new CategoryDAO(this);
        final List<Category> categories = categoryDAO.getAllCategories();
        String[] categorynames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {  // i indexes each element successively.
            categorynames[i]=categories.get(i).getCategoryName();
        }
        final ArrayList mSelectedItems = new ArrayList();
        builder.setTitle("Pick categories to add")
                .setMultiChoiceItems(categorynames, null, new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface dialog, int which,
                                        boolean isChecked) {
                        if (isChecked) {
                            // If the user checked the item, add it to the selected items
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            // Else, if the item is already in the array, remove it
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                    }
                })
                        // Set the action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        for (int i = 0; i < mSelectedItems.size(); i++) {  // i indexes each element successively.
                            int catID =categories.get(i).getCategoryid();
                            taskDAO = new TaskDAO(getApplicationContext());
                            taskDAO.addCategoryToTask(taskid,Long.valueOf(catID));
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.show();

    }

}


