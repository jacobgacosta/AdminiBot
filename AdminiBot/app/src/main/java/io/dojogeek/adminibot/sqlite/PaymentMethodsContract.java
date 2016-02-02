package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class PaymentMethodsContract {

    public static abstract class PaymentMethods implements BaseColumns {

        public static final String TABLE_NAME = "payment_methods";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_REFERENCE = "reference";
        public static final String COLUMN_TYPE_PAYMENT_METHOD_ID = "type_payment_method_id";
        public static final String COLUMN_AVAILABLE_CREDIT = "available_credit";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + PaymentMethods.TABLE_NAME + "(" +
            PaymentMethods._ID  + " INTEGER PRIMARY KEY, " +
            PaymentMethods.COLUMN_NAME + " TEXT NOT NULL, " +
            PaymentMethods.COLUMN_REFERENCE + " TEXT NOT NULL, " +
            PaymentMethods.COLUMN_TYPE_PAYMENT_METHOD_ID + " INTEGER NOT NULL, " +
            PaymentMethods.COLUMN_AVAILABLE_CREDIT + " REAL NOT NULL, " +
            PaymentMethods.COLUMN_USER_ID + " INTEGER NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + PaymentMethods.TABLE_NAME;

}
