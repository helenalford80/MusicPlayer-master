package com.example.somee.musicplayer.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.somee.musicplayer.Adapter.CategoryModel;
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

public class Fragmentsearchlistview  extends Fragment implements AdapterView.OnItemSelectedListener{

    APIInterface apiInterface;

    public Fragmentsearchlistview() {
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }

    ListView listView;
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view = inflater.inflate(R.layout.activity_searchfragment, container, false);
        listView=(ListView)view.findViewById(R.id.listViewspinner);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                SoundModel soundmodel = (SoundModel) o;
//                Toast.makeText(FirstActivity.this, "Selected :" + " " + soundmodel.getMusic_name(), Toast.LENGTH_LONG).show();
//                Log.i("intent",soundmodel.getMusic_name());
                Intent intent= new Intent(getActivity(), MainActivity.class);
                intent.putExtra("sound",soundmodel);
                startActivity(intent);
            }
        });
        spinner=(Spinner)view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        final List<String> categories = new ArrayList<String>();

        Call<List<CategoryModel>> call = apiInterface.Getcategorys();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {

                for (CategoryModel categoryModel:response.body()) {
                    categories.add(categoryModel.getCategory_name());
                }
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, categories);
                dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
                spinner.setAdapter(dataAdapter);
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {

            }
        });




        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String item = parent.getItemAtPosition(position).toString();

        Call<List<SoundModel>> call = apiInterface.Getcategory(item);
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
