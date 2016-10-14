package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class CardDetailContract {

    public static abstract class CardDetail implements BaseColumns {

        public static final String TABLE_NAME = "cards_details";
        public static final String COLUMN_CREDIT_LIMIT = "credit_limit";
        public static final String COLUMN_CURRENT_BALANCE = "current_balance";
        public static final String COLUMN_CUTOFF_DATE = "cutoff_date";
        public static final String COLUMN_PAY_DAY_LIMIT = "payday_limit";
        public static final String COLUMN_BANK_CARD_ID = "bank_card_id";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +  CardDetail.TABLE_NAME +
            "(" + CardDetail._ID + " INTEGER PRIMARY KEY, " +
            CardDetail.COLUMN_CREDIT_LIMIT + " REAL NOT NULL, " +
            CardDetail.COLUMN_CURRENT_BALANCE + " REAL NOT NULL, " +
            CardDetail.COLUMN_CUTOFF_DATE + " DATETIME NOT NULL, " +
            CardDetail.COLUMN_BANK_CARD_ID + " INTEGER NOT NULL, " +
            CardDetail.COLUMN_PAY_DAY_LIMIT + " TEXT NOT NULL) ";


    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + CardDetail.TABLE_NAME;

}
