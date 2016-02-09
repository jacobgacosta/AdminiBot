package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class ExpensesPaymentMethodsContract {

    public static abstract class ExpensesPaymentMethods implements BaseColumns {
        public static final String TABLE_NAME = "expenses_payment_methods";
        public static final String COLUMN_EXPENSE_ID = "expense_id";
        public static final String COLUMN_PAYMENTH_METHOD_ID = "payment_method_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ExpensesPaymentMethods.TABLE_NAME +
            "(" + ExpensesPaymentMethods._ID + " INTEGER PRIMARY KEY, " +
            ExpensesPaymentMethods.COLUMN_EXPENSE_ID + " INTEGER NOT NULL, " +
            ExpensesPaymentMethods.COLUMN_PAYMENTH_METHOD_ID + " INTEGER NOT NULL, " +
            ExpensesPaymentMethods.COLUMN_AMOUNT + " REAL NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + ExpensesPaymentMethods.TABLE_NAME;

}
