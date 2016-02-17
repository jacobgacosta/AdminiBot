package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CLOSE_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CLOSE_QUOTE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.COMMA_SEP;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.INSERT_INTO;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.OPEN_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.OPEN_QUOTE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.VALUES;

public class TypesPaymentMethodsContract {

    public static final TypePaymentMethodEnum [] TYPES_PAYMENT_METHODS = TypePaymentMethodEnum.values();


    public static abstract class TypePaymentMethod implements BaseColumns {

        public static final String TABLE_NAME = "types_payment_methods";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TypePaymentMethod.TABLE_NAME + "(" +
            TypePaymentMethod._ID + " INTEGER PRIMARY KEY, " +
            TypePaymentMethod.COLUMN_NAME + " TEXT NOT NULL, " +
            TypePaymentMethod.COLUMN_DESCRIPTION + " TEXT NOT NULL)";

    public static final String SQL_INSERT_INITIAL_VALUES_1 = "INSERT INTO " + TypePaymentMethod.TABLE_NAME +
            " VALUES(" + TypePaymentMethod.COLUMN_NAME_NULLABLE + "," +
            "'" + TYPES_PAYMENT_METHODS[0].getName() + "'," +
            "'" + TYPES_PAYMENT_METHODS[0].getDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_2 = "INSERT INTO " + TypePaymentMethod.TABLE_NAME +
            " VALUES(" + TypePaymentMethod.COLUMN_NAME_NULLABLE + "," +
            "'" + TYPES_PAYMENT_METHODS[1].getName()  + "'," +
            "'" + TYPES_PAYMENT_METHODS[1].getDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_3 = "INSERT INTO " + TypePaymentMethod.TABLE_NAME +
            " VALUES(" + TypePaymentMethod.COLUMN_NAME_NULLABLE + "," +
            "'" + TYPES_PAYMENT_METHODS[2].getName()  + "'," +
            "'" + TYPES_PAYMENT_METHODS[2].getDescription() + "')";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + TypePaymentMethod.TABLE_NAME;
}