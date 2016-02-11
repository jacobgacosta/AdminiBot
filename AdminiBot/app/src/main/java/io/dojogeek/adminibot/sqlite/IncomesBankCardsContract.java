package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class IncomesBankCardsContract {

    public static abstract class IncomesBankCards implements BaseColumns {

        public static final String TABLE_NAME = "incomes_bank_cards";
        public static final String COLUMN_INCOME_ID= "income_id";
        public static final String COLUMN_PAYMENT_METHOD_ID = "payment_method_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + IncomesBankCards.TABLE_NAME +
            "(" + IncomesBankCards._ID + " INTEGER PRIMARY KEY, " +
            IncomesBankCards.COLUMN_INCOME_ID + " INTEGER NOT NULL, " +
            IncomesBankCards.COLUMN_PAYMENT_METHOD_ID + " INTEGER NOT NULL, " +
            IncomesBankCards.COLUMN_AMOUNT + " REAL NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + IncomesBankCards.TABLE_NAME;
}
