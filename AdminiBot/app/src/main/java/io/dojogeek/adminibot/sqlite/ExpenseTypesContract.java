package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.R;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class ExpenseTypesContract {

    public static final int [] EXPENSES_TYPES = {R.string.expenses_types_foods,
            R.string.expenses_types_clothes, R.string.expenses_types_drinks, R.string.expenses_types_medicals,
            R.string.expenses_types_luxuries};
    public static final int [] EXPENSES_TYPES_DESCRIPTIONS = {R.string.expenses_types_foods_description,
            R.string.expenses_types_clothes_description, R.string.expenses_types_drinks_description,
            R.string.expenses_types_medicals_description, R.string.expenses_types_luxuries_description};

    public static abstract class ExpenseType implements BaseColumns {

        public static final String TABLE_NAME = "expenses_types";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ExpenseType.TABLE_NAME +
            "(" + ExpenseType._ID + " INTEGER PRIMARY KEY, " +
            ExpenseType.COLUMN_NAME + " INTEGER NOT NULL, " +
            ExpenseType.COLUMN_DESCRIPTION + " INTEGER NOT NULL)";

    public static final String SQL_INSERT_INITIAL_VALUES_1 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[0] + "'," +
            "'" + EXPENSES_TYPES_DESCRIPTIONS[0] + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_2 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[1]  + "'," +
            "'" + EXPENSES_TYPES_DESCRIPTIONS[1] + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_3 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[2] + "'," +
            "'" + EXPENSES_TYPES_DESCRIPTIONS[2] + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_4 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[3] + "'," +
            "'" + EXPENSES_TYPES_DESCRIPTIONS[3] + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_5 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[4] + "'," +
            "'" + EXPENSES_TYPES_DESCRIPTIONS[4] + "')";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + ExpenseType.TABLE_NAME;

}
