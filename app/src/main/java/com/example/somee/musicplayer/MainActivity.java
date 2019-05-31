package com.example.somee.musicplayer;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.somee.musicplayer.Adapter.SoundModel;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView imageview_play;
    ImageView imageview_back;
    ImageView imageview_next;
    ImageView imageView_loop;
    ImageView imageView_shuffle;
    ImageView backgerandmain;
    ImageView cirecleimage;
    ProgressBar progressBar;
    TextView textView;
    TextView textName;
    TextView textDescription;
    SeekBar seekBar;
    CardView cardviewmain;
    LinearLayout playerboundry;
    MediaPlayer mediaPlayer;
    DownloadManager downloadManager;
    private static int REQUEST_CODE = 1;
    private long downloadID;
    boolean playpause;
    Handler handler = new Handler();
    SoundModel soundModel;

    Animation frombottomtotop;
    Animation fromtopbottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frombottomtotop= AnimationUtils.loadAnimation(this,R.anim.fromtopbottom);
        fromtopbottom= AnimationUtils.loadAnimation(this,R.anim.fromup);

        getSupportActionBar().hide();
        mediaPlayer = new MediaPlayer();
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        if ((savedInstanceState != null)
                    && (savedInstanceState.getSerializable("mediaplayer") != null)) {
            Log.i("mediaplayermediaplayer","ererr");
//            mediaPlayer = ((serializrionmediplayer) savedInstanceState.getSerializable("mediaplayer")).mediaPlayer;
                }
                else {
            seekBar.setMax(0);
        }

        soundModel=(SoundModel)getIntent().getSerializableExtra("sound");

        imageview_back = (ImageView) findViewById(R.id.back);
        imageview_play = (ImageView) findViewById(R.id.play);
        imageview_next = (ImageView) findViewById(R.id.loop);
        imageView_loop = (ImageView) findViewById(R.id.loop);
        imageView_shuffle = (ImageView) findViewById(R.id.download);
        backgerandmain = (ImageView) findViewById(R.id.backgerandmain);
        cirecleimage = (ImageView) findViewById(R.id.cirecleimage);
        textView = (TextView)findViewById(R.id.timmp3);
        textName = (TextView)findViewById(R.id.txtName);
        textDescription = (TextView)findViewById(R.id.txtDescription);
        imageview_play.setTag(R.drawable.ic_play_circle_filled);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        cardviewmain=(CardView) findViewById(R.id.cardviewmain);
        playerboundry=(LinearLayout) findViewById(R.id.playerboundry);

        cardviewmain.startAnimation(frombottomtotop);
        playerboundry.setAnimation(fromtopbottom);
        imageview_play.setOnClickListener(this);
        imageview_back.setOnClickListener(this);
        imageview_next.setOnClickListener(this);
        imageView_loop.setOnClickListener(this);
        imageView_shuffle.setOnClickListener(this);
        progressBar.setVisibility(View.GONE);
        textName.setText(soundModel.getMusic_name());
        textDescription.setText(soundModel.getMusic_description());
        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        playpause=false;



//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress);
                }
                int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();

                textView.setText(milliSecondsToTimer(mediaPlayer.getCurrentPosition()));
//                textView.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(getApplicationContext(),"finish",Toast.LENGTH_SHORT).show();


                if(!mp.isPlaying()){
                    imageview_play.setImageResource(R.drawable.ic_play_circle_filled);
                    imageview_play.setTag(R.drawable.ic_play_circle_filled);
                    mediaPlayer.reset();
                    playpause=false;
                    handler.removeCallbacks(run);

                }
            }
        });
        try {
            backgerandmain.setImageBitmap(BitmapFactory.decodeByteArray(soundModel.getImage(), 0, soundModel.getImage().length));
        }catch (Exception e){
            backgerandmain.setImageResource(R.drawable.nath);
        }
        try {
            cirecleimage.setImageBitmap(BitmapFactory.decodeByteArray(soundModel.getImage(), 0, soundModel.getImage().length));
        }catch (Exception e){
            try {
                loadimage(soundModel.getMusic_name(),cirecleimage);
            }catch (Exception e1){
                cirecleimage.setImageResource(R.drawable.nath);
            }

        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
        clearMediaPlayer();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.play:
//BalladePourAdeline

                switch ((Integer) imageview_play.getTag()) {
                    case R.drawable.ic_play_circle_filled:
                        String name=String.format("%s.mp3",soundModel.getMusic_name());
                        String url = String.format("http://10.62.20.89/downloadFile/%s",name); // your URL here
                        Log.i("tagurl",String.valueOf(soundModel.getMusic_name().length()));
                        seekUpdation();
                        //Your read write code.
                        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/kidmusics/" + name);
                        if (playpause){
                            imageview_play.setImageResource(R.drawable.ic_pause_circle_filled);
                            imageview_play.setTag(R.drawable.ic_pause_circle_filled);
                            mediaPlayer.start();
                        }
                        else if (f.isFile()) {
                            try {
                                imageview_play.setImageResource(R.drawable.ic_pause_circle_filled);
                                imageview_play.setTag(R.drawable.ic_pause_circle_filled);
                                mediaPlayer.setDataSource(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/kidmusics/" + name);
                                mediaPlayer.prepare();
                                seekBar.setMax(mediaPlayer.getDuration());
                                mediaPlayer.start();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                imageview_play.setImageResource(R.drawable.ic_pause_circle_filled);
                                imageview_play.setTag(R.drawable.ic_pause_circle_filled);
                                mediaPlayer.setDataSource(url);
                                mediaPlayer.prepare();
                                seekBar.setMax(mediaPlayer.getDuration());
                                mediaPlayer.start();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }


                        break;
                    case R.drawable.ic_pause_circle_filled:
                        imageview_play.setImageResource(R.drawable.ic_play_circle_filled);
                        imageview_play.setTag(R.drawable.ic_play_circle_filled);
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                            playpause=true;
                        }

                        break;

                }
                break;
            case R.id.back:
                Toast.makeText(getApplicationContext(),"back set",Toast.LENGTH_SHORT).show();
                break;
            case R.id.next:
                break;
            case R.id.loop:
                Toast.makeText(getApplicationContext(),"آهنگ به صورت خودکار تکرار می شود",Toast.LENGTH_SHORT).show();
                mediaPlayer.setLooping(true);
                break;
            case R.id.download:
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

                break;

        }

    }


    Runnable run = new Runnable() {
        @Override
        public void run() {
            seekUpdation();
        }
    };

    public void seekUpdation() {
//        Log.i("infoooooo", String.valueOf(mediaPlayer.getCurrentPosition()));
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        handler.postDelayed(run, 1000);
    }

    private void clearMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "آهنگ دانلود شد لطفا به بخش دانلود ها مراجعه کنید", Toast.LENGTH_SHORT).show();

            }
        }
    };
    public  String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }


    @Override
    public void onBackPressed() {
//        Log.i("onbackpress","preesssss");
//        clearMediaPlayer();
        handler.removeCallbacks(run);
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of item position
//        serializseakbar serializseakbar=new serializseakbar(seekBar);
//        outState.putSerializable("seekbar",  serializseakbar);
        serializrionmediplayer serializrionmediplayer=new serializrionmediplayer(mediaPlayer);
        outState.putSerializable("mediaplayer",  serializrionmediplayer);




        }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Read the state of item position
        mediaPlayer= ((serializrionmediplayer) savedInstanceState.getSerializable("mediaplayer")).mediaPlayer;
//        seekBar = ((serializseakbar)savedInstanceState.getSerializable("seekBar")).seekBar;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the


                    String name=String.format("%s.mp3",soundModel.getMusic_name());
                    String url = String.format("http://10.62.20.89/downloadFile/%s",name); // your URL here
                    File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/kidmusics/" + name);
                    if (!f.isFile()){

                        progressBar.setVisibility(View.VISIBLE);
                        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                        request.setAllowedOverRoaming(false);
                        request.setTitle("Downloading " + name + ".mp3");
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/kidmusics/" + name);
                        downloadID = downloadManager.enqueue(request);
                    }else {
                        Toast.makeText(getApplicationContext(),"آهنگ از قبل دانلود شده است",Toast.LENGTH_SHORT).show();
                    }

                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "برای ذخیره آهنگ نیاز به اجازه شما می باشد", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void loadimage(String name,ImageView imageView){

        try {
            String name_temp=String.format("%s.mp3",name);
            String url = String.format("http://10.62.20.89/downloadFile/%s",name_temp);
            final MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(url, new HashMap<String, String>());
            try {
                final byte[] art = metaRetriever.getEmbeddedPicture();

                imageView.setImageBitmap(BitmapFactory.decodeByteArray(art, 0, art.length));
            } catch (Exception e) {

                imageView.setImageResource(R.drawable.nath);
            }


        } catch (NullPointerException e) {

            imageView.setImageResource(R.drawable.nath);
        }
    }
}

