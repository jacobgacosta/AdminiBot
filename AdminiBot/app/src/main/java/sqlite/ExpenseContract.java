package sqlite;

import android.provider.BaseColumns;

import static sqlite.SQLiteConstants.COMMA_SEP;
import static sqlite.SQLiteConstants.DOUBLE;
import static sqlite.SQLiteConstants.TEXT_TYPE;
import static sqlite.SQLiteConstants.PRIMARY_KEY;
import static sqlite.SQLiteConstants.INTEGER;
import static sqlite.SQLiteConstants.CREATE_TABLE;
import static sqlite.SQLiteConstants.OPEN_PARENTHESIS;
import static sqlite.SQLiteConstants.CLOSE_PARENTHESIS;
import static sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class ExpenseContract {

    public static abstract class Expense implements BaseColumns {

        public static final String TABLE_NAME = "expenses";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_EXPENSES_TYPE_ID = "expenses_type_id";
        public static final String COLUMN_AMOUNT = "amount";
        public static final String COLUMN_DATE_EXPEDITURE = "date_expediture";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_ENTRIES = CREATE_TABLE + Expense.TABLE_NAME + OPEN_PARENTHESIS +
            Expense._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
            Expense.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
            Expense.COLUMN_EXPENSES_TYPE_ID + INTEGER + COMMA_SEP +
            Expense.COLUMN_USER_ID + INTEGER + COMMA_SEP +
            Expense.COLUMN_AMOUNT + DOUBLE  + COMMA_SEP +
            Expense.COLUMN_DATE_EXPEDITURE + TEXT_TYPE + CLOSE_PARENTHESIS;

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + Expense.TABLE_NAME;

}
