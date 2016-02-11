package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class IncomesPaymentMethodsContract {

    public static abstract class IncomesPaymentMethods implements BaseColumns {
        public static final String TABLE_NAME = "incomes_payment_methods";
        public static final String COLUMN_EXPENSE_ID = "income_id";
        public static final String COLUMN_PAYMENTH_METHOD_ID = "payment_method_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + IncomesPaymentMethods.TABLE_NAME +
            "(" + IncomesPaymentMethods._ID + " INTEGER PRIMARY KEY, " +
            IncomesPaymentMethods.COLUMN_EXPENSE_ID + " INTEGER NOT NULL, " +
            IncomesPaymentMethods.COLUMN_PAYMENTH_METHOD_ID + " INTEGER NOT NULL, " +
            IncomesPaymentMethods.COLUMN_AMOUNT + " REAL NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + IncomesPaymentMethods.TABLE_NAME;

}
