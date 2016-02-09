package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.utiltest.sqlite.DataBaseConfigurationTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;

@RunWith(AndroidJUnit4.class)
public class IncomesSQLiteOpenHelperTest {

    private static final int  AFFECTED_ROWS = 1;
    private static final int NONE_TABLE_CREATED = 0;
    private static final int INSERT_ERROR = -1;

    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private DataBaseConfigurationTest mDataBaseConfigurationTest;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mDataBaseConfigurationTest = DataBaseConfigurationTest.getInstance(mContext);
        mDataBaseConfigurationTest.prepareDataBase();

        mSQLiteDatabase = mDataBaseConfigurationTest.getSQLiteDatabase();

    }

    @After
    public void tearDown() throws Exception {
        mDataBaseConfigurationTest.closeDataBaseConnection();
    }

}
