package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.enums.BankEnum;
import io.dojogeek.adminibot.enums.ExpenseTypeEnum;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class BanksSQLiteOpenHelperInsertionTest {

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
    public void sqliteHelper_numberOfInsertedBanks_isEquals() {

        final int insertedInitialValues = 12;

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + BanksContract.Bank.TABLE_NAME,
                null);

        assertEquals(insertedInitialValues, cursor.getCount());
    }

    @Test
    public void sqliteHelper_recordMatching_isEquals() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + BanksContract.Bank.TABLE_NAME, null);

        assertNotNull(cursor);
        assertThat(cursor.getCount(), greaterThan(0));

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }
    }

    private void compareResultQueryFields(Cursor currentPosition) {

        long id = currentPosition.getInt(currentPosition.getColumnIndex(BanksContract.Bank._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(BanksContract.Bank.COLUMN_NAME));
        String imageName = currentPosition.getString(currentPosition.getColumnIndex(BanksContract.Bank.COLUMN_IMAGE_NAME));

        int expectedName = BanksContract.BANKS[((int) id - 1)].getName();
        String expectedImageName = BanksContract.BANKS[((int) id - 1)].getImageName();

        assertEquals(expectedName, name);
        assertEquals(expectedImageName, imageName);

    }

    private void loadSQLiteHelper() {
        mAdminiBotSQLiteOpenHelper = AdminiBotSQLiteOpenHelper.getInstance(mContext);
    }

    public SQLiteDatabase openDataBaseConnection() {
        return mAdminiBotSQLiteOpenHelper.getReadableDatabase();
    }

    public void closeDataBaseConnection() {
        mAdminiBotSQLiteOpenHelper.close();
    }
}
