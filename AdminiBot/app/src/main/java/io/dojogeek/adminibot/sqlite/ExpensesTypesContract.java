package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.enums.ExpenseTypeEnum;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class ExpensesTypesContract {

    public static final ExpenseTypeEnum [] EXPENSES_TYPES = ExpenseTypeEnum.values();

    public static abstract class ExpenseType implements BaseColumns {

        public static final String TABLE_NAME = "expenses_types";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + ExpenseType.TABLE_NAME +
            "(" + ExpenseType._ID + " INTEGER PRIMARY KEY, " +
            ExpenseType.COLUMN_NAME + " TEXT NOT NULL, " +
            ExpenseType.COLUMN_DESCRIPTION + " INTEGER NOT NULL)";

    public static final String SQL_INSERT_INITIAL_VALUES_1 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[0].name() + "'," +
            "'" + EXPENSES_TYPES[0].getDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_2 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[1].name() + "'," +
            "'" + EXPENSES_TYPES[1].getDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_3 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[2].name() + "'," +
            "'" + EXPENSES_TYPES[2].getDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_4 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[3].name() + "'," +
            "'" + EXPENSES_TYPES[3].getDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_5 = "INSERT INTO " + ExpenseType.TABLE_NAME +
            " VALUES(" + ExpenseType.COLUMN_NAME_NULLABLE + "," +
            "'" + EXPENSES_TYPES[4].name() + "'," +
            "'" + EXPENSES_TYPES[4].getDescription() + "')";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + ExpenseType.TABLE_NAME;

}
