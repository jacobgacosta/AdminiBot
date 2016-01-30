package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.sqlite.utils.DataBaseConfigurationTest;
import io.dojogeek.adminibot.sqlite.utils.TypesPaymentMethodsDataUtilTest;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TypesPaymentMethodsSQLiteOpenHelperTest {

    private static final int NONE_TABLE_CREATED = 0;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private TypesPaymentMethodsDataUtilTest mTypesPaymentMethodsDataUtilTest;

    @Before
    public void setUp() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadPaymentMethodUtil();
    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_successfulTableCreation_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT count(*) FROM sqlite_master WHERE type = 'table' AND name = '" +
                TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME + "'", null);

        if (cursor != null) {

            assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

        }

    }

    @Test
    public void sqliteHelper_numberOfInsertedPaymentMethods_isEquals() {

        final int initialValuesInserted = 3;

        Cursor cursor = mTypesPaymentMethodsDataUtilTest.queryAllRecord();

        assertEquals(cursor.getCount(), initialValuesInserted);

    }

    @Test
    public void sqliteHelper_insertedPaymentMethods_matchingIsTrue() {

        Cursor cursor = mTypesPaymentMethodsDataUtilTest.queryAllRecord();

        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }

    }

    private void compareResultQueryFields(Cursor currentPosition) {

        long id = currentPosition.getInt(currentPosition.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_NAME));
        int description = currentPosition.getInt(currentPosition.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_DESCRIPTION));

        String expectedName = mTypesPaymentMethodsDataUtilTest.getValuePaymentMethodFromId(mContext, id);
        String expectedDescription = mTypesPaymentMethodsDataUtilTest.getValuePaymentMethodDescriptionFromId(mContext, id);

        assertEquals(expectedName, mContext.getString(name));
        assertEquals(expectedDescription, mContext.getString(description));

    }

    private void loadPaymentMethodUtil() {
        mTypesPaymentMethodsDataUtilTest = new TypesPaymentMethodsDataUtilTest(mSQLiteDatabase);
    }
}
