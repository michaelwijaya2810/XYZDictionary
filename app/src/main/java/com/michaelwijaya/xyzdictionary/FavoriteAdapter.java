package com.michaelwijaya.xyzdictionary;

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

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    ArrayList<Words> words;
    Context context;
    Gson gson;
    String json;

    public FavoriteAdapter(Context context, ArrayList<Words> words){
        this.words = words;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View FavoriteView = inflater.inflate(R.layout.item_word, parent, false);
        return new FavoriteViewHolder(FavoriteView);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        gson = new Gson();
        holder.tvWordTitle.setText(words.get(position).getWord());
        holder.itemView.setOnClickListener(v -> {
            json = gson.toJson(words.get(position).getDefinitions());

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("definitions", json);
            intent.putExtra("title", words.get(position).getWord());

            context.startActivity(intent);
        });
        holder.btnDelete.setVisibility(View.VISIBLE);
        holder.btnDelete.setOnClickListener(v -> {
            FavoriteDBHelper dbHelper = new FavoriteDBHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            String selection = FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE + " LIKE ?";
            String[] selectionArgs = {words.get(position).getWord()};
            db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs);

            words.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, words.size());

            Toast.makeText(context, "Word removed from Favorites!", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    static class FavoriteViewHolder extends RecyclerView.ViewHolder{
        TextView tvWordTitle;
        Button btnDelete;
        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWordTitle = itemView.findViewById(R.id.tv_word_title);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
