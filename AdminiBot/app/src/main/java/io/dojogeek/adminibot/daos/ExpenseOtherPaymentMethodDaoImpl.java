package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseOtherPaymentMethodModel;
import io.dojogeek.adminibot.sqlite.ExpensesOthersPaymentMethodsContract;

public class ExpenseOtherPaymentMethodDaoImpl extends SQLiteGlobalDao implements ExpenseOtherPaymentMethodDao {

    public ExpenseOtherPaymentMethodDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createExpenseOtherPaymentMethod(ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel) {

        ContentValues contentValues = createContentValues(expenseOtherPaymentMethodModel);

        long response = mDatabase.insert(ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.TABLE_NAME,
                ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.COLUMN_NAME_NULLABLE, contentValues);

        return response;
    }

    @Override
    public ExpenseOtherPaymentMethodModel getExpenseOtherPaymentMethodById(long expenseOtherPaymentMethod) throws DataException {

        String [] args = {String.valueOf(expenseOtherPaymentMethod)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " +
                ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.TABLE_NAME + " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel = new ExpenseOtherPaymentMethodModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                expenseOtherPaymentMethodModel = getExpenseOtherPaymentMethodModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return expenseOtherPaymentMethodModel;
    }

    @Override
    public List<ExpenseOtherPaymentMethodModel> getExpensesOthersPaymentMethods() {

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel = getExpenseOtherPaymentMethodModelFromCursor(cursor);

                expenseOtherPaymentMethodModelList.add(expenseOtherPaymentMethodModel);

                cursor.moveToNext();
            }
        }

        return expenseOtherPaymentMethodModelList;
    }

    @Override
    public List<ExpenseOtherPaymentMethodModel> getExpenseOtherPaymentMethodByExpenseId(long expenseId) {

        String [] args = {String.valueOf(expenseId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.TABLE_NAME +
                " WHERE expense_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModelList = fillExpenseOtherPaymentMethodModelList(cursor);

        return expenseOtherPaymentMethodModelList;
    }

    @Override
    public List<ExpenseOtherPaymentMethodModel> getExpenseOtherPaymentMethodByPaymentMethod(long paymentMethod) {

        String [] args = {String.valueOf(paymentMethod)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.TABLE_NAME +
                " WHERE payment_method_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModelList = fillExpenseOtherPaymentMethodModelList(cursor);

        return expenseOtherPaymentMethodModelList;
    }

    @Override
    public long updateExpenseOtherPaymentMethod(ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel, String where) {

        ContentValues contentValues = createContentValues(expenseOtherPaymentMethodModel);

        long updatedRows = mDatabase.update(ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.TABLE_NAME,
                contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long deleteExpenseOtherPaymentMethod(long expenseOtherPaymentMethod) {

        String [] arg = {String.valueOf(expenseOtherPaymentMethod)};

        int deletedRows = mDatabase.delete(ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.TABLE_NAME,
                ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod._ID + "= ?", arg);

        return deletedRows;
    }

    private ContentValues createContentValues(ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.COLUMN_AMOUNT,
                expenseOtherPaymentMethodModel.getAmount());
        contentValues.put(ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.COLUMN_EXPENSE_ID,
                expenseOtherPaymentMethodModel.getExpenseId());
        contentValues.put(ExpensesOthersPaymentMethodsContract.ExpenseOtherPaymentMethod.COLUMN_PAYMENTH_METHOD_ID,
                expenseOtherPaymentMethodModel.getOtherPaymentMethodId());

        return contentValues;
    }

    private ExpenseOtherPaymentMethodModel getExpenseOtherPaymentMethodModelFromCursor(Cursor cursor) {

        double amount = cursor.getDouble(cursor.getColumnIndex(ExpensesOthersPaymentMethodsContract.
                ExpenseOtherPaymentMethod.COLUMN_AMOUNT));
        long expenseId = cursor.getLong(cursor.getColumnIndex(ExpensesOthersPaymentMethodsContract.
                ExpenseOtherPaymentMethod.COLUMN_EXPENSE_ID));
        long otherPaymentMethodId = cursor.getLong(cursor.getColumnIndex(ExpensesOthersPaymentMethodsContract.
                ExpenseOtherPaymentMethod.COLUMN_PAYMENTH_METHOD_ID));
        long id = cursor.getLong(cursor.getColumnIndex(ExpensesOthersPaymentMethodsContract.
                ExpenseOtherPaymentMethod._ID));

        ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel = new ExpenseOtherPaymentMethodModel();
        expenseOtherPaymentMethodModel.setOtherPaymentMethodId(otherPaymentMethodId);
        expenseOtherPaymentMethodModel.setExpenseId(expenseId);
        expenseOtherPaymentMethodModel.setAmount(amount);
        expenseOtherPaymentMethodModel.setId(id);

        return expenseOtherPaymentMethodModel;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }

    private List<ExpenseOtherPaymentMethodModel> fillExpenseOtherPaymentMethodModelList(Cursor cursor) {

        List<ExpenseOtherPaymentMethodModel> expenseOtherPaymentMethodModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseOtherPaymentMethodModel expenseOtherPaymentMethodModel = getExpenseOtherPaymentMethodModelFromCursor(cursor);
                expenseOtherPaymentMethodModelList.add(expenseOtherPaymentMethodModel);
                cursor.moveToNext();
            }
        }

        return expenseOtherPaymentMethodModelList;

    }
}
