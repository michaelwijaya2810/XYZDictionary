package com.michaelwijaya.xyzdictionary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    boolean isSaved = false;
    Gson gson;
    ArrayList<Definitions> definitions;
    Intent intent;

    FavoriteDBHelper dbHelper;
    SQLiteDatabase db;

    String title;

    Button btnSave;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        gson = new Gson();

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setHomeAsUpIndicator(R.drawable.back_button);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        intent = getIntent();
        String json = intent.getStringExtra("definitions");
        Type type = new TypeToken<ArrayList<Definitions>>(){}.getType();
        definitions = gson.fromJson(json, type);

        TextView tvWordTitle = findViewById(R.id.tv_word_title);
        RecyclerView rvDefList = findViewById(R.id.rv_def_list);
        btnSave = findViewById(R.id.btn_save);
        btnDelete = findViewById(R.id.btn_delete);

        title = intent.getStringExtra("title");

        tvWordTitle.setText(title);

        DefinitionAdapter adapter = new DefinitionAdapter(this, definitions);
        rvDefList.setAdapter(adapter);
        rvDefList.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration divider = new DividerItemDecoration(rvDefList.getContext(), DividerItemDecoration.VERTICAL);
        rvDefList.addItemDecoration(divider);

        dbHelper = new FavoriteDBHelper(DetailActivity.this);
        db = dbHelper.getWritableDatabase();

        String[] projection = {
                FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE,
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

        while(cursor.moveToNext()){
            String check = cursor.getString(cursor.getColumnIndexOrThrow(FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE));
            if(check.equals(title)){
                isSaved = true;
                break;
            }
        }

        cursor.close();

        changeButtonState(isSaved);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_save){
            String json = gson.toJson(definitions);
            ContentValues values = new ContentValues();
            values.put(FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE, title);
            values.put(FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_DEFS, json);

            db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null, values);
            isSaved = true;
            changeButtonState(true);
            Toast.makeText(DetailActivity.this, "Word saved to Favorites!", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.btn_delete){
            String selection = FavoriteContract.FavoriteEntry.COLUMN_NAME_WORD_TITLE + " LIKE ?";
            String[] selectionArgs = {title};

            db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, selection, selectionArgs);
            isSaved = false;
            changeButtonState(false);
            Toast.makeText(DetailActivity.this, "Word removed from Favorites!", Toast.LENGTH_SHORT).show();
        }
    }

    public void changeButtonState(boolean isSaved){
        if(isSaved){
            btnDelete.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
        }else{
            btnSave.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}