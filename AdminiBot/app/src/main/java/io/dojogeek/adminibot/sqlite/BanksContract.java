package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.enums.BankEnum;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class BanksContract {

    public static final BankEnum [] BANKS = BankEnum.values();

    public static abstract class Bank implements BaseColumns {

        public static final String TABLE_NAME = "banks";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE_NAME = "image_name";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + Bank.TABLE_NAME +
            "(" + Bank._ID + " INTEGER PRIMARY KEY, " +
            Bank.COLUMN_NAME + " INTEGER NOT NULL, " +
            Bank.COLUMN_IMAGE_NAME + " TEXT NOT NULL)";

    public static final String SQL_INSERT_INITIAL_VALUES_1 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[0].getName() + "'," +
            "'" + BANKS[0].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_2 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[1].getName() + "'," +
            "'" + BANKS[1].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_3 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[2].getName() + "'," +
            "'" + BANKS[2].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_4 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[3].getName() + "'," +
            "'" + BANKS[3].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_5 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[4].getName() + "'," +
            "'" + BANKS[4].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_6 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[5].getName() + "'," +
            "'" + BANKS[5].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_7 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[6].getName() + "'," +
            "'" + BANKS[6].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_8 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[7].getName() + "'," +
            "'" + BANKS[7].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_9 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[8].getName() + "'," +
            "'" + BANKS[8].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_10 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[9].getName() + "'," +
            "'" + BANKS[9].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_11 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[10].getName() + "'," +
            "'" + BANKS[10].getImageName() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_12 = "INSERT INTO " + Bank.TABLE_NAME +
            " VALUES(" + Bank.COLUMN_NAME_NULLABLE + "," +
            "'" + BANKS[11].getName() + "'," +
            "'" + BANKS[11].getImageName() + "')";

    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + Bank.TABLE_NAME;
}
