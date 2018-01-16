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
import static org.junit.Assert.assertEquals;

@Deprecated
@RunWith(AndroidJUnit4.class)
public class CardTypesSQLiteOpenHelperInsertionTest {

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
    public void sqliteHelper_numberOfInsertedCardTypes_isEquals() {

        final int insertedInitialValues = 3;

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + CardTypeContract.CardType.TABLE_NAME,
                null);

        assertEquals(insertedInitialValues, cursor.getCount());

    }

    @Test
    public void sqliteHelper_recordMatching_isEquals() {

        Cursor cursor = mSQLiteDatabase.rawQuery("SELECT * FROM " + CardTypeContract.CardType.TABLE_NAME,
                null);

        if (cursor != null && cursor.moveToFirst()) {

            while (cursor.isAfterLast()) {
                compareResultQueryFields(cursor);
                cursor.moveToNext();
            }
        }
    }

    private void compareResultQueryFields(Cursor currentPosition) {

        long id = currentPosition.getInt(currentPosition.getColumnIndex(CardTypeContract.CardType._ID));
        int name = currentPosition.getInt(currentPosition.getColumnIndex(CardTypeContract.CardType.COLUMN_NAME));
        int description = currentPosition.getInt(currentPosition.getColumnIndex(CardTypeContract.CardType.COLUMN_DESCRIPTION));

        String expectedName = CardTypeContract.CARD_TYPES[((int) id)].getCardType();
        String expectedDescription = CardTypeContract.CARD_TYPES[((int) id)].getDescription();

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