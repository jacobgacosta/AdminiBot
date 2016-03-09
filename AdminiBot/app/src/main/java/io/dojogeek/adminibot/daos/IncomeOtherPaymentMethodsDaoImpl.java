package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeOtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.IncomesOthersPaymentMethodsContract;

public class IncomeOtherPaymentMethodsDaoImpl extends SQLiteGlobalDao implements IncomeOtherPaymentMethodsDao {

    public IncomeOtherPaymentMethodsDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createIncomeOtherPaymentMethod(IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel) {

        openConnection();

        ContentValues contentValues = createContentValues(incomeOtherPaymentMethodModel);

        long result = mDatabase.insert(IncomesOthersPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                IncomesOthersPaymentMethodsContract.IncomePaymentMethod.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    @Override
    public IncomeOtherPaymentMethodModel getIncomeOtherPaymentMethodById(long incomeOtherPaymentMethodModelId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(incomeOtherPaymentMethodModelId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +
                        IncomesOthersPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME + " WHERE _ID = ? ",
                args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel = new IncomeOtherPaymentMethodModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                incomeOtherPaymentMethodModel = getIncomeOtherPaymentMethodModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return incomeOtherPaymentMethodModel;
    }

    @Override
    public List<IncomeOtherPaymentMethodModel> getIncomesOthersPaymentMethods() {

        openConnection();

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(IncomesOthersPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel =
                        getIncomeOtherPaymentMethodModelFromCursor(cursor);

                incomeOtherPaymentMethodModelList.add(incomeOtherPaymentMethodModel);

                cursor.moveToNext();
            }
        }

        return incomeOtherPaymentMethodModelList;
    }

    @Override
    public List<IncomeOtherPaymentMethodModel> getIncomeOtherPaymentMethodByIncomeId(long incomeId) {

        openConnection();

        String [] args = {String.valueOf(incomeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesOthersPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME +
                " WHERE income_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList =
                fillIncomeOtherPaymentMethodModelList(cursor);

        return incomeOtherPaymentMethodModelList;
    }

    @Override
    public List<IncomeOtherPaymentMethodModel> getIncomeOtherPaymentMethodByPaymentMethodId(long paymentMethodId) {

        openConnection();

        String [] args = {String.valueOf(paymentMethodId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesOthersPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME +
                " WHERE payment_method_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList =
                fillIncomeOtherPaymentMethodModelList(cursor);

        return incomeOtherPaymentMethodModelList;
    }

    @Override
    public long deleteIncomeOtherPaymentMethod(long incomeOtherPaymentMethodModelId) {

        openConnection();

        String [] arg = {String.valueOf(incomeOtherPaymentMethodModelId)};

        int deletedRows = mDatabase.delete(IncomesOthersPaymentMethodsContract.IncomePaymentMethod.TABLE_NAME,
                IncomesOthersPaymentMethodsContract.IncomePaymentMethod._ID + "= ?", arg);

        return deletedRows;
    }

    private ContentValues createContentValues(IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesOthersPaymentMethodsContract.IncomePaymentMethod.COLUMN_AMOUNT,
                incomeOtherPaymentMethodModel.amount);
        contentValues.put(IncomesOthersPaymentMethodsContract.IncomePaymentMethod.COLUMN_INCOME_ID,
                incomeOtherPaymentMethodModel.incomeId);
        contentValues.put(IncomesOthersPaymentMethodsContract.IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID,
                incomeOtherPaymentMethodModel.otherPaymentMethodId);

        return contentValues;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }

    private IncomeOtherPaymentMethodModel getIncomeOtherPaymentMethodModelFromCursor(Cursor cursor) {

        double amount = cursor.getDouble(cursor.getColumnIndex(IncomesOthersPaymentMethodsContract.
                IncomePaymentMethod.COLUMN_AMOUNT));
        long incomeId = cursor.getLong(cursor.getColumnIndex(IncomesOthersPaymentMethodsContract.
                IncomePaymentMethod.COLUMN_INCOME_ID));
        long paymentMethodId =
                cursor.getLong(cursor.getColumnIndex(IncomesOthersPaymentMethodsContract.
                        IncomePaymentMethod.COLUMN_PAYMENTH_METHOD_ID));

        IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel = new IncomeOtherPaymentMethodModel();
        incomeOtherPaymentMethodModel.amount = amount;
        incomeOtherPaymentMethodModel.incomeId = incomeId;
        incomeOtherPaymentMethodModel.otherPaymentMethodId = paymentMethodId;

        return incomeOtherPaymentMethodModel;
    }

    private List<IncomeOtherPaymentMethodModel> fillIncomeOtherPaymentMethodModelList(Cursor cursor) {

        List<IncomeOtherPaymentMethodModel> incomeOtherPaymentMethodModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeOtherPaymentMethodModel incomeOtherPaymentMethodModel =
                        getIncomeOtherPaymentMethodModelFromCursor(cursor);
                incomeOtherPaymentMethodModelList.add(incomeOtherPaymentMethodModel);
                cursor.moveToNext();
            }
        }

        return incomeOtherPaymentMethodModelList;

    }
}
