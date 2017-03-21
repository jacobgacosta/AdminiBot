package io.dojogeek.adminibot.daos;

import android.database.Cursor;

import android.content.Context;
import android.content.ContentValues;

import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomePaymentMethodModel;
import io.dojogeek.adminibot.sqlite.IncomesPaymentMethodsContract;

public class IncomePaymentMethodDaoImpl extends SQLiteGlobalDao implements IncomePaymentMethodDao {

    public IncomePaymentMethodDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long delete(long id) {

        openConnection();

        String [] arg = {String.valueOf(id)};

        int deletedRows = mDatabase.delete(IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                IncomesPaymentMethodsContract.IncomePaymentMethod._ID + "= ?", arg);

        return deletedRows;
    }

    @Override
    public List<IncomePaymentMethodModel> get() {

        openConnection();

        List<IncomePaymentMethodModel> incomePaymentMethods = new ArrayList<>();

        Cursor cursor = mDatabase.query(IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomePaymentMethodModel incomePaymentMethod =
                        getIncomePaymentMethodFromCursor(cursor);

                incomePaymentMethods.add(incomePaymentMethod);

                cursor.moveToNext();
            }
        }

        return incomePaymentMethods;
    }

    @Override
    public List<IncomePaymentMethodModel> getByIncomeId(long incomeId) {

        openConnection();

        String [] args = {String.valueOf(incomeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + 
                IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME + 
                " WHERE income_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomePaymentMethodModel> incomePaymentMethods =
                getIncomePaymentMethodsFromCursor(cursor);

        return incomePaymentMethods;
    }

    @Override
    public IncomePaymentMethodModel getById(long id) throws DataException {

        openConnection();

        String [] args = {String.valueOf(id)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +
                        IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME + 
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        IncomePaymentMethodModel incomePaymentMethod = new IncomePaymentMethodModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                incomePaymentMethod = getIncomePaymentMethodFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return incomePaymentMethod;
    }

    @Override
    public long create(IncomePaymentMethodModel incomePaymentMethod) {

        openConnection();

        ContentValues contentValues = createContentValues(incomePaymentMethod);

        long result = mDatabase.insert(IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    @Override
    public List<IncomePaymentMethodModel> getByPaymentMethodId(long paymentMethodId) {

        openConnection();

        String [] args = {String.valueOf(paymentMethodId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + 
                IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME +
                " WHERE payment_method_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomePaymentMethodModel> incomePaymentMethods =
                getIncomePaymentMethodsFromCursor(cursor);

        return incomePaymentMethods;
    }

    private IncomePaymentMethodModel getIncomePaymentMethodFromCursor(Cursor cursor) {

        String amount = cursor.getString(cursor.getColumnIndex(IncomesPaymentMethodsContract.
                IncomePaymentMethod.COLUMN_AMOUNT));
        long incomeId = cursor.getLong(cursor.getColumnIndex(IncomesPaymentMethodsContract.
                IncomePaymentMethod.COLUMN_INCOME_ID));
        long paymentMethodId =
                cursor.getLong(cursor.getColumnIndex(IncomesPaymentMethodsContract.
                        IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID));

        IncomePaymentMethodModel incomePaymentMethod = new IncomePaymentMethodModel();
        incomePaymentMethod.setAmount(new BigDecimal(amount));
        incomePaymentMethod.setIncomeId(incomeId);
        incomePaymentMethod.setPaymentMethodId(paymentMethodId);

        return incomePaymentMethod;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }

    private List<IncomePaymentMethodModel> getIncomePaymentMethodsFromCursor(Cursor cursor) {

        List<IncomePaymentMethodModel> incomePaymentMethods = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomePaymentMethodModel incomePaymentMethod = 
                        getIncomePaymentMethodFromCursor(cursor);
                incomePaymentMethods.add(incomePaymentMethod);
                cursor.moveToNext();
            }
        }

        return incomePaymentMethods;
    }

    private ContentValues createContentValues(IncomePaymentMethodModel incomePaymentMethod) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_AMOUNT,
                incomePaymentMethod.getAmount().toString());
        contentValues.put(IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_INCOME_ID,
                incomePaymentMethod.getIncomeId());
        contentValues.put(IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID,
                incomePaymentMethod.getPaymentMethodId());

        return contentValues;
    }
}
