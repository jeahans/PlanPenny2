package com.example.jeanette.planpenny.Objects;

/**
 * Created by Jeanette on 14-05-2015.
 */
public class ClipBoard {

    private int clipboardid;
    private String url;

    public ClipBoard(){}

    public ClipBoard(String url){
        this.url= url;
    }

    public int getClipboardid(){
        return clipboardid;
    }

    public void setClipboardid(int clipboardid){
        this.clipboardid = clipboardid;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }
}
