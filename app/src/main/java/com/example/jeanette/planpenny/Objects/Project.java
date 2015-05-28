package com.example.jeanette.planpenny.Objects;

import java.util.List;

/**
 * Created by Jeanette on 14-05-2015.
 */
public class Project extends Category {
    String projectname;
    int projectID;

    List<Project> projects;
    private Project project;
    private List<Task> taskList;

    public Project(){}
    public Project(String projectname){
        this.projectname = projectname;
    }

    public String getProjectname(){
        return projectname;
    }

    public void setProjectname(String projectname){
        this.projectname = projectname;
    }

    public int getProjectID(){
        return projectID;
    }

    public void setProjectID(int projectID){
        this.projectID=projectID;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<Project> getProjects(){
        return projects;
    }

    public void addProject(Project project){
        projects.add(project);
    }

    public void removeProject(Project project){
        projects.remove(project);
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
