package com.example.somee.musicplayer;

import android.media.MediaPlayer;

import java.io.Serializable;

public class serializrionmediplayer implements Serializable {
    MediaPlayer mediaPlayer;
    public serializrionmediplayer(MediaPlayer mediaPlayer){
        this.mediaPlayer = mediaPlayer;
    }
}
