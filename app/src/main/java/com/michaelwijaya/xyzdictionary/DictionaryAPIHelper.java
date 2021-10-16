package com.michaelwijaya.xyzdictionary;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DictionaryAPIHelper {
    @GET("https://myawesomedictionary.herokuapp.com/words")
    Call<ArrayList<Words>> getWords(@Query("q") String query);
}
