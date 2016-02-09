package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.R;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CLOSE_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.COMMA_SEP;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CREATE_TABLE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.INTEGER;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.OPEN_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.PRIMARY_KEY;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.INSERT_INTO;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.OPEN_QUOTE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CLOSE_QUOTE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.VALUES;

public class ExpenseTypesContract {

    public static final int [] EXPENSES_TYPES = {R.string.expenses_types_foods,
            R.string.expenses_types_clothes, R.string.expenses_types_drinks, R.string.expenses_types_medicals,
            R.string.expenses_types_luxuries};
    public static final int [] EXPENSES_TYPES_DESCRIPTIONS = {R.string.expenses_types_foods_description,
            R.string.expenses_types_clothes_description, R.string.expenses_types_drinks_description,
            R.string.expenses_types_medicals_description, R.string.expenses_types_luxuries_description};

    public static abstract class ExpenseType implements BaseColumns {

        public static final String TABLE_NAME = "expenses_types ";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ExpenseType.TABLE_NAME +
            "(" + ExpenseType._ID + " INTEGER PRIMARY KEY, " +
            ExpenseType.COLUMN_NAME + " INTEGER NOT NULL, " +
            ExpenseType.COLUMN_DESCRIPTION + " INTEGER NOT NULL)";

    public static final String SQL_INSERT_INITIAL_VALUES_1 = INSERT_INTO + ExpenseType.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + ExpenseType.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES[0] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES_DESCRIPTIONS[0] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_2 = INSERT_INTO + ExpenseType.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + ExpenseType.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES[1] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES_DESCRIPTIONS[1] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_3 = INSERT_INTO + ExpenseType.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + ExpenseType.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES[2] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES_DESCRIPTIONS[2] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_4 = INSERT_INTO + ExpenseType.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + ExpenseType.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES[3] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES_DESCRIPTIONS[3] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_INSERT_INITIAL_VALUES_5 = INSERT_INTO + ExpenseType.TABLE_NAME +
            VALUES + OPEN_PARENTHESIS + ExpenseType.COLUMN_NAME_NULLABLE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES[4] + CLOSE_QUOTE + COMMA_SEP +
            OPEN_QUOTE + EXPENSES_TYPES_DESCRIPTIONS[4] + CLOSE_QUOTE + CLOSE_PARENTHESIS;

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + ExpenseType.TABLE_NAME;

}
