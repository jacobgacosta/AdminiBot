package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class ExpensesBankCardsContract {

    public static abstract class BankCard implements BaseColumns {

        public static final String TABLE_NAME = "expenses_bank_cards";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_EXPENSE_ID = "expense_id";
        public static final String COLUMN_BANK_CARD_ID = "bank_card_id";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + BankCard.TABLE_NAME +
            "(" + BankCard._ID + " INTEGER PRIMARY KEY, " +
            BankCard.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
            BankCard.COLUMN_AMOUNT + " REAL NOT NULL, " +
            BankCard.COLUMN_EXPENSE_ID + " INTEGER NOT NULL, " +
            BankCard.COLUMN_BANK_CARD_ID + " INTEGER NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + BankCard.TABLE_NAME;

}
