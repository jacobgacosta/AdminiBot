package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeTypePaymentMethodModel;
import io.dojogeek.adminibot.sqlite.IncomesPaymentMethodsContract;

public class IncomeTypeTypePaymentMethodDaoImpl extends SQLiteGlobalDao implements
        IncomeTypePaymentMethodDao {

    @Inject
    public IncomeTypeTypePaymentMethodDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long delete(long id) {

        openConnection();

        String [] arg = {String.valueOf(id)};

        int deletedRows = mDatabase.delete(
                IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                IncomesPaymentMethodsContract.IncomePaymentMethod._ID + "= ?", arg);

        return deletedRows;
    }

    @Override
    public List<IncomeTypePaymentMethodModel> getAll() {

        openConnection();

        List<IncomeTypePaymentMethodModel> incomePaymentMethods = new ArrayList<>();

        Cursor cursor = mDatabase.query(IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeTypePaymentMethodModel incomePaymentMethod =
                        getIncomePaymentMethodFromCursor(cursor);

                incomePaymentMethods.add(incomePaymentMethod);

                cursor.moveToNext();
            }
        }

        return incomePaymentMethods;
    }

    @Override
    public List<IncomeTypePaymentMethodModel> getByIncomeId(long incomeId) {

        openConnection();

        String [] args = {String.valueOf(incomeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + 
                IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME + 
                " WHERE income_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomeTypePaymentMethodModel> incomePaymentMethods =
                getIncomePaymentMethodsFromCursor(cursor);

        return incomePaymentMethods;
    }

    @Override
    public IncomeTypePaymentMethodModel getById(long id) throws DataException {

        openConnection();

        String [] args = {String.valueOf(id)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +
                        IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME + 
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        IncomeTypePaymentMethodModel incomePaymentMethod = new IncomeTypePaymentMethodModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                incomePaymentMethod = getIncomePaymentMethodFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return incomePaymentMethod;
    }

    @Override
    public long create(IncomeTypePaymentMethodModel incomePaymentMethod) {

        openConnection();

        ContentValues contentValues = createContentValues(incomePaymentMethod);

        long result = mDatabase.insert(
                IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_NAME_NULLABLE,
                contentValues);

        return result;
    }

    @Override
    public List<IncomeTypePaymentMethodModel> getByTypePaymentMethodId(long typePaymentMethodId) {

        openConnection();

        String [] args = {String.valueOf(typePaymentMethodId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + 
                IncomesPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME +
                " WHERE payment_method_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomeTypePaymentMethodModel> incomePaymentMethods =
                getIncomePaymentMethodsFromCursor(cursor);

        return incomePaymentMethods;
    }

    private IncomeTypePaymentMethodModel getIncomePaymentMethodFromCursor(Cursor cursor) {

//        String amount = cursor.getString(cursor.getColumnIndex(IncomesPaymentMethodsContract.
//                IncomePaymentMethod.COLUMN_AMOUNT));
//        long incomeId = cursor.getLong(cursor.getColumnIndex(IncomesPaymentMethodsContract.
//                IncomePaymentMethod.COLUMN_INCOME_ID));
//        long paymentMethodId =
//                cursor.getLong(cursor.getColumnIndex(IncomesPaymentMethodsContract.
//                        IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID));

        IncomeTypePaymentMethodModel incomePaymentMethod = new IncomeTypePaymentMethodModel();
        incomePaymentMethod.setAmount(null);
        incomePaymentMethod.setIncomeId(0);
        incomePaymentMethod.setPaymentMethodId(0);

        return incomePaymentMethod;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }

    private List<IncomeTypePaymentMethodModel> getIncomePaymentMethodsFromCursor(Cursor cursor) {

        List<IncomeTypePaymentMethodModel> incomePaymentMethods = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeTypePaymentMethodModel incomePaymentMethod =
                        getIncomePaymentMethodFromCursor(cursor);
                incomePaymentMethods.add(incomePaymentMethod);
                cursor.moveToNext();
            }
        }

        return incomePaymentMethods;
    }

    private ContentValues createContentValues(IncomeTypePaymentMethodModel incomePaymentMethod) {

//        ContentValues contentValues = new ContentValues();
//        contentValues.put(IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_AMOUNT,
//                incomePaymentMethod.getAmount().toString());
//        contentValues.put(IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_INCOME_ID,
//                incomePaymentMethod.getIncomeId());
//        contentValues.put(IncomesPaymentMethodsContract.IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID,
//                incomePaymentMethod.getPaymentMethodId());

        return null;
    }
}
