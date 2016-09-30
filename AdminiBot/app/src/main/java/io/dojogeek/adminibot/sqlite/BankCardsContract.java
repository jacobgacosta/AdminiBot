package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class BankCardsContract {

    public static abstract class BankCard implements BaseColumns {

        public static final String TABLE_NAME = "bank_cards";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_NUMBER = "number";
        public static final String COLUMN_BANK_ID = "bank_id";
        public static final String COLUMN_BRAND = "brand";
        public static final String COLUMN_CREDIT_AVAILABLE = "credit_available";
        public static final String COLUMN_CARD_TYPE = "card_type_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +  BankCard.TABLE_NAME +
            "(" + BankCard._ID + " INTEGER PRIMARY KEY, " +
            BankCard.COLUMN_NAME + " TEXT NOT NULL, " +
            BankCard.COLUMN_NUMBER + " TEXT NOT NULL, " +
            BankCard.COLUMN_BANK_ID + " INTEGER NOT NULL, " +
            BankCard.COLUMN_BRAND + " TEXT NOT NULL, " +
            BankCard.COLUMN_CREDIT_AVAILABLE + " REAL NOT NULL, " +
            BankCard.COLUMN_CARD_TYPE + " TEXT NOT NULL, " +
            BankCard.COLUMN_USER_ID + " INTEGER NOT NULL)";


    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + BankCard.TABLE_NAME;

}
