package io.dojogeek.adminibot.sqlite;

import android.provider.BaseColumns;

import io.dojogeek.adminibot.R;

import static io.dojogeek.adminibot.sqlite.SQLiteConstants.DROP_TABLE_IF_EXIST;

public class CardTypeContract {

    public static final int [] CARD_TYPES = {R.string.card_types_credit,
            R.string.card_types_debit, R.string.card_types_prepaid};

    public static final int [] CARD_TYPES_DESCRIPTIONS = {R.string.card_types_credit_description,
            R.string.card_types_debit_description, R.string.card_types_prepaid_description};

    public static abstract class CardType implements BaseColumns {

        public static final String TABLE_NAME = "card_types";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_NAME_NULLABLE = "null";

    }

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + CardType.TABLE_NAME +
            "(" + CardType._ID + " INTEGER PRIMARY KEY, " +
            CardType.COLUMN_NAME + " TEXT NOT NULL, " +
            CardType.COLUMN_DESCRIPTION + " TEXT NOT NULL)";

    public static final String SQL_INSERT_INITIAL_VALUES_1 = "INSERT INTO " + CardType.TABLE_NAME +
            " VALUES(" + CardType.COLUMN_NAME_NULLABLE + "," +
            "'" + CARD_TYPES[0].getCardType() + "'," +
            "'" + CARD_TYPES[0].getmDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_2 = "INSERT INTO " + CardType.TABLE_NAME +
            " VALUES(" + CardType.COLUMN_NAME_NULLABLE + "," +
            "'" + CARD_TYPES[1].getCardType() + "'," +
            "'" + CARD_TYPES[1].getmDescription() + "')";

    public static final String SQL_INSERT_INITIAL_VALUES_3 = "INSERT INTO " + CardType.TABLE_NAME +
            " VALUES(" + CardType.COLUMN_NAME_NULLABLE + "," +
            "'" + CARD_TYPES[2].getCardType() + "'," +
            "'" + CARD_TYPES[2].getmDescription() + "')";


    public static final String SQL_DELETE_ENTRIES = DROP_TABLE_IF_EXIST + CardType.TABLE_NAME;

}
