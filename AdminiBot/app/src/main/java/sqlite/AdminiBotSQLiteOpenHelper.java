package sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AdminiBotSQLiteOpenHelper extends SQLiteOpenHelper {

    public static String TAG = "AdminiBotSQLiteOpenHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AdminiBot.db";

    private static AdminiBotSQLiteOpenHelper sInstance = null;

    public static AdminiBotSQLiteOpenHelper getInstance(Context ctx) {

        if (sInstance == null) {
            sInstance = new AdminiBotSQLiteOpenHelper(ctx.getApplicationContext());
        }
        return sInstance;
    }

    private AdminiBotSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTables(db);

        insertInitialValues(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        deleteTables(db);

        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL(UserContract.SQL_CREATE_ENTRIES);
        db.execSQL(ExpenseTypeContract.SQL_CREATE_ENTRIES);
        db.execSQL(ExpenseContract.SQL_CREATE_ENTRIES);
        db.execSQL(PaymentMethodsContract.SQL_CREATE_ENTRIES);
    }

    private void deleteTables(SQLiteDatabase db) {
        db.execSQL(UserContract.SQL_DELETE_ENTRIES);
        db.execSQL(ExpenseTypeContract.SQL_DELETE_ENTRIES);
        db.execSQL(ExpenseContract.SQL_DELETE_ENTRIES);
        db.execSQL(PaymentMethodsContract.SQL_DELETE_ENTRIES);
    }

    private void insertInitialValues(SQLiteDatabase db) {

        insertExpensesTypes(db);
        insertPaymentMethods(db);
    }

    private void insertExpensesTypes(SQLiteDatabase db) {
        db.execSQL(ExpenseTypeContract.SQL_INSERT_INITIAL_VALUES_1);
        db.execSQL(ExpenseTypeContract.SQL_INSERT_INITIAL_VALUES_2);
        db.execSQL(ExpenseTypeContract.SQL_INSERT_INITIAL_VALUES_3);
        db.execSQL(ExpenseTypeContract.SQL_INSERT_INITIAL_VALUES_4);
        db.execSQL(ExpenseTypeContract.SQL_INSERT_INITIAL_VALUES_5);
    }

    private void insertPaymentMethods(SQLiteDatabase db) {
        db.execSQL(PaymentMethodsContract.SQL_INSERT_INITIAL_VALUES_1);
        db.execSQL(PaymentMethodsContract.SQL_INSERT_INITIAL_VALUES_2);
        db.execSQL(PaymentMethodsContract.SQL_INSERT_INITIAL_VALUES_3);
        db.execSQL(PaymentMethodsContract.SQL_INSERT_INITIAL_VALUES_4);
    }

}
