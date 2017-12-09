package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class ExpensesOthersPaymentMethodsContract {

    public static abstract class ExpenseOtherPaymentMethod implements BaseColumns {
        public static final String TABLE_NAME = "expenses_payment_methods";
        public static final String COLUMN_EXPENSE_ID = "expense_id";
        public static final String COLUMN_PAYMENTH_METHOD_ID = "payment_method_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ExpenseOtherPaymentMethod.TABLE_NAME +
            "(" + ExpenseOtherPaymentMethod._ID + " INTEGER PRIMARY KEY, " +
            ExpenseOtherPaymentMethod.COLUMN_EXPENSE_ID + " INTEGER NOT NULL, " +
            ExpenseOtherPaymentMethod.COLUMN_PAYMENTH_METHOD_ID + " INTEGER NOT NULL, " +
            ExpenseOtherPaymentMethod.COLUMN_AMOUNT + " REAL NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + ExpenseOtherPaymentMethod.TABLE_NAME;

}
