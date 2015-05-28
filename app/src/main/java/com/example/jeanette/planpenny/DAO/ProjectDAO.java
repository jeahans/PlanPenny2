package com.example.jeanette.planpenny.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jeanette.planpenny.Database.DBHelper;
import com.example.jeanette.planpenny.Objects.Project;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Jeanette on 18-05-2015.
 */
public class ProjectDAO {

    public static final String TAG = "ProjectDAO";
    private Context context;
    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private String[] mAllColumns= {DBHelper.COLUMN_PROJECT_ID, DBHelper.COLUMN_PROJECT_NAME};

    public ProjectDAO(Context context){
        mDBHelper = new DBHelper(context);
        this.context = context;

        //open the database
        try{
            open();
        } catch(SQLException e){
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close(){
        mDBHelper.close();
    }

    public Project createProject(String projectName){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PROJECT_NAME, projectName);
        long insertId = mDatabase.insert(DBHelper.TABLE_PROJECT, null, values);
        Cursor cursor =mDatabase.query(DBHelper.TABLE_PROJECT,
                mAllColumns, DBHelper.COLUMN_PROJECT_ID+"="+insertId, null, null, null, null);
        cursor.moveToFirst();
        Project newProject = cursorToProject(cursor);
        cursor.close();
        return newProject;
    }

    public boolean updateProject(int projectID, String projectName) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_PROJECT_NAME, projectName);
        return mDatabase.update(DBHelper.TABLE_PROJECT, values, DBHelper.COLUMN_PROJECT_ID + "=" + projectID, null) > 0;
    }

    public void deleteProject(Project project){
        int id = project.getProjectID();
        System.out.println("the deleted project has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_PROJECT,DBHelper.COLUMN_PROJECT_ID+"=+"+id,null);
    }

    public ArrayList<Project> getAllProjects(){
        ArrayList<Project> listProjects= new ArrayList<Project>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_PROJECT,
                mAllColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Project project = cursorToProject(cursor);
            listProjects.add(project);
            cursor.moveToNext();
        }
        //Close the cursor
        cursor.close();
        return listProjects;
    }




    private Project cursorToProject(Cursor cursor) {
        Project project = new Project();
        project.setProjectID(cursor.getInt(0));
        project.setProjectname(cursor.getString(1));
        return project;
    }



    public Project getProjectByID(int projectID) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_PROJECT, mAllColumns,
                DBHelper.COLUMN_PROJECT_ID+"=?",
                new String[]{String.valueOf(projectID)},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        Project project = cursorToProject(cursor);

        return project;
    }

    private JSONArray getProjectResults()
    {

        String myPath = "";// Set path to your database

        String myTable = DBHelper.TABLE_PROJECT;//Set name of your table

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
