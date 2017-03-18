package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class IncomesContract {


    public static abstract class Incomes implements BaseColumns {

        public static final String TABLE_NAME = "incomes";

        public static final String COLUMN_NULLABLE = "null";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_CREATED_AT = "created_at";
        public static final String COLUMN_NEXT_ENTRY = "next_entry";
        public static final String COLUMN_TOTAL_AMOUNT = "total_amount";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +  Incomes.TABLE_NAME +
            "(" + Incomes._ID + " INTEGER PRIMARY KEY, " +
            Incomes.COLUMN_NAME + " TEXT NOT NULL, " +
            Incomes.COLUMN_TOTAL_AMOUNT + " REAL NOT NULL, " +
            Incomes.COLUMN_NEXT_ENTRY + " TEXT, " +
            Incomes.COLUMN_CREATED_AT + " TEXT NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + Incomes.TABLE_NAME;

}
