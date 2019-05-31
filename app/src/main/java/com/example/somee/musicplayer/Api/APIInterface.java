package com.example.somee.musicplayer.Api;

import com.example.somee.musicplayer.Adapter.CategoryModel;
import com.example.somee.musicplayer.Adapter.SoundModel;
import com.example.somee.musicplayer.Adapter.commentmodel;
//import com.example.somee.musicplayer.Model.MusicJson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {


    @POST("/infoAll")
    Call<List<SoundModel>> GetAll();
    @POST("/infoNewMusic")
    Call<List<SoundModel>> GetinfoNewMusic();
    @POST("infoSuggestionMusic")
    Call<List<SoundModel>> GetinfoSuggestionMusic();
    @POST("/infoCategory")
    Call<List<CategoryModel>> Getcategorys();
    @POST("/infoComment")
    Call<List<commentmodel>> Getcomment();
    @POST("/infoBycategory")
    Call<List<SoundModel>> Getcategory(@Query("category") String category);
}
