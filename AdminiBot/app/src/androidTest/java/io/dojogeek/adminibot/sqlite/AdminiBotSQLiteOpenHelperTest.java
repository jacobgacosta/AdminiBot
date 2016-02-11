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
        deleteDataBase();
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

        int version = mSQLiteDatabase.getVersion();

        assertEquals(AdminiBotSQLiteOpenHelper.DATABASE_VERSION, version);
    }

    @Test
    public void sqliteHelper_creationTables_isTrue() {

        String [] tables = {ExpensesContract.Expense.TABLE_NAME,
                ExpensesPaymentMethodsContract.ExpensesPaymentMethods.TABLE_NAME,
                ExpensesTypesContract.ExpenseType.TABLE_NAME, IncomesContract.Incomes.TABLE_NAME,
                PaymentMethodsContract.PaymentMethods.TABLE_NAME,
                TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME, UsersContract.User.TABLE_NAME};

        for (String table : tables) {
            Cursor cursor = selectTable(table);
            assertNotNull(cursor);
            assertNotEquals(INSERT_ERROR, NONE_TABLE_CREATED);
        }


    }

    private Cursor selectTable(String tableName) {
        String query = "select DISTINCT tbl_name from sqlite_master where tbl_name = '" + tableName +
                "'";
        return mSQLiteDatabase.rawQuery(query, null);
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

    public void deleteDataBase() {
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }
}
