package com.example.somee.musicplayer.Fragment;

import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.text.SpannableString;

import com.example.somee.musicplayer.Adapter.RecycleViewAdapter;
import com.example.somee.musicplayer.Adapter.SoundModel;
import com.example.somee.musicplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Fragmentdownload extends Fragment {
    private RecyclerView recyclerView;
    private RecycleViewAdapter studentAdapter;
    private List<SoundModel> soundsDataList ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        View view = inflater.inflate(R.layout.threeactivity, container, false);
        recyclerView=view.findViewById(R.id.recycler_view);
        DataPrepare("allmusic");
        studentAdapter = new RecycleViewAdapter(soundsDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(manager);
//        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(studentAdapter);
        setHasOptionsMenu(true);
        return view;
    }



//    @RequiresApi(api = Build.VERSION_CODES.N)
    private void DataPrepare(String category) {
        soundsDataList= new ArrayList<>();
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/kidmusics/" );

        File[] listOfFiles = folder.listFiles();

      try {
          if(listOfFiles.length>0){
              MediaMetadataRetriever retriever = new MediaMetadataRetriever();
              for (File file:listOfFiles) {
                  retriever.setDataSource(file.getPath());
                  if (category.equals("allmusic")){
                      SoundModel  temp =new SoundModel(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                      temp.setImage(retriever.getEmbeddedPicture());
                      temp.setDurationTime(getDurationWithMp3Spi(file));
                      soundsDataList.add(temp);
                  }
                  else if(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM).equals(category)){
                      SoundModel  temp =new SoundModel(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                      temp.setImage(retriever.getEmbeddedPicture());
                      temp.setDurationTime(getDurationWithMp3Spi(file));
                      soundsDataList.add(temp);
                  }


              }
          }else {
              Toast.makeText(getActivity()," آهنگی ذخیره نشده است لطفل از بخش جستجو فایل خود رو ابتدا دانلود کنید !!!",Toast.LENGTH_LONG).show();
          }
      }catch (NullPointerException e){
          Toast.makeText(getActivity()," آهنگی ذخیره نشده است لطفل از بخش جستجو فایل خود رو ابتدا دانلود کنید !!!",Toast.LENGTH_LONG).show();
      }
    }

    private  String getDurationWithMp3Spi(File file) {
        String out = "";
        MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
        metaRetriever.setDataSource(file.getPath());
//        metaRetriever.extractMetadata(MediaMetadataRetriever.)
//        metaRetriever.getEmbeddedPicture()
        String duration =
                metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

        long dur = Long.parseLong(duration);
        String seconds = String.valueOf((dur % 60000) / 1000);
        String minutes = String.valueOf(dur / 60000);
        out = minutes + ":" + seconds;
//        if (seconds.length() == 1) {
//            txtTime.setText("0" + minutes + ":0" + seconds);
//        }else {
//            txtTime.setText("0" + minutes + ":" + seconds);
//        }

        // close object
        metaRetriever.release();
        return  out;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        DataPrepare(String.valueOf(item.getTitle()));
        studentAdapter = new RecycleViewAdapter(soundsDataList);
        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(studentAdapter);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/kidmusics/" );
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();

        File[] listOfFiles = folder.listFiles();
        List < String> stringList=new ArrayList<>();
       try {
           for (File file:listOfFiles) {
               retriever.setDataSource(file.getPath());

               if(!(stringList.contains(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)))){
                   stringList.add(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
                   menu.add(0, 0, 0, retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));

               }
           }
           for(int i = 0; i < menu.size(); i++) {

               MenuItem item = menu.getItem(i);
               SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
               spanString.setSpan(new ForegroundColorSpan(Color.BLUE), 0,     spanString.length(), 0); //fix the color to white
               spanString.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL),0, spanString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE); //fix the color to white

               item.setTitle(spanString);
           }
       }catch (NullPointerException e){

       }

        super.onCreateOptionsMenu(menu, inflater);
    }

}
