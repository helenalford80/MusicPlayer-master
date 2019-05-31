package com.example.somee.musicplayer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.somee.musicplayer.Adapter.ListAdapterSound;
import com.example.somee.musicplayer.Adapter.SoundModel;
import com.example.somee.musicplayer.Api.APIClient;
import com.example.somee.musicplayer.Api.APIInterface;
import com.example.somee.musicplayer.MainActivity;
import com.example.somee.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragmentlistview extends Fragment {
    APIInterface apiInterface;

    List<SoundModel> soundModels;

    public Fragmentlistview() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        List<SoundModel> soundModels = new ArrayList<SoundModel>();
        View view = inflater.inflate(R.layout.firstactivity, container, false);
        final ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                SoundModel soundmodel = (SoundModel) o;
                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("sound",soundmodel);
                startActivity(intent);
            }
        });

        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<SoundModel>> call = apiInterface.GetAll();
        call.enqueue(new Callback<List<SoundModel>>() {
            @Override
            public void onResponse(Call<List<SoundModel>> call, Response<List<SoundModel>> response) {
                final ListAdapterSound listAdapterSound = new ListAdapterSound(response.body(), getActivity().getApplicationContext());
                listView.setDivider(null);
                listView.setAdapter(listAdapterSound);
            }

            @Override
            public void onFailure(Call<List<SoundModel>> call, Throwable t) {

            }
        });

        return view;
    }
//    private List<SoundModel> getListData() {
//        List<SoundModel> list = new ArrayList<SoundModel>();
////        SoundModel vietnam = new SoundModel("rus", "vn");
////        SoundModel usa = new SoundModel("usa", "us");
////        SoundModel russia = new SoundModel("vit", "ru");
////
////
////        list.add(vietnam);
////        list.add(usa);
////        list.add(russia);
//
//
//        return list;
//    }
}
