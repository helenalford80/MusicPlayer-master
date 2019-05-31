package com.example.somee.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.somee.musicplayer.Adapter.ListAdapterSound;
import com.example.somee.musicplayer.Adapter.SoundModel;
import com.example.somee.musicplayer.Api.APIClient;
import com.example.somee.musicplayer.Api.APIInterface;
//import com.example.somee.musicplayer.Model.MusicJson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirstActivity  extends AppCompatActivity {

    APIInterface apiInterface;

    List<SoundModel> soundModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstactivity);
        List<SoundModel> soundModels = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);

//        final ListAdapterSound listAdapterSound = new ListAdapterSound(soundModels, this);
//        listView.setDivider(null);
//        listView.setAdapter(listAdapterSound);

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                SoundModel soundmodel = (SoundModel) o;
//                Toast.makeText(FirstActivity.this, "Selected :" + " " + soundmodel.getMusic_name(), Toast.LENGTH_LONG).show();
                Log.i("intent",soundmodel.getMusic_name());
                Intent intent= new Intent(FirstActivity.this,MainActivity.class);
                intent.putExtra("sound",soundmodel);
                startActivity(intent);
            }
        });


        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<List<SoundModel>> call = apiInterface.GetAll();
        call.enqueue(new Callback<List<SoundModel>>() {
            @Override
            public void onResponse(Call<List<SoundModel>> call, Response<List<SoundModel>> response) {
                final ListAdapterSound listAdapterSound = new ListAdapterSound(response.body(), getApplicationContext());
                listView.setDivider(null);
                listView.setAdapter(listAdapterSound);

//                listView.invalidateViews();
                for (SoundModel SoundModel:response.body()) {
                    Log.i("taggg",SoundModel.getMusic_name());
                }
            }

            @Override
            public void onFailure(Call<List<SoundModel>> call, Throwable t) {

            }
        });


    }

    private List<SoundModel> getListData() {
        List<SoundModel> list = new ArrayList<SoundModel>();
//        SoundModel vietnam = new SoundModel("rus", "vn");
//        SoundModel usa = new SoundModel("usa", "us");
//        SoundModel russia = new SoundModel("vit", "ru");
//
//
//        list.add(vietnam);
//        list.add(usa);
//        list.add(russia);


        return list;
    }
}



