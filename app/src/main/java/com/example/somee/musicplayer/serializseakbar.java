package com.example.somee.musicplayer;

import android.widget.SeekBar;

import java.io.Serializable;

public class serializseakbar implements Serializable {
    SeekBar seekBar;
    public serializseakbar(SeekBar seekBar){
        this.seekBar=seekBar;

    }
}
