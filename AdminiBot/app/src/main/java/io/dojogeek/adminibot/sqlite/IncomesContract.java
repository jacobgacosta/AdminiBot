package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class IncomesContract {


    public static abstract class Incomes implements BaseColumns {

        public static final String TABLE_NAME = "incomes";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_DATE = "receipt_date";
        public static final String COLUMN_NEXT_ENTRY = "next_entry";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +  Incomes.TABLE_NAME +
            "(" + Incomes._ID + " INTEGER PRIMARY KEY, " +
            Incomes.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            Incomes.COLUMN_AMOUNT + " REAL NOT NULL, " +
            Incomes.COLUMN_DATE + " TEXT NOT NULL, " +
            Incomes.COLUMN_NEXT_ENTRY + " TEXT NOT NULL, " +
            Incomes.COLUMN_USER_ID + " INTEGER NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + Incomes.TABLE_NAME;

}
