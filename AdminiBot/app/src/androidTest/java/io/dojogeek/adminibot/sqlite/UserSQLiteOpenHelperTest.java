package io.dojogeek.adminibot.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.utils.DateUtils;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserSQLiteOpenHelperTest {

    private static final int INSERT_ERROR = -1;
    private static final int ADITIONAL_INDEX = 1;
    private static final int NONE_TABLE_CREATED = 0;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;

    @Before
    public void setUp() {

        mContext = getTargetContext();

        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);

        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);

        mSQLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
    }

    @After
    public void tearDown() throws Exception {
        mAdminiBotSQLiteOpenHelper.close();
    }

    @Test
    public void sqliteHelper_correctCreatedTable_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
                UserContract.User.TABLE_NAME + "'", null);

        if (cursor != null) {

            assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

        }
    }

    @Test
    public void sqliteHelper_correctInsertionData_isTrue() {

        UserModel userModel = createUserModel();

        ContentValues contentValues = createContentValues(userModel);

        long insertedRecordId = mSQLiteDatabase.insert(UserContract.User.TABLE_NAME,
                UserContract.User.COLUMN_NAME_NULLABLE, contentValues);

        assertTrue(insertedRecordId > INSERT_ERROR);

    }

    private UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.email = "jgacosta@dojogeek.io";
        userModel.name = "Jacob";
        userModel.lastName = "Guzman";

        return userModel;
    }

    private ContentValues createContentValues(UserModel userModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.COLUMN_NAME, userModel.name);
        contentValues.put(UserContract.User.COLUMN_EMAIL, userModel.email);
        contentValues.put(UserContract.User.COLUMN_LAST_NAME, userModel.lastName);

        return contentValues;
    }
}
