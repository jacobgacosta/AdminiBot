package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

public class ExpensesPaymentMethodsContract {

    public static abstract class ExpensesPaymentMethods implements BaseColumns {
        public static final String TABLE_NAME = "expenses_payment_methods ";
        public static final String COLUMN_EXPENSE_ID = "expense_id";
        public static final String COLUMN_PAYMENTH_METHOD_ID = "payment_method_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_NAME_NULLABLE = "null";
    }

}
