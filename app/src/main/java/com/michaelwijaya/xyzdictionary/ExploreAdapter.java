package com.michaelwijaya.xyzdictionary;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ExploreViewHolder>{
    ArrayList<Words> words;
    Context context;
    Gson gson;
    String json;

    public ExploreAdapter(Context context, ArrayList<Words> words){
        this.words = words;
        this.context = context;
    }

    @NonNull
    @Override
    public ExploreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View ExploreView = inflater.inflate(R.layout.item_word, parent, false);
        return new ExploreViewHolder(ExploreView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExploreViewHolder holder, int position) {
        gson = new Gson();
        holder.tvWordTitle.setText(words.get(position).getWord());
        holder.itemView.setOnClickListener(v -> {
            json = gson.toJson(words.get(position).getDefinitions());

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("definitions", json);
            intent.putExtra("title", words.get(position).getWord());

            context.startActivity(intent);
        });
        holder.btnSave.setVisibility(View.VISIBLE);
        holder.btnSave.setOnClickListener(v -> {
            FavoriteDBHelper dbHelper = new FavoriteDBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            json = gson.toJson(words.get(position).getDefinitions());

            ContentValues values = new ContentValues();
            values.put(FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE, words.get(position).getWord());
            values.put(FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_DEFS, json);

            db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
            Toast.makeText(context, "Word saved to Favorites!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class ExploreViewHolder extends RecyclerView.ViewHolder{
        TextView tvWordTitle;
        Button btnSave;
        public ExploreViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWordTitle = itemView.findViewById(R.id.tv_word_title);
            btnSave = itemView.findViewById(R.id.btn_save);
        }
    }
}
