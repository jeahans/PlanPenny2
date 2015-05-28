package com.example.jeanette.planpenny.Objects;

/**
 * Created by Jeanette on 14-05-2015.
 */
public class Category{

    private int categoryid;
    private String categoryName;
    private int colorCode;

    public Category(){}

    public Category(String categoryName, int colorCode){
        this.categoryName = categoryName;
        this.colorCode = colorCode;
    }
    public int getCategoryid(){
        return categoryid;
    }

    public void setCategoryid(int categoryid){
        this.categoryid = categoryid;
    }

    public Category(int colorCode) {
        this.colorCode = colorCode;
    }

    public String getCategoryName(){
        return categoryName;
    }

    public void setCategoryName(String categoryName){
        this.categoryName=categoryName;
    }

    public int getColorCode(){
        return colorCode;
    }

    public void setColorCode(int colorCode){
        this.colorCode = colorCode;
    }


}
