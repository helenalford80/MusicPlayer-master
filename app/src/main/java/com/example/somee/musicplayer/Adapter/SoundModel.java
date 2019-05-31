package com.example.somee.musicplayer.Adapter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SoundModel implements Serializable {

    @SerializedName("music_id")
    public Integer music_id;
    @SerializedName("music_name")
    public String music_name;
    @SerializedName("music_description")
    public String music_description;
    @SerializedName("music_category")
    public String music_category;

    private String Title;
    private String Artist;
    private String Album;

    public String getDurationTime() {
        return DurationTime;
    }

    public void setDurationTime(String durationTime) {
        DurationTime = durationTime;
    }

    private String DurationTime;


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }



    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public byte[] Image;
//    public String Album;


    public SoundModel(String music_name, String music_description) {
        this.music_name = music_name;
        this.music_description = music_description;
    }

    public Integer getMusic_id() {
        return music_id;
    }

    public void setMusic_id(Integer music_id) {
        this.music_id = music_id;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getMusic_description() {
        return music_description;
    }

    public void setMusic_description(String music_description) {
        this.music_description = music_description;
    }

    public String getMusic_category() {
        return music_category;
    }

    public void setMusic_category(String music_category) {
        this.music_category = music_category;
    }
}
