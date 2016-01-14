package sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.R;

import static sqlite.SQLiteConstants.CLOSE_PARENTHESIS;
import static sqlite.SQLiteConstants.CLOSE_QUOTE;
import static sqlite.SQLiteConstants.COMMA_SEP;
import static sqlite.SQLiteConstants.CREATE_TABLE;
import static sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;
import static sqlite.SQLiteConstants.INSERT_INTO;
import static sqlite.SQLiteConstants.INTEGER;
import static sqlite.SQLiteConstants.OPEN_PARENTHESIS;
import static sqlite.SQLiteConstants.OPEN_QUOTE;
import static sqlite.SQLiteConstants.PRIMARY_KEY;
import static sqlite.SQLiteConstants.VALUES;

public class PaymentMethodsContract {

    private static final int [] PAYMENT_METHODS = {R.string.payment_methods_credit_card,
            R.string.payment_methods_food_stamps, R.string.payment_methods_debit_card, R.string.payment_methods_cash};

    private static final int [] PAYMENT_METHODS_DESCRIPTIONS = {R.string.payment_methods_credit_card_description,
    R.string.payment_methods_food_stamps_description, R.string.payment_methods_debit_card_description,
            R.string.payment_methods_cash_description};

    public static abstract class PaymentMethod implements BaseColumns {

        public static final String TABLE_NAME = "payment_methods ";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + PaymentMethod.TABLE_NAME + OPEN_PARENTHESIS +
            PaymentMethod._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
            PaymentMethod.COLUMN_NAME + INTEGER + COMMA_SEP +
            PaymentMethod.COLUMN_DESCRIPTION + INTEGER + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_1 = INSERT_INTO + PaymentMethod.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + PaymentMethod.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS[0] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS_DESCRIPTIONS[0] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_2 = INSERT_INTO + PaymentMethod.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + PaymentMethod.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS[1] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS_DESCRIPTIONS[1] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_3 = INSERT_INTO + PaymentMethod.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + PaymentMethod.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS[2] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS_DESCRIPTIONS[2] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_4 = INSERT_INTO + PaymentMethod.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + PaymentMethod.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS[3] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + PAYMENT_METHODS_DESCRIPTIONS[3] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + PaymentMethod.TABLE_NAME;
}