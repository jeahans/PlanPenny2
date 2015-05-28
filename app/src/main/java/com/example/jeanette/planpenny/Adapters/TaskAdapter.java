package com.example.jeanette.planpenny.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeanette.planpenny.Activities.TaskAppend;
import com.example.jeanette.planpenny.DAO.TaskDAO;
import com.example.jeanette.planpenny.Objects.Task;
import com.example.jeanette.planpenny.R;

import java.util.List;


/**
 * Created by Jeanette on 19-05-2015.
 */
public class TaskAdapter extends BaseAdapter {

    private TaskDAO mTaskDAO;
    private List<Task> list;
    private Context context;
    private LayoutInflater mInflater;


    public TaskAdapter(Context context, List<Task> list) {
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0;
    }

    @Override
    public Task getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null;
    }


    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getTaskID() : position;
    }



    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.task_list_item, null);
            context = view.getContext();
            holder = new ViewHolder();
            holder.item = (TextView) view.findViewById(android.R.id.list);
            holder.delete = (Button) view.findViewById(R.id.buttonDelete);
            holder.btnTaskOption = (ImageButton) view.findViewById(R.id.btnTaskOptions);


            holder.item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeTaskDialog(position);
                    notifyDataSetChanged();


                    Toast.makeText(context, "Delete button Clicked", Toast.LENGTH_LONG).show();
                }
            });

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mTaskDAO = new TaskDAO(context);
                    Task task = getItem(position);
                    mTaskDAO.deleteTask(task);
                    list.remove(position);
                    List<Task> newList = mTaskDAO.getAllTasks();
                    updateTaskList(newList);



                }
            });

            holder.btnTaskOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mTaskDAO = new TaskDAO(context);
                    Task task = getItem(position);
                    int i = task.getTaskID();

                    Intent intent = new Intent(context,TaskAppend.class);
                    intent.putExtra("TaskID",i);

                    context.startActivity(intent);
                    Log.d("TaskAdapter", "You pressed the settings button");
                }
            });

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        // fill row data
        Task currentItem = getItem(position);
        if (currentItem != null) {
            holder.item.setText(currentItem.getTaskname());


        }


        return view;
    }


    public List<Task> getItems() {
        return list;
    }

    public void seItems(List<Task> list) {
        this.list = list;
    }

    public void refresh(List<Task> list) {
        this.list = list;
        notifyDataSetChanged();

    }


    class ViewHolder {
        TextView item;
        Button delete;
        ImageButton btnTaskOption;
    }

    public void changeTaskDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Change task name");

        final EditText input = new EditText(context);
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

                mTaskDAO = new TaskDAO(context);

                String title = input.getText().toString();
                Task task = getItem(position);
                int taskID = task.getTaskID();
                //mTaskDAO.updateTask(taskID,title);
                mTaskDAO.updateTaskName(taskID,title);
                List<Task> newList = mTaskDAO.getAllTasks();
                updateTaskList(newList);



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }



    public void updateTaskList(List<Task> newlist) {
        list.clear();
        list.addAll(newlist);
        this.notifyDataSetChanged();
    }
}