package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

public class IncomesContract {


    public static abstract class Incomes implements BaseColumns {

        public static final String TABLE_DESCRIPTION = "description";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_DATE = "receipt_date";
        public static final String COLUMN_NEXT_ENTRY = "next_entry";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

}
