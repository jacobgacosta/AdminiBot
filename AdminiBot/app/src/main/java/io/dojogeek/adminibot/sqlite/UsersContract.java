package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.COMMA_SEP;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.TEXT_TYPE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.PRIMARY_KEY;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.INTEGER;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CREATE_TABLE;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.OPEN_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.CLOSE_PARENTHESIS;
import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class UsersContract {

    public static abstract class User implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " +  User.TABLE_NAME +
            "(" + User._ID + " INTEGER PRIMARY KEY, " +
            User.COLUMN_NAME + " TEXT NOT NULL, " +
            User.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
            User.COLUMN_EMAIL + " TEXT NOT NULL)";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + User.TABLE_NAME;

}
