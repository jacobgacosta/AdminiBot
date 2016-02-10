package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class AdminiBotSQLiteOpenHelperTest {

    private AdminiBotSQLiteOpenHelper mAdminiBotSQLiteOpenHelper;
    private Context mContext;

    @Before
    public void setup() {

        mContext = getTargetContext();
        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);

    }

    @Test
    public void sqliteHelper_sameGetInstante_isTrue() {

        AdminiBotSQLiteOpenHelper expectedAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);

        assertEquals(expectedAdminiBotSQLiteOpenHelper, mAdminiBotSQLiteOpenHelper);
    }

}
