package io.dojogeek.adminibot.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.utils.UserModelDataUtilTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserSQLiteOpenHelperTest {

    private static final int INSERT_ERROR = -1;
    private static final int ADITIONAL_INDEX = 1;
    private static final int NONE_TABLE_CREATED = 0;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private UserModelDataUtilTest mUserModelDataUtilTest;

    @Before
    public void setUp() {

        loadContext();
        deleteExistentDB();
        loadSQLiteHelper();
        openDataBaseConnection();
        loadUserModelUtil();

    }

    @After
    public void tearDown() throws Exception {
        closeDataBaseConnection();
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

        UserModel userModel = mUserModelDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        assertTrue(insertedRecordId > INSERT_ERROR);

    }

    @Test
    public void sqliteHelper_queryData_isTrue() {

        UserModel userModel = mUserModelDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        Cursor cursor = mUserModelDataUtilTest.queryRecordWhere(getIdField(insertedRecordId));

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, userModel);
            }
        }
    }

    @Test
    public void sqliteHelper_updateDate_isTrue() {

        UserModel userModel = mUserModelDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        userModel = mUserModelDataUtilTest.createUserModel("Irene", "Gutierrez", "igutierrez@dojogeek.io");

        ContentValues newContentValues = mUserModelDataUtilTest.createUserContentValues(userModel);

        long updatedRecord = mUserModelDataUtilTest.updateRecord(newContentValues, getIdField(insertedRecordId));

        Cursor cursor = mUserModelDataUtilTest.queryRecordWhere(getIdField(updatedRecord));

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, userModel);
            }
        }
    }

    @Test
    public void sqlite_correctDeleteData_isTrue() {

        UserModel userModel = mUserModelDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        long deletedRecord = mSQLiteDatabase.delete(ExpenseContract.Expense.TABLE_NAME,
                getIdField(insertedRecordId), null);

        assertEquals(deletedRecord, deletedRecord);

    }

    private long insertData(UserModel userModel) {

        ContentValues contentValues = mUserModelDataUtilTest.createUserContentValues(userModel);

        long insertedRecordId = mUserModelDataUtilTest.createRecord(contentValues);

        return  insertedRecordId;

    }

    private void compareResultQueryFields(Cursor currentPosition, UserModel userModel) {

        String name = currentPosition.getString(currentPosition.getColumnIndex(UserContract.User.COLUMN_NAME));
        String lastName = currentPosition.getString(currentPosition.getColumnIndex(UserContract.User.COLUMN_LAST_NAME));
        String email = currentPosition.getString(currentPosition.getColumnIndex(UserContract.User.COLUMN_EMAIL));


        assertEquals(userModel.name, name);
        assertEquals(userModel.lastName, lastName);
        assertEquals(userModel.email, email);

    }


    private void loadUserModelUtil() {
        if (mUserModelDataUtilTest == null) {
            mUserModelDataUtilTest = new UserModelDataUtilTest(mSQLiteDatabase);
        }
    }

    private String getIdField(long id) {
        return UserContract.User._ID + " = " + id;
    }
}
