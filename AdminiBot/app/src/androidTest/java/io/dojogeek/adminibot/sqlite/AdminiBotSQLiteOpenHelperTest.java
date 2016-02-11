package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import io.dojogeek.adminibot.utiltest.sqlite.DataBaseConfigurationTest;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AdminiBotSQLiteOpenHelperTest {

    private static final int INSERT_ERROR = -1;
    private static final int NONE_TABLE_CREATED = 0;
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
    public void sqliteHelper_sameGetInstante_isTrue() {

        AdminiBotSQLiteOpenHelper expectedAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);

        assertEquals(expectedAdminiBotSQLiteOpenHelper, mAdminiBotSQLiteOpenHelper);
    }

    @Test
    public void sqliteHelper_creationDB_isTrue() {

        File db = mContext.getDatabasePath(AdminiBotSQLiteOpenHelper.DATABASE_NAME);

        assertTrue(db.exists());

    }

    @Test
    public void sqliteHelper_versionDB_isTrue() {

        int version = mAdminiBotSQLiteOpenHelper.getReadableDatabase().getVersion();
        mAdminiBotSQLiteOpenHelper.close();

        assertEquals(AdminiBotSQLiteOpenHelper.DATABASE_VERSION, version);
    }
}
