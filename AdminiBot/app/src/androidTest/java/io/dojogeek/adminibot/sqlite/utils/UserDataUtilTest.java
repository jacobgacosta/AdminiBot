package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.UserContract;

public class UserDataUtilTest {

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtilTest mDatabaseOperationsUtilTest;

    public UserDataUtilTest(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtilTest = new DatabaseOperationsUtilTest();
    }

    public long createRecord(ContentValues contentValues) {

        long insertedValueId = mDatabaseOperationsUtilTest.createRecord(mSQLiteDatabase, UserContract.User.TABLE_NAME,
                contentValues);

        return insertedValueId;
    }

    public Cursor queryRecordWhere(String where) {

        return mDatabaseOperationsUtilTest.queryRecordWhere(mSQLiteDatabase, UserContract.User.TABLE_NAME, where);

    }

    public long updateRecord(ContentValues contentValues, String where) {
        return mDatabaseOperationsUtilTest.updateRecord(mSQLiteDatabase, UserContract.User.TABLE_NAME, contentValues, where);
    }

    public long deleteRecordWhere(String where) {
        return mDatabaseOperationsUtilTest.deleteRecordWhere(mSQLiteDatabase, UserContract.User.TABLE_NAME, where);
    }

    public UserModel createUserModel() {

        return createUserModel("Jacob", "Guzman", "jgacosta@dojogeek.io");
    }

    public ContentValues createUserContentValues(UserModel userModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.COLUMN_NAME, userModel.name);
        contentValues.put(UserContract.User.COLUMN_LAST_NAME, userModel.lastName);
        contentValues.put(UserContract.User.COLUMN_EMAIL, userModel.email);

        return contentValues;
    }

    public UserModel createUserModel(String name, String lastName, String email) {

        UserModel userModel = new UserModel();
        userModel.name = name;
        userModel.lastName = lastName;
        userModel.email = email;

        return userModel;
    }

}
