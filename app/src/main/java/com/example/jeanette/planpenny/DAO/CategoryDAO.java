package com.example.jeanette.planpenny.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jeanette.planpenny.Database.DBHelper;
import com.example.jeanette.planpenny.Objects.Category;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeanette on 18-05-2015.
 */
public class CategoryDAO {

    public static final String TAG = "CategoryDAO";


    //Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;
    private Context context;
    private String[] mAllColumns={DBHelper.COLUMN_CATEGORY_ID,
    DBHelper.COLUMN_CATEGORY_NAME, DBHelper.COLUMN_COLOR_CODE};

    public CategoryDAO(Context context){
        this.context = context;
        mDBHelper = new DBHelper(context);

        //Open the database
        try{
            open();
        }catch (SQLException e){
            Log.e(TAG, "SQLException on opening database" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open () throws SQLException {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close(){
        mDBHelper.close();
    }

    public Category createCategory(String categoryName, int colorCode){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CATEGORY_NAME, categoryName);
        values.put(DBHelper.COLUMN_COLOR_CODE, colorCode);
        long insertID = mDatabase.
                insert(DBHelper.TABLE_CATEGORY, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CATEGORY, mAllColumns,
                DBHelper.COLUMN_CATEGORY_ID+"="+insertID,null, null,
                null, null);
        cursor.moveToFirst();
        Category newCategory = cursorToCategory(cursor);
        cursor.close();
        return newCategory;
    }

    public boolean updateCategory(Long categoryID, String categoryName, int colorcode) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_CATEGORY_NAME, categoryName);
        values.put(DBHelper.COLUMN_COLOR_CODE, colorcode);
        return mDatabase.update(DBHelper.TABLE_CATEGORY, values, DBHelper.COLUMN_CATEGORY_ID + "=" + categoryID, null) > 0;
    }

    public void deleteCategory(Category category){
        long id = category.getCategoryid();
        System.out.println("The deleted category has the id"+id);
        mDatabase.delete(DBHelper.TABLE_CATEGORY,DBHelper.COLUMN_CATEGORY_ID
        + "=" +id,null);
    }

    public List<Category> getAllCategories(){
        List<Category> listCategories = new ArrayList<Category>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_CATEGORY, mAllColumns,
                null, null, null, null, null);
        if (cursor !=null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Category category = cursorToCategory(cursor);
                listCategories.add(category);
                cursor.moveToNext();
            }

            //make sure to close the cursor
            cursor.close();
        }
        return listCategories;
    }

    public Category getCategoryByID(long id){
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CATEGORY, mAllColumns,
                DBHelper.COLUMN_CATEGORY_ID+"=?",
                new String[]{String.valueOf(id)}, null, null ,null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Category category = cursorToCategory(cursor);
        return category;

    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();
        category.setCategoryid(cursor.getInt(0));
        category.setCategoryName(cursor.getString(1));
        category.setColorCode(cursor.getInt(2));
        return category;
    }

    private JSONArray getCategoryResults()
    {
        String myPath = "";// Set path to your database

        String myTable = DBHelper.TABLE_CATEGORY;//Set name of your table

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
