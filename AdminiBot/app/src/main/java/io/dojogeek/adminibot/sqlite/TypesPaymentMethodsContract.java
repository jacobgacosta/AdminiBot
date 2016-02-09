package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.R;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CLOSE_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CLOSE_QUOTE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.COMMA_SEP;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.INSERT_INTO;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.OPEN_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.OPEN_QUOTE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.VALUES;

public class TypesPaymentMethodsContract {

    public static final int [] TYPES_PAYMENT_METHODS = {R.string.payment_methods_food_coupons,
            R.string.payment_methods_cheque, R.string.payment_methods_cash};

    public static final int [] TYPES_PAYMENT_METHODS_DESCRIPTIONS = {R.string.payment_methods_food_coupons_description,
    R.string.payment_methods_cheque_description, R.string.payment_methods_cash_description};

    public static abstract class TypePaymentMethod implements BaseColumns {

        public static final String TABLE_NAME = "types_payment_methods";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TypePaymentMethod.TABLE_NAME + "(" +
            TypePaymentMethod._ID + " INTEGER PRIMARY KEY, " +
            TypePaymentMethod.COLUMN_NAME + " INTEGER NOT NULL, " +
            TypePaymentMethod.COLUMN_DESCRIPTION + " INTEGER NOT NULL)";

    public static final String SQL_INSERT_INITIAL_VALUES_1 = "INSERT INTO " + TypePaymentMethod.TABLE_NAME +
            " VALUES(" + TypePaymentMethod.COLUMN_NAME_NULLABLE + "," +
            "'" + TYPES_PAYMENT_METHODS[0] + "'," +
            "'" + TYPES_PAYMENT_METHODS_DESCRIPTIONS[0] + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_2 = "INSERT INTO " + TypePaymentMethod.TABLE_NAME +
            " VALUES(" + TypePaymentMethod.COLUMN_NAME_NULLABLE + "," +
            "'" + TYPES_PAYMENT_METHODS[1]  + "'," +
            "'" + TYPES_PAYMENT_METHODS_DESCRIPTIONS[1] + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_3 = "INSERT INTO " + TypePaymentMethod.TABLE_NAME +
            " VALUES(" + TypePaymentMethod.COLUMN_NAME_NULLABLE + "," +
            "'" + TYPES_PAYMENT_METHODS[2]  + "'," +
            "'" + TYPES_PAYMENT_METHODS_DESCRIPTIONS[2] + "')";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + TypePaymentMethod.TABLE_NAME;
}