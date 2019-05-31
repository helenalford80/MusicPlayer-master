package com.example.somee.musicplayer.Adapter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class commentmodel implements Serializable {
    @SerializedName("comment")
    private String comment_text;
    @SerializedName("fname")
    private String user_fname;
    @SerializedName("lname")
    private String user_lname;

    public commentmodel(String comment_text, String user_fname, String user_lname) {
        this.comment_text = comment_text;
        this.user_fname = user_fname;
        this.user_lname = user_lname;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getUser_fname() {
        return user_fname;
    }

    public void setUser_fname(String user_fname) {
        this.user_fname = user_fname;
    }

    public String getUser_lname() {
        return user_lname;
    }

    public void setUser_lname(String user_lname) {
        this.user_lname = user_lname;
    }
}
