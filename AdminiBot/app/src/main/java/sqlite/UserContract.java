package sqlite;

import android.provider.BaseColumns;
import static sqlite.SQLiteConstants.COMMA_SEP;
import static sqlite.SQLiteConstants.TEXT_TYPE;
import static sqlite.SQLiteConstants.PRIMARY_KEY;
import static sqlite.SQLiteConstants.INTEGER;
import static sqlite.SQLiteConstants.CREATE_TABLE;
import static sqlite.SQLiteConstants.OPEN_PARENTHESIS;
import static sqlite.SQLiteConstants.CLOSE_PARENTHESIS;
import static sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class UserContract {

    public static abstract class User implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_ENTRIES =  CREATE_TABLE + User.TABLE_NAME +  OPEN_PARENTHESIS +
            User._ID + INTEGER + PRIMARY_KEY + COMMA_SEP +
            User.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
            User.COLUMN_LAST_NAME + TEXT_TYPE + COMMA_SEP +
            User.COLUMN_EMAIL + TEXT_TYPE  + CLOSE_PARENTHESIS;

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + User.TABLE_NAME;

}
