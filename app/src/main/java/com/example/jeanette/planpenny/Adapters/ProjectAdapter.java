package com.example.jeanette.planpenny.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeanette.planpenny.DAO.ProjectDAO;
import com.example.jeanette.planpenny.Objects.Project;
import com.example.jeanette.planpenny.R;

import java.util.List;

/**
 * Created by Jeanette on 19-05-2015.
 */
public class ProjectAdapter extends BaseAdapter {

    public static final String TAG ="ProjectListFragment";

    private ProjectDAO mProjectDAO;
    private List<Project> list;
    private Context context;
    private LayoutInflater mInflater;


    public ProjectAdapter(Context context, List<Project> list) {
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Project getItem(int position){
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    public List<Project> getItems(){
        return list;
    }




    @Override
    public long getItemId(int position){
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getProjectID() : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.simple_list_item, null);
            context = view.getContext();
            holder = new ViewHolder();
            holder.item = (TextView) view.findViewById(android.R.id.list);
            holder.delete = (Button) view.findViewById(R.id.buttonDelete);

            holder.item.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    changeProjectDialog(position);
                    notifyDataSetChanged();

                    Toast.makeText(context, "Delete button Clicked", Toast.LENGTH_LONG).show();
                }});

            holder.delete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    mProjectDAO = new ProjectDAO(context);
                    Project project = getItem(position);
                    mProjectDAO.deleteProject(project);
                    list.remove(position);

                    notifyDataSetChanged();

                    Log.d(TAG, "delete button pressed");
                }});




            view.setTag(holder);

        }else{
            holder = (ViewHolder) view.getTag();
        }


        // fill row data
        Project currentItem = getItem(position);
        if(currentItem != null) {
            holder.item.setText(currentItem.getProjectname());
        }


        return view;

    }

    public void refresh(List<Project> list){
        this.list = list;
        notifyDataSetChanged();

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
                Project project = getItem(position);
                long projectID = project.getProjectID();
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
        list.clear();
        list.addAll(newList);
        this.notifyDataSetChanged();
    }


    class ViewHolder {
        TextView item;
        Button delete;
    }
}
