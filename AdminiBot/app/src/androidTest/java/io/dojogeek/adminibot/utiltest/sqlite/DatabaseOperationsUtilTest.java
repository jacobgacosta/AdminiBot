package io.dojogeek.adminibot.utiltest.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.sqlite.UsersContract;

public class DatabaseOperationsUtilTest {

    private SQLiteDatabase mSQLiteDatabase;

    public long createRecord(SQLiteDatabase sqliteDatabase, String tableName, ContentValues contentValues) {

        setSQLiteDatabase(sqliteDatabase);

        return createRecord(tableName, contentValues);
    }

    public Cursor queryAllRecords(SQLiteDatabase sqliteDatabase, String tableName) {

        setSQLiteDatabase(sqliteDatabase);

        Cursor cursor = queryRecord(tableName, null, null, null, null, null, null);

        return cursor;
    }

    public Cursor queryRecordWhere(SQLiteDatabase sqliteDatabase, String tableName, String where) {

        setSQLiteDatabase(sqliteDatabase);

        Cursor cursor = queryRecord(tableName, null, where, null, null, null, null);

        return cursor;
    }

    public long updateRecord(SQLiteDatabase sqliteDatabase, String tableName, ContentValues contentValues,String where) {

        setSQLiteDatabase(sqliteDatabase);

        long updatedValue = updateRecord(tableName, contentValues, where, null);

        return updatedValue;
    }

    public long deleteRecordWhere(SQLiteDatabase sqliteDatabase, String table, String where) {

        setSQLiteDatabase(sqliteDatabase);

        return deleteRecord(table, where, null);
    }

    private long createRecord(String tableName, ContentValues contentValues) {

        long insertedRecordId = mSQLiteDatabase.insertOrThrow(tableName,
                UsersContract.User.COLUMN_NAME_NULLABLE, contentValues);

        return insertedRecordId;
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

    private long deleteRecord(String table, String whereClause, String[] whereArgs) {

        long deletedRecord = mSQLiteDatabase.delete(table, whereClause, whereArgs);

        return  deletedRecord;
    }

    private void setSQLiteDatabase(SQLiteDatabase sqLiteDatabase) {
        mSQLiteDatabase = sqLiteDatabase;
    }

}
