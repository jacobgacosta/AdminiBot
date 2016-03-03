package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class IncomesOthersPaymentMethodsContract {

    public static abstract class IncomePaymentMethod implements BaseColumns {
        public static final String TABLE_NAME = "incomes_payment_methods";
        public static final String COLUMN_INCOME_ID = "income_id";
        public static final String COLUMN_PAYMENTH_METHOD_ID = "payment_method_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + IncomePaymentMethod.TABLE_NAME +
            "(" + IncomePaymentMethod._ID + " INTEGER PRIMARY KEY, " +
            IncomePaymentMethod.COLUMN_INCOME_ID + " INTEGER NOT NULL, " +
            IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID + " INTEGER NOT NULL, " +
            IncomePaymentMethod.COLUMN_AMOUNT + " REAL NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + IncomePaymentMethod.TABLE_NAME;

}
