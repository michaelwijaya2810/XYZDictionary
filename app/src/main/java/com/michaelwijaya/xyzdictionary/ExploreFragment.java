package com.michaelwijaya.xyzdictionary;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExploreFragment extends Fragment {
    public String TAG = ExploreFragment.class.getSimpleName();
    public static final String BASE_URL = "https://myawesomedictionary.herokuapp.com/";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ExploreFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        SearchView searchView = view.findViewById(R.id.sv_search_bar);
        RecyclerView rvExploreList = view.findViewById(R.id.rv_explore_list);
        rvExploreList.setLayoutManager(new LinearLayoutManager(super.getContext()));

        DividerItemDecoration divider = new DividerItemDecoration(rvExploreList.getContext(), DividerItemDecoration.VERTICAL);
        rvExploreList.addItemDecoration(divider);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DictionaryAPIHelper apiHelper = retrofit.create(DictionaryAPIHelper.class);
                Call<ArrayList<Words>> callWords = apiHelper.getWords(query);
                callWords.enqueue(new Callback<ArrayList<Words>>() {
                    @Override
                    public void onResponse(@NonNull Call<ArrayList<Words>> call, @NonNull Response<ArrayList<Words>> response) {
                        if(!response.isSuccessful()){
                            Log.d(TAG, "Code: " + response.code());
                        }

                        ArrayList<Words> words = response.body();
                        ExploreAdapter adapter = new ExploreAdapter(ExploreFragment.super.getContext(), words);
                        rvExploreList.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(@NonNull Call<ArrayList<Words>> call, @NonNull Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }
}