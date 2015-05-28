package com.example.jeanette.planpenny.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeanette.planpenny.DAO.ProjectDAO;
import com.example.jeanette.planpenny.Objects.Project;
import com.example.jeanette.planpenny.Objects.Task;
import com.example.jeanette.planpenny.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeanette on 20-05-2015.
 */
public class ProjectExpandableAdapter extends BaseExpandableListAdapter {


    private Context context;
    private ArrayList<Project> listProject;
    ProjectDAO mProjectDAO = new ProjectDAO(context);
    TextView textView;


    private LayoutInflater inflater;




    public ProjectExpandableAdapter(Context context, ArrayList<Project> listProject) {
        this.context =context;
        this.listProject = listProject;
    }
    public void addItem(Task item, Project group) {
        if (!listProject.contains(group)) {
            listProject.add(group);
        }
        int index = listProject.indexOf(group);
        ArrayList<Task> ch = listProject.get(index).getTaskList();
        ch.add(item);
        listProject.get(index).setTaskList(ch);
    }

    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Task> chList = listProject.get(groupPosition).getTaskList();
        return chList.get(childPosition);
    }

    public int getChildrenCount(int groupPosition) {
        ArrayList<Task> chList = listProject.get(groupPosition).getTaskList();
        return chList.size();
    }




    @Override
    public int getGroupCount() {
        return 0;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    public List<Project> getItems(){
        return listProject;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }




    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_list_item, null);
        }

        TextView item = (TextView) convertView.findViewById(android.R.id.list);
        Button delete = (Button) convertView.findViewById(R.id.buttonDelete);
        item.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                changeProjectDialog(groupPosition);
                notifyDataSetChanged();

            }});

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mProjectDAO = new ProjectDAO(context);
                Project project = listProject.get(groupPosition);
                mProjectDAO.deleteProject(project);
                listProject.remove(groupPosition);
                List<Project> newList = mProjectDAO.getAllProjects();
                updateProjectList(newList);


                Toast.makeText(context, "Delete button Clicked", Toast.LENGTH_LONG).show();

            }});


        // Set group name
        item.setText(listProject.get(groupPosition).getProjectname());


        return convertView;
    }

    @Override
    public View getChildView(final int parentPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        Task child = (Task) getChild(parentPosition, childPosition);


        if (convertView == null) {

            convertView = inflater.inflate(R.layout.project_child_row, null);
        }

        textView = (TextView) convertView.findViewById(R.id.textView1);
        textView.setText(child.getTaskname());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void changeProjectDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Change project name");

        final EditText input = new EditText(context);
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

                mProjectDAO = new ProjectDAO(context);

                String title = input.getText().toString();
                Project project = listProject.get(position);
                int projectID = project.getProjectID();
                mProjectDAO.updateProject(projectID,title);
                List<Project> newList = mProjectDAO.getAllProjects();
                updateProjectList(newList);



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void updateProjectList(List<Project> newList) {
        listProject.clear();
        listProject.addAll(newList);
        this.notifyDataSetChanged();
    }


}
