package com.example.jeanette.planpenny.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jeanette on 14-05-2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "DBHelper";

    //Columns of the Task table
    public static final String TABLE_TASK = "task";
    public static final String COLUMN_TASK_ID = "taskid";
    public static final String COLUMN_TASK_NAME="taskname";
    public static final String COLUMN_TTC ="ttc";
    public static final String COLUMN_TIME_USED="timeused";
    public static final String COLUMN_START_TIME = "starttime";
    public static final String COLUMN_END_TIME ="endtime";
    public static final String COLUMN_REMINDER = "reminder";
    public static final String COLUMN_PLACE = "place";
    public static final String COLUMN_NOTE = "note";

    //Columns of the Category table
    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_CATEGORY_ID ="categoryid";
    public static final String COLUMN_CATEGORY_NAME = "categoryname";
    public static final String COLUMN_COLOR_CODE = "colorcode";

    //Columns of table Project
    public static final String TABLE_PROJECT = "project";
    public static final String COLUMN_PROJECT_ID ="projectid";
    public static final String COLUMN_PROJECT_NAME = "projectname";

    //Columns of the table ClipBoard
    public static final String TABLE_CLIP_BOARD = "clipboard";
    public static final String COLUMN_CLIP_BOARD_ID="clipboardid";
    public static final String COLUMN_URL= "url";

    private static final String DATABASE_NAME ="planpenny.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_TASK = "CREATE TABLE "+TABLE_TASK+"("
          +COLUMN_TASK_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
          +COLUMN_TASK_NAME+" TEXT NOT NULL, "
          +COLUMN_TTC+" INTEGER, "
          +COLUMN_TIME_USED+" TEXT, "
          +COLUMN_START_TIME+" TEXT, "
          +COLUMN_END_TIME+" TEXT, "
          +COLUMN_PLACE+" TEXT, "
          +COLUMN_NOTE+" TEXT, "
          +COLUMN_CATEGORY_ID+" INTEGER, "
          +COLUMN_PROJECT_ID+" INTEGER"
          +");";

    private static final String SQL_CREATE_TABLE_PROJECT= "CREATE TABLE "+TABLE_PROJECT+" ( "
            +COLUMN_PROJECT_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +COLUMN_PROJECT_NAME+" TEXT NOT NULL"
            +");";

    private static final String SQL_CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + " ( "
            + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_CATEGORY_NAME + " TEXT NOT NULL, "
            + COLUMN_COLOR_CODE + " INTEGER NOT NULL "
            + ");";


    private static final String SQL_CREATE_TABLE_CLIP_BOARD= "CREATE TABLE "+TABLE_CLIP_BOARD+"("
            +COLUMN_CLIP_BOARD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_URL+" TEXT NOT NULL"
            +");";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CATEGORY);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CLIP_BOARD);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PROJECT);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_TASK);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading the database from version" + oldVersion + "to " + newVersion);
        //clear all data
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_CLIP_BOARD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_PROJECT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_PROJECT);

        onCreate(sqLiteDatabase);

    }

    public void clearData(SQLiteDatabase sqLiteDatabase){
        Log.w(TAG, "Clearing database");
        //clear all data
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_CLIP_BOARD);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_PROJECT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXIST"+TABLE_PROJECT);
        onCreate(sqLiteDatabase);
    }
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
