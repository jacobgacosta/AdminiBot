package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class IncomesPaymentMethodsContract {

    public static abstract class IncomePaymentMethod implements BaseColumns {
        public static final String TABLE_NAME = "incomes_payment_methods";

        public static final String COLUMN_NAME_NULLABLE = "null";

        static final String COLUMN_AMOUNT = "amount";
        static final String COLUMN_INCOME_ID = "income_id";
        static final String COLUMN_UPDATED_AT = "update_at";
        static final String COLUMN_CREATED_AT = "created_at";
        static final String COLUMN_PAYMENTH_METHOD_ID = "payment_method_id";
    }

    static final String SQL_CREATE_TABLE = "CREATE TABLE " + IncomePaymentMethod.TABLE_NAME +
            "(" + IncomePaymentMethod._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            IncomePaymentMethod.COLUMN_INCOME_ID + " INTEGER NOT NULL, " +
            IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID + " INTEGER NOT NULL, " +
            IncomePaymentMethod.COLUMN_AMOUNT + " TEXT NOT NULL, " +
            IncomePaymentMethod.COLUMN_CREATED_AT + " TEXT NOT NULL, " +
            IncomePaymentMethod.COLUMN_UPDATED_AT + " TEXT NOT NULL)";

    static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + IncomePaymentMethod.TABLE_NAME;
}
