package com.example.jeanette.planpenny.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeanette on 13-05-2015.
 */
public class Task {
    private String taskname;
    private String ttc;
    private String timeUsed;
    private String starttime;
    private String endtime;
    private String reminder;
    private String place;
    private String note;
    private int taskID;
    private int updateStatus;
    private Project project;
    private Category category;
    private int projectid;
    private int categoryid;

    private int mHeight;
    private ArrayList<Category> categories;
    private ArrayList<Project> projects;
    public ArrayList<Boolean> options;


    public Task(){

    }
    public Task(String taskname, String ttc, String timeUsed, String starttime, String endtime, String reminder, String place, String note, int updateStatus){
        this.taskname = taskname;
        this.ttc = ttc;
        this.timeUsed = timeUsed;
        this.starttime = starttime;
        this.endtime = endtime;
        this.reminder = reminder;
        this.note = note;
    }

    public Task(String taskname){
        this.taskname = taskname;
    }

    public String getTaskname(){
        return taskname;
    }

    public void setTaskname(String taskname){
        this.taskname = taskname;
    }

    public String getTtc(){
        return ttc;
    }

    public void setTtc(String ttc){
        this.ttc= ttc;
    }

    public String getTimeUsed(){
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed){
        this.timeUsed = timeUsed;
    }

    public String getStarttime(){
        return starttime;
    }

    public void setStarttime(String starttime){
        this.starttime = starttime;
    }

    public String getEndtime(){
        return endtime;
    }

    public void setEndtime(String endtime){
        this.endtime = endtime;
    }

    public String getPlace(){
        return place;
    }

    public void setPlace(String place){
        this.place = place;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public int getTaskID(){return taskID;}

    public void setTaskID(int taskID){
        this.taskID = taskID;
    }
    public Project getProject(){
        return project;
    }

    public void setProject(Project project){
        this.project = project;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public String getReminder(){
        return reminder;
    }

    public void setReminder(String reminder){
        this.reminder = reminder;
    }

    public int getUpdateStatus(){
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus){
        this.updateStatus = updateStatus;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }


    public Task(int collapsedHeight) {
        super();
        mHeight = collapsedHeight;
        projects = new ArrayList<Project>();
        categories = new ArrayList<Category>();
        options = new ArrayList<Boolean>();
        for (int i = 0; i < 5; i++) {
            options.add(i, false);
        }

    }
    public int getHeight(){
        return mHeight;
    }

    public List<Category> getCategories(){
        return categories;
    }
}

