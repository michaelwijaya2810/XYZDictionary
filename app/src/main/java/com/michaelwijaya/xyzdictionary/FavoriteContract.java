package com.michaelwijaya.xyzdictionary;

import android.provider.BaseColumns;

public class FavoriteContract {
    private FavoriteContract(){}

    public static class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favorite_table";
        public static final String COLUMN_NAME_WORD_TITLE = "word_title";
        public static final String COLUMN_NAME_WORD_DEFS = "word_defs";
    }
}
