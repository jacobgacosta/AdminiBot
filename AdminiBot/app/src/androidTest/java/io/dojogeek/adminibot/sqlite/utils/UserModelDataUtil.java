package io.dojogeek.adminibot.sqlite.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.UserContract;

public class UserModelDataUtil {

    private SQLiteDatabase mSQLiteDatabase;
    private DatabaseOperationsUtils mDatabaseOperationsUtils;

    public UserModelDataUtil(SQLiteDatabase sqliteDatabase) {
        mSQLiteDatabase = sqliteDatabase;
        mDatabaseOperationsUtils = new DatabaseOperationsUtils();
    }

    public long createRecord(ContentValues contentValues) {

        long insertedValueId = mDatabaseOperationsUtils.createRecord(mSQLiteDatabase, UserContract.User.TABLE_NAME,
                contentValues);

        return insertedValueId;
    }

    public Cursor queryRecordWhere(String where) {

        return mDatabaseOperationsUtils.queryRecordBySelection(UserContract.User.TABLE_NAME, where);

    }

    public UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.name = "Jacob";
        userModel.lastName = "Guzman";
        userModel.email = "jgacosta@dojogeek.io";

        return userModel;
    }

    public ContentValues createUserContentValues(UserModel userModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.COLUMN_NAME, userModel.name);
        contentValues.put(UserContract.User.COLUMN_LAST_NAME, userModel.lastName);
        contentValues.put(UserContract.User.COLUMN_EMAIL, userModel.email);

        return contentValues;
    }

}
