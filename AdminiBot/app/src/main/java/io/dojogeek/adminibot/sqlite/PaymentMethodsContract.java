package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class PaymentMethodsContract {

    public static abstract class PaymentMethod implements BaseColumns {

        public static final String TABLE_NAME = "payment_methods";

        public static final String COLUMN_NAME_NULLABLE = "null";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TYPE = "type";
        public static final String COLUMN_UPDATED_AT = "update_at";
        public static final String COLUMN_CREATED_AT = "created_at";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + PaymentMethod.TABLE_NAME + "(" +
            PaymentMethod._ID + " INTEGER PRIMARY KEY, " +
            PaymentMethod.COLUMN_NAME + " TEXT NOT NULL, " +
            PaymentMethod.COLUMN_TYPE + " TEXT NOT NULL, " +
            PaymentMethod.COLUMN_CREATED_AT + " TEXT NOT NULL, " +
            PaymentMethod.COLUMN_UPDATED_AT + " TEXT)";

    static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + PaymentMethod.TABLE_NAME;
}