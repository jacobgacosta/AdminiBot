package io.dojogeek.adminibot.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.utiltest.CreatorModels;
import io.dojogeek.adminibot.utiltest.sqlite.DataBaseConfigurationTest;
import io.dojogeek.adminibot.utiltest.sqlite.IncomesDataUtilTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class IncomesSQLiteOpenHelperTest {

    private static final int  AFFECTED_ROWS = 1;
    private static final int NONE_TABLE_CREATED = 0;
    private static final int INSERT_ERROR = -1;

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;
    private IncomesDataUtilTest mIncomesDataUtilTest;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

        loadIncomesUtil();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

    @Test
    public void sqliteHelper_creationIncomesTable_isTrue() {

        Cursor cursor = mSQLiteDatabase.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" +
                IncomesContract.Incomes.TABLE_NAME + "'", null);

        assertNotNull(cursor);
        assertTrue(cursor.getCount() > NONE_TABLE_CREATED);

    }

    @Test
    public void sqliteHelper_insertionData_isTrue() {

        IncomeModel incomeModel = CreatorModels.createIncomeModel();

        assertTrue(insertIncome(incomeModel) > INSERT_ERROR);
    }

    private long insertIncome(IncomeModel incomeModel) {

        ContentValues contentValues = mIncomesDataUtilTest.createContentValues(incomeModel);

        long insertedRecordId = mIncomesDataUtilTest.createRecord(contentValues);

        return insertedRecordId;
    }

    private void loadIncomesUtil() {
        mIncomesDataUtilTest = new IncomesDataUtilTest(mSQLiteDatabase);
    }

}
