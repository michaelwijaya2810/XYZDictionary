package com.michaelwijaya.xyzdictionary;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteFragment extends Fragment {
    public FavoriteFragment(){}
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        RecyclerView rvFavList = view.findViewById(R.id.rv_fav_list);

        gson = new Gson();

        FavoriteDBHelper dbHelper = new FavoriteDBHelper(super.getContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE,
                FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_DEFS
        };

        Cursor cursor = db.query(
                FavoriteContract.FavoriteEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Words> words =  new ArrayList<>();
        while(cursor.moveToNext()){
            String word = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE));
            String definitions = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_DEFS));
            Type type = new TypeToken<ArrayList<Definitions>>(){}.getType();
            ArrayList<Definitions> definitionsArrayList = gson.fromJson(definitions, type);

            Words wordEntry = new Words();
            wordEntry.setWord(word);
            wordEntry.setDefinitions(definitionsArrayList);
            words.add(wordEntry);
        }

        cursor.close();

        FavoriteAdapter adapter = new FavoriteAdapter(super.getContext(), words);
        rvFavList.setAdapter(adapter);
        rvFavList.setLayoutManager(new LinearLayoutManager(super.getContext()));

        DividerItemDecoration divider = new DividerItemDecoration(rvFavList.getContext(), DividerItemDecoration.VERTICAL);
        rvFavList.addItemDecoration(divider);

        return view;
    }
}