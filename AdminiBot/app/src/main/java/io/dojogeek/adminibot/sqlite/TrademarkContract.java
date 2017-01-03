package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.enums.TrademarkEnum;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class TrademarkContract {

    public static final TrademarkEnum[] TRADEMARKS = TrademarkEnum.values();

    public static class Trademark implements BaseColumns {

        public static final String TABLE_NAME = "trademarks";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE_NAME = "image_name";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + Trademark.TABLE_NAME +
            "(" + Trademark._ID + " INTEGER PRIMARY KEY, " +
            Trademark.COLUMN_NAME + " TEXT NOT NULL, " +
            Trademark.COLUMN_IMAGE_NAME + " INTEGER NOT NULL)";


    public static final String SQL_INSERT_INITIAL_VALUES_1 = "INSERT INTO " + Trademark.TABLE_NAME +
            " VALUES(" + Trademark.COLUMN_NAME_NULLABLE + "," +
            "'" + TRADEMARKS[0].getName() + "'," +
            "'" + TRADEMARKS[0].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_2 = "INSERT INTO " + Trademark.TABLE_NAME +
            " VALUES(" + Trademark.COLUMN_NAME_NULLABLE + "," +
            "'" + TRADEMARKS[1].getName() + "'," +
            "'" + TRADEMARKS[1].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_3 = "INSERT INTO " + Trademark.TABLE_NAME +
            " VALUES(" + Trademark.COLUMN_NAME_NULLABLE + "," +
            "'" + TRADEMARKS[2].getName() + "'," +
            "'" + TRADEMARKS[2].getImageName() + "')";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + Trademark.TABLE_NAME;

}
