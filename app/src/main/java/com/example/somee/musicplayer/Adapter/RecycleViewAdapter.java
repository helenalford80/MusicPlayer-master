package com.example.somee.musicplayer.Adapter;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.somee.musicplayer.MainActivity;
import com.example.somee.musicplayer.R;

import java.util.List;
import java.util.Random;

public class RecycleViewAdapter extends RecyclerView.Adapter {

    List SoundModels;

    public RecycleViewAdapter(List SoundModel) {
        this.SoundModels = SoundModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_list_row, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final SoundModel data = (SoundModel) SoundModels.get(position);
        TextView name, age;
        LinearLayout parent;
        ImageView imageView;
        ImageView imagenextplay;
        parent = holder.itemView.findViewById(R.id.parent);
        name = holder.itemView.findViewById(R.id.name);
        age = holder.itemView.findViewById(R.id.age);
        imageView = holder.itemView.findViewById(R.id.imageView2);

        Random rnd = new Random();
        int currentColor = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        parent.setBackgroundColor(currentColor);
        name.setText(data.getMusic_name());
        age.setText(data.getDurationTime());

        try {
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(data.getImage(), 0, data.getImage().length));
        } catch (NullPointerException e) {

            imageView.setImageResource(R.drawable.nath);
        }
        imagenextplay = holder.itemView.findViewById(R.id.overflow);
        imagenextplay.setOnClickListener(new View.OnClickListener() {
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
//            parent = itemView.findViewById(R.id.parent);
//            name = itemView.findViewById(R.id.name);
//            age = itemView.findViewById(R.id.age);
//            imageView = itemView.findViewById(R.id.imageView2);
//            imagenextplay= itemView.findViewById(R.id.overflow);
//            imagenextplay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(itemView.getContext(),"test",Toast.LENGTH_SHORT);
//                }
//            });

        }
    }
}