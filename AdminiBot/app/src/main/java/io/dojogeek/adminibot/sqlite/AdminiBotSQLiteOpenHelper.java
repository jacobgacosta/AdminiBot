package io.dojogeek.adminibot.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL(UsersContract.SQL_CREATE_TABLE);
        db.execSQL(ExpensesTypesContract.SQL_CREATE_TABLE);
        db.execSQL(ExpensesContract.SQL_CREATE_TABLE);
        db.execSQL(TypesPaymentMethodsContract.SQL_CREATE_TABLE);
        db.execSQL(PaymentMethodsContract.SQL_CREATE_TABLE);
        db.execSQL(ExpensesPaymentMethodsContract.SQL_CREATE_TABLE);
        db.execSQL(IncomesContract.SQL_CREATE_TABLE);
        db.execSQL(IncomesPaymentMethodsContract.SQL_CREATE_TABLE);
        db.execSQL(BankCardsContract.SQL_CREATE_TABLE);
    }

    private void deleteTables(SQLiteDatabase db) {
        db.execSQL(UsersContract.SQL_DELETE_ENTRIES);
        db.execSQL(ExpensesTypesContract.SQL_DELETE_ENTRIES);
        db.execSQL(ExpensesContract.SQL_DELETE_ENTRIES);
        db.execSQL(TypesPaymentMethodsContract.SQL_DELETE_ENTRIES);
        db.execSQL(PaymentMethodsContract.SQL_DELETE_ENTRIES);
        db.execSQL(ExpensesPaymentMethodsContract.SQL_DELETE_ENTRIES);
        db.execSQL(IncomesContract.SQL_DELETE_ENTRIES);
        db.execSQL(IncomesPaymentMethodsContract.SQL_DELETE_ENTRIES);
        db.execSQL(BankCardsContract.SQL_DELETE_ENTRIES);
    }

    private void insertInitialValues(SQLiteDatabase db) {

        insertExpensesTypes(db);
        insertPaymentMethods(db);
    }

    private void insertExpensesTypes(SQLiteDatabase db) {
        db.execSQL(ExpensesTypesContract.SQL_INSERT_INITIAL_VALUES_1);
        db.execSQL(ExpensesTypesContract.SQL_INSERT_INITIAL_VALUES_2);
        db.execSQL(ExpensesTypesContract.SQL_INSERT_INITIAL_VALUES_3);
        db.execSQL(ExpensesTypesContract.SQL_INSERT_INITIAL_VALUES_4);
        db.execSQL(ExpensesTypesContract.SQL_INSERT_INITIAL_VALUES_5);
    }

    private void insertPaymentMethods(SQLiteDatabase db) {
        db.execSQL(TypesPaymentMethodsContract.SQL_INSERT_INITIAL_VALUES_1);
        db.execSQL(TypesPaymentMethodsContract.SQL_INSERT_INITIAL_VALUES_2);
        db.execSQL(TypesPaymentMethodsContract.SQL_INSERT_INITIAL_VALUES_3);
    }

}
