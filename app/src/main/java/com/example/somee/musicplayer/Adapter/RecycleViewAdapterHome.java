package com.example.somee.musicplayer.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.somee.musicplayer.MainActivity;
import com.example.somee.musicplayer.R;

import java.util.HashMap;
import java.util.List;
import java.util.Random;



public class RecycleViewAdapterHome extends RecyclerView.Adapter {
    List<SoundModel> SoundModels;

    public RecycleViewAdapterHome(List<SoundModel> soundModels) {
        SoundModels = soundModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.home_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final SoundModel data = (SoundModel) SoundModels.get(position);
        TextView namehome, agehome;
//        LinearLayout parenthome;
        ImageView imageView;
        ImageView overflowhome;
//        parenthome = holder.itemView.findViewById(R.id.parenthome);
        namehome = holder.itemView.findViewById(R.id.namehome);
        agehome = holder.itemView.findViewById(R.id.agehome);
        imageView = holder.itemView.findViewById(R.id.imageView2home);

//        Random rnd = new Random();
//        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        parenthome.setBackgroundColor(currentColor);
        namehome.setText(data.getMusic_name());
        agehome.setText(data.getDurationTime());

        try {
            String name=String.format("%s.mp3",data.getMusic_name());
            String url = String.format("http://10.62.20.89/downloadFile/%s",name);
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
        overflowhome = holder.itemView.findViewById(R.id.overflowhome);
        overflowhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.itemView.getContext(), MainActivity.class);
                intent.putExtra("sound", data);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return SoundModels.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, age;
        LinearLayout parent;
        ImageView imageView;
        ImageView imagenextplay;

        public MyViewHolder(View itemView) {
            super(itemView);


        }
    }

}
