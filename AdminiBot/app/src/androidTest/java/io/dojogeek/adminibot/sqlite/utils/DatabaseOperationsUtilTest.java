package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.UserContract;

public class DatabaseOperationsUtilTest {

    private SQLiteDatabase mSQLiteDatabase;

    public long createRecord(SQLiteDatabase sqliteDatabase, String tableName, ContentValues contentValues) {

        setSQLiteDatabase(sqliteDatabase);

        return createRecord(tableName, contentValues);
    }

    private long createRecord(String tableName, ContentValues contentValues) {

        long insertedRecordId = mSQLiteDatabase.insert(tableName,
                UserContract.User.COLUMN_NAME_NULLABLE, contentValues);

        return insertedRecordId;
    }

    public Cursor queryAllRecords(String tableName) {

        Cursor cursor = queryRecord(tableName, null, null, null, null, null, null);

        return cursor;
    }

    public Cursor queryRecordWhere(String tableName, String where) {

        Cursor cursor = queryRecord(tableName, null, where, null, null, null, null);

        return cursor;
    }

    public long updateRecord(String tableName, ContentValues contentValues,String where) {

        long updatedValue = updateRecord(tableName, contentValues, where, null);

        return updatedValue;
    }

    private Cursor queryRecord(String table, String[] columns, String selection, String[] selectionArgs,
                               String groupBy, String having, String orderBy) {

        Cursor cursor = mSQLiteDatabase.query(table, columns, selection,
                selectionArgs, groupBy, having, orderBy);

        return cursor;
    }

    private long updateRecord(String table, ContentValues values, String whereClause, String[] whereArgs) {

        long updatedValue = mSQLiteDatabase.update(table, values, whereClause, whereArgs);

        return updatedValue;
    }

    private void setSQLiteDatabase(SQLiteDatabase sqLiteDatabase) {
        mSQLiteDatabase = sqLiteDatabase;
    }

}
