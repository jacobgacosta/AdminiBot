package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@Deprecated
@RunWith(AndroidJUnit4.class)
public class TypesPaymentMethodsSQLiteOpenHelperInsertionTest {

    private Context mContext;
    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private SQLiteDatabase mSQLiteDatabase;

    @Before
    public void setup() {

        mContext = getTargetContext();

        loadSQLiteHelper();

        mSQLiteDatabase = openDataBaseConnection();
    }

    @After
    public void tearDown() {
        closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_numberOfInsertedTypesPaymentMethods_isEquals() {

        final int initialValuesInserted = 3;

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME,
                null);

        assertEquals(cursor.getCount(), initialValuesInserted);

    }

    @Test
    public void sqliteHelper_recordMatching_isEquals() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME,
                null);

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }
    }

    private void compareResultQueryFields(Cursor currentPosition) {

        long id = currentPosition.getInt(currentPosition.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_NAME));
        int description = currentPosition.getInt(currentPosition.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_DESCRIPTION));

        String expectedName = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS[((int) id)].getName();
        String expectedDescription = TypesPaymentMethodsContract.TYPES_PAYMENT_METHODS[((int) id)].getDescription();

        assertEquals(expectedName, mContext.getString(name));
        assertEquals(expectedDescription, mContext.getString(description));

    }

    private void loadSQLiteHelper() {
        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);
    }

    public void closeDataBaseConnection() {
        mAdminiBotSQLiteOpenHelper.close();
    }

    public SQLiteDatabase openDataBaseConnection() {
        return mAdminiBotSQLiteOpenHelper.getReadableDatabase();
    }

}
