package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class ExpensesBankCardsContract {

    public static abstract class ExpensesBankCard implements BaseColumns {

        public static final String TABLE_NAME = "expenses_bank_cards";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_EXPENSE_ID = "expense_id";
        public static final String COLUMN_BANK_CARD_ID = "bank_card_id";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ExpensesBankCard.TABLE_NAME +
            "(" + ExpensesBankCard._ID + " INTEGER PRIMARY KEY, " +
            ExpensesBankCard.COLUMN_AMOUNT + " REAL NOT NULL, " +
            ExpensesBankCard.COLUMN_EXPENSE_ID + " INTEGER NOT NULL, " +
            ExpensesBankCard.COLUMN_BANK_CARD_ID + " INTEGER NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + ExpensesBankCard.TABLE_NAME;

}
