package io.dojogeek.adminibot.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.utils.DataBaseConfigurationTest;
import io.dojogeek.adminibot.sqlite.utils.UserDataUtilTest;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserSQLiteOpenHelperTest {

    private static final int INSERT_ERROR = -1;
    private static final int NONE_TABLE_CREATED = 0;
    private SQLiteDatabase mSQLiteDatabase;
    private UserDataUtilTest mUserDataUtilTest;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;

    @Before
    public void setUp() {

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(getTargetContext());
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadUserModelUtil();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
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

        UserModel userModel = mUserDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        assertTrue(insertedRecordId > INSERT_ERROR);

    }

    @Test
    public void sqliteHelper_queryData_isTrue() {

        UserModel userModel = mUserDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        Cursor cursor = mUserDataUtilTest.queryRecordWhere(getIdField(insertedRecordId));

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, userModel);
            }
        }
    }

    @Test
    public void sqliteHelper_updateDate_isTrue() {

        UserModel userModel = mUserDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        userModel = mUserDataUtilTest.createUserModel("Irene", "Gutierrez", "igutierrez@dojogeek.io");

        ContentValues newContentValues = mUserDataUtilTest.createUserContentValues(userModel);

        long updatedRecord = mUserDataUtilTest.updateRecord(newContentValues, getIdField(insertedRecordId));

        Cursor cursor = mUserDataUtilTest.queryRecordWhere(getIdField(updatedRecord));

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, userModel);
            }
        }
    }

    @Test
    public void sqlite_correctDeleteData_isTrue() {

        UserModel userModel = mUserDataUtilTest.createUserModel();

        long insertedRecordId = insertData(userModel);

        long deletedRecord = mSQLiteDatabase.delete(ExpenseContract.Expense.TABLE_NAME,
                getIdField(insertedRecordId), null);

        assertEquals(deletedRecord, deletedRecord);

    }

    private long insertData(UserModel userModel) {

        ContentValues contentValues = mUserDataUtilTest.createUserContentValues(userModel);

        long insertedRecordId = mUserDataUtilTest.createRecord(contentValues);

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
        mUserDataUtilTest = new UserDataUtilTest(mSQLiteDatabase);
    }

    private String getIdField(long id) {
        return UserContract.User._ID + " = " + id;
    }
}
