package com.example.jeanette.planpenny.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jeanette.planpenny.Database.DBHelper;
import com.example.jeanette.planpenny.Objects.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeanette on 18-05-2015.
 */
public class TaskDAO {
    public static final String TAG = "TaskDAO";

    private Context context;

    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private String[] mAllColumns={DBHelper.COLUMN_TASK_ID, DBHelper.COLUMN_TASK_NAME, DBHelper.COLUMN_TTC,
                DBHelper.COLUMN_TIME_USED, DBHelper.COLUMN_START_TIME, DBHelper.COLUMN_END_TIME,
                DBHelper.COLUMN_PLACE, DBHelper.COLUMN_NOTE, DBHelper.COLUMN_PROJECT_ID, DBHelper.COLUMN_CATEGORY_ID};

    public TaskDAO(Context context) {
        mDBHelper = new DBHelper(context);
        this.context = context;
        //open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void open() throws SQLException {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close() {
        mDBHelper.close();
    }

    public Task createTask(String taskname, String ttc, String timeused, String starttime,
                           String endtime, String place, String note, long projectID, long categoryID) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TASK_NAME, taskname);
        values.put(DBHelper.COLUMN_TTC, ttc);
        values.put(DBHelper.COLUMN_TIME_USED, timeused);
        values.put(DBHelper.COLUMN_START_TIME, starttime);
        values.put(DBHelper.COLUMN_END_TIME, endtime);
        values.put(DBHelper.COLUMN_PLACE, place);
        values.put(DBHelper.COLUMN_NOTE, note);
        values.put(DBHelper.COLUMN_PROJECT_ID, projectID);
        values.put(DBHelper.COLUMN_CATEGORY_ID, categoryID);
        long insertID = mDatabase.insert(DBHelper.TABLE_TASK, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASK,
                mAllColumns, DBHelper.COLUMN_TASK_ID + "=" + insertID, null, null, null, null);
        cursor.moveToFirst();
        Task newTask = cursorToTask(cursor);
        cursor.close();
        return newTask;
    }


    public boolean updateTaskName(int taskID, String taskname){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TASK_NAME, taskname);
        return mDatabase.update(DBHelper.TABLE_TASK, values, DBHelper.COLUMN_TASK_ID + "=" + taskID, null) > 0;
    }

    public boolean updateTask(int taskID, String taskname, String ttc, String timeused, String starttime,
                              String endtime, String place, String note, long projectID, long categoryID) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TASK_NAME, taskname);
        values.put(DBHelper.COLUMN_TTC, ttc);
        values.put(DBHelper.COLUMN_TIME_USED, timeused);
        values.put(DBHelper.COLUMN_START_TIME, starttime);
        values.put(DBHelper.COLUMN_END_TIME, endtime);
        values.put(DBHelper.COLUMN_PLACE, place);
        values.put(DBHelper.COLUMN_NOTE, note);
        values.put(DBHelper.COLUMN_PROJECT_ID, projectID);
        values.put(DBHelper.COLUMN_CATEGORY_ID, categoryID);
        return mDatabase.update(DBHelper.TABLE_TASK, values, DBHelper.COLUMN_TASK_ID + "=" + taskID, null) > 0;
    }
    public boolean addProjectToTask(int taskID, long projectID) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PROJECT_ID, projectID);
        return mDatabase.update(DBHelper.TABLE_TASK, values, DBHelper.COLUMN_TASK_ID + "=" + taskID, null) > 0;
    }

    public boolean addCategoryToTask(int taskID, long categoryID) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CATEGORY_ID, categoryID);
        return mDatabase.update(DBHelper.TABLE_TASK, values, DBHelper.COLUMN_TASK_ID + "=" + taskID, null) > 0;
    }



    public void deleteTask(Task task) {
        int id = task.getTaskID();
        System.out.println("the deleted task has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_TASK, DBHelper.COLUMN_TASK_ID + "=" + id, null);
    }


    public List<Task> getAllTasks(){
        List<Task> listTasks = new ArrayList<Task>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASK, mAllColumns,
                null, null, null, null, null);
        if (cursor !=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Task task = cursorToTask(cursor);
                listTasks.add(task);
                cursor.moveToNext();
            }

            //make sure to close the cursor
            cursor.close();
        }
        return listTasks;
    }

    public List<Task> getTasksOfProjectID(long projectID){
        List<Task> listTasks = new ArrayList<Task>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASK, mAllColumns,
                DBHelper.COLUMN_PROJECT_ID + "=?",
                new String[]{String.valueOf(projectID)}, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = cursorToTask(cursor);
            listTasks.add(task);
            cursor.moveToNext();
        }
        //close cursor
        cursor.close();
        return listTasks;
    }



    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setTaskID(cursor.getInt(0));
        task.setTaskname(cursor.getString(1));
        task.setTtc(cursor.getString(2));
        task.setTimeUsed(cursor.getString(3));
        task.setStarttime(cursor.getString(4));
        task.setEndtime(cursor.getString(5));
        task.setPlace(cursor.getString(6));
        task.setNote(cursor.getString(7));
        return task;
    }
    public Task getTaskByID(long taskID) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_TASK, mAllColumns,
                DBHelper.COLUMN_TASK_ID+"=?",
                new String[]{String.valueOf(taskID)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Task task = cursorToTask(cursor);

        return task;
    }

    private JSONArray getTaskResults()
    {

        String myPath = "";// Set path to your database

        String myTable = DBHelper.TABLE_TASK;//Set name of your table

        SQLiteDatabase myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        String searchQuery = "SELECT  * FROM " + myTable;
        Cursor cursor = myDataBase.rawQuery(searchQuery, null );

        JSONArray resultSet     = new JSONArray();

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();

            for( int i=0 ;  i< totalColumn ; i++ )
            {
                if( cursor.getColumnName(i) != null )
                {
                    try
                    {
                        if( cursor.getString(i) != null )
                        {
                            Log.d("TAG_NAME", cursor.getString(i));
                            rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
                        }
                        else
                        {
                            rowObject.put( cursor.getColumnName(i) ,  "" );
                        }
                    }
                    catch( Exception e )
                    {
                        Log.d("TAG_NAME", e.getMessage());
                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("TAG_NAME", resultSet.toString());
        return resultSet;
    }


}
