package com.example.somee.musicplayer.Fragment;

import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.somee.musicplayer.Adapter.ListAdapter_list_item_home;
import com.example.somee.musicplayer.Adapter.RecycleViewAdapterHome;
import com.example.somee.musicplayer.Adapter.SoundModel;
import com.example.somee.musicplayer.Adapter.commentmodel;
import com.example.somee.musicplayer.Api.APIClient;
import com.example.somee.musicplayer.Api.APIInterface;
import com.example.somee.musicplayer.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Homeactivity extends Fragment {
    private RecyclerView recyclerView;
    private RecycleViewAdapterHome studentAdapter;
    private List<SoundModel> soundsDataList ;
    TextView suggestion_name1;
    TextView suggestion_description1;
    TextView suggestion_name2;
    TextView suggestion_description2;
    TextView suggestion_name3;
    TextView suggestion_description3;
    TextView usernumber;
    ImageView suggestion_pic1;
    ImageView suggestion_pic2;
    ImageView suggestion_pic3;
    APIInterface apiInterface;
    public Homeactivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.homeactivity, container, false);

        suggestion_name1 =(TextView) view.findViewById(R.id.suggestion_name1);
        suggestion_description1 =(TextView) view.findViewById(R.id.suggestion_description1);
        suggestion_name2 =(TextView) view.findViewById(R.id.suggestion_name2);
        suggestion_description2 =(TextView) view.findViewById(R.id.suggestion_description2);
        suggestion_name3 =(TextView) view.findViewById(R.id.suggestion_name3);
        suggestion_description3 =(TextView) view.findViewById(R.id.suggestion_description3);
        usernumber =(TextView) view.findViewById(R.id.usernumber);
        suggestion_pic1 = (ImageView) view.findViewById(R.id.suggestion_pic1);
        suggestion_pic2 = (ImageView) view.findViewById(R.id.suggestion_pic2);
        suggestion_pic3 = (ImageView) view.findViewById(R.id.suggestion_pic3);
        final ListView listView = (ListView) view.findViewById(R.id.listViewhome);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        recyclerView=view.findViewById(R.id.recycler_view_home);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<SoundModel>> call = apiInterface.GetinfoNewMusic();
        call.enqueue(new Callback<List<SoundModel>>() {
            @Override
            public void onResponse(Call<List<SoundModel>> call, Response<List<SoundModel>> response) {

                soundsDataList= response.body();
//                Log.i("studentsize=",String.valueOf(soundsDataList.size()));

                studentAdapter = new RecycleViewAdapterHome(soundsDataList);
//        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setLayoutManager(manager);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(studentAdapter);
                setHasOptionsMenu(true);
            }

            @Override
            public void onFailure(Call<List<SoundModel>> call, Throwable t) {

            }
        });
        Call<List<SoundModel>> call2 = apiInterface.GetinfoSuggestionMusic();
        call2.enqueue(new Callback<List<SoundModel>>() {
            @Override
            public void onResponse(Call<List<SoundModel>> call, Response<List<SoundModel>> response) {
                suggestion_name1.setText(response.body().get(0).getMusic_name());
                suggestion_name2.setText(response.body().get(1).getMusic_name());
                suggestion_name3.setText(response.body().get(2).getMusic_name());
                suggestion_description1.setText(response.body().get(0).getMusic_description());
                suggestion_description2.setText(response.body().get(0).getMusic_description());
                suggestion_description3.setText(response.body().get(0).getMusic_description());
                loadimage(response.body().get(0).getMusic_name(),suggestion_pic1);
                loadimage(response.body().get(1).getMusic_name(),suggestion_pic2);
                loadimage(response.body().get(2).getMusic_name(),suggestion_pic3);
            }

            @Override
            public void onFailure(Call<List<SoundModel>> call, Throwable t) {

            }
        });
        Call<List<commentmodel>> call3 = apiInterface.Getcomment();
        call3.enqueue(new Callback<List<commentmodel>>() {
            @Override
            public void onResponse(Call<List<commentmodel>> call, Response<List<commentmodel>> response) {
                ListAdapter_list_item_home listAdapterSound=new ListAdapter_list_item_home(response.body(),getActivity().getApplicationContext());

                usernumber.setText(String.valueOf(response.body().size()));
                listView.setDivider(null);
                listView.setAdapter(listAdapterSound);
            }

            @Override
            public void onFailure(Call<List<commentmodel>> call, Throwable t) {

            }
        });
        listView.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        return view;

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
