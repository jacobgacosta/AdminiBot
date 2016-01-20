package io.dojogeek.adminibot.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.utils.DateUtils;

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

    @Before
    public void setUp() {

        loadContext();

        deleteExistentDB();

        loadSQLiteHelper();

        openDataBaseConnection();

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

        UserModel userModel = createUserModel();

        ContentValues contentValues = createContentValues(userModel);

        long insertedRecordId = mSQLiteDatabase.insert(UserContract.User.TABLE_NAME,
                UserContract.User.COLUMN_NAME_NULLABLE, contentValues);

        assertTrue(insertedRecordId > INSERT_ERROR);

    }

    @Test
    public void sqliteHelper_queryData_isTrue() {

        UserModel userModel = createUserModel();

        ContentValues contentValues = createContentValues(userModel);

        long insertedRecordId = mSQLiteDatabase.insert(UserContract.User.TABLE_NAME,
                UserContract.User.COLUMN_NAME_NULLABLE, contentValues);

        String where =  ExpenseContract.Expense._ID + " = " + insertedRecordId;

        Cursor cursor = mSQLiteDatabase.query(UserContract.User.TABLE_NAME, null, where,
                null, null, null, null);

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, userModel);
            }
        }
    }

    @Test
    public void sqliteHelper_updateDate_isTrue() {

        UserModel userModel = createUserModel();

        ContentValues contentValues = createContentValues(userModel);

        long insertedRecordId = mSQLiteDatabase.insert(UserContract.User.TABLE_NAME,
                UserContract.User.COLUMN_NAME_NULLABLE, contentValues);

        userModel = changeUserModelValues(userModel);

        contentValues = createContentValues(userModel);

        String where =  UserContract.User._ID + " = " + insertedRecordId;

        mSQLiteDatabase.update(UserContract.User.TABLE_NAME, contentValues, where, null);

        Cursor cursor = mSQLiteDatabase.query(UserContract.User.TABLE_NAME, null, where, null, null, null, null);

        assertNotNull(cursor);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor, userModel);
            }
        }
    }

    @Test
    public void sqlite_correctDeleteData_isTrue() {

        UserModel userModel = createUserModel();

        ContentValues contentValues = createContentValues(userModel);

        long insertedRecordId = mSQLiteDatabase.insert(UserContract.User.TABLE_NAME,
                UserContract.User.COLUMN_NAME_NULLABLE, contentValues);


        String where =  UserContract.User._ID + " = " + insertedRecordId;

        long deletedRecord = mSQLiteDatabase.delete(ExpenseContract.Expense.TABLE_NAME, where, null);

        assertEquals(deletedRecord, deletedRecord);

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

    private void compareResultQueryFields(Cursor currentPosition, UserModel userModel) {

        String name = currentPosition.getString(currentPosition.getColumnIndex(UserContract.User.COLUMN_NAME));
        String lastName = currentPosition.getString(currentPosition.getColumnIndex(UserContract.User.COLUMN_LAST_NAME));
        String email = currentPosition.getString(currentPosition.getColumnIndex(UserContract.User.COLUMN_EMAIL));


        assertEquals(userModel.name, name);
        assertEquals(userModel.lastName, lastName);
        assertEquals(userModel.email, email);

    }

    private UserModel changeUserModelValues(UserModel userModel) {

        userModel.name = "Irene";
        userModel.lastName = "Gutierrez";
        userModel.email = "igutierrez@dojogeek.io";

        return userModel;
    }

    private void loadContext() {
        mContext = getTargetContext();
    }

    private void deleteExistentDB() {
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    private void loadSQLiteHelper() {
        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);
    }

    private void openDataBaseConnection() {
        mSQLiteDatabase = mAdminiBotSQLiteOpenHelper.getReadableDatabase();
    }

    private void closeDataBaseConnection() {
        mAdminiBotSQLiteOpenHelper.close();
    }

    private void loadUserModelUtil() {
        if (mUserModelDataUtil == null) {
            mUserModelDataUtil = new UserModelDataUtil(mSQLiteDatabase);
        }
    }
}
