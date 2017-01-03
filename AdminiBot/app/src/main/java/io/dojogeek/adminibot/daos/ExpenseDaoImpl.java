package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseModel;
import io.dojogeek.adminibot.sqlite.ExpensesContract;

public class ExpenseDaoImpl extends SQLiteGlobalDao implements ExpenseDao {

    @Inject
    public ExpenseDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createExpense(ExpenseModel expenseModel) {

        openConnection();

        ContentValues contentValues = createContentValues(expenseModel);

        long response = mDatabase.insertOrThrow(ExpensesContract.Expense.TABLE_NAME, ExpensesContract.Expense.COLUMN_NAME_NULLABLE, contentValues);

        return response;

    }

    @Override
    public long updateExpense(ExpenseModel expenseModel, long expenseId) {

        openConnection();

        ContentValues contentValues = createContentValues(expenseModel);

        String where = ExpensesContract.Expense._ID + "= " +  expenseId;

        long updatedRows = mDatabase.update(ExpensesContract.Expense.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public List<ExpenseModel> getExpenses() {

        openConnection();

        List<ExpenseModel> expenseTypeModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpensesContract.Expense.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseModel expenseModel = getExpenseModelFromCursor(cursor);

                expenseTypeModelList.add(expenseModel);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return expenseTypeModelList;
    }

    @Override
    public ExpenseModel getExpenseById(long expenseId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(expenseId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesContract.Expense.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        ExpenseModel expenseModel = new ExpenseModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                expenseModel = getExpenseModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return expenseModel;
    }

    @Override
    public long deleteExpense(long expenseId) {

        openConnection();

        String [] arg = {String.valueOf(expenseId)};

        int deletedRows = mDatabase.delete(ExpensesContract.Expense.TABLE_NAME,
                ExpensesContract.Expense._ID + "= ?", arg);

        return deletedRows;
    }

    @Override
    public List<ExpenseModel> getExpenseByExpenseTypeId(long expenseTypeId) {

        openConnection();

        String [] args = {String.valueOf(expenseTypeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesContract.Expense.TABLE_NAME +
                " WHERE expense_type_id = ? ", args);

        List<ExpenseModel> expenseModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseModel expenseModel = getExpenseModelFromCursor(cursor);
                expenseModelList.add(expenseModel);
                cursor.moveToNext();
            }
        }

        return expenseModelList;
    }

    private ContentValues createContentValues(ExpenseModel expenseModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensesContract.Expense.COLUMN_DESCRIPTION, expenseModel.getDescription());
        contentValues.put(ExpensesContract.Expense.COLUMN_AMOUNT, expenseModel.getAmount());
        contentValues.put(ExpensesContract.Expense.COLUMN_DATE_EXPEDITURE, expenseModel.getDateExpediture());
        contentValues.put(ExpensesContract.Expense.COLUMN_NEXT_EXIT, expenseModel.getNextExit());
        contentValues.put(ExpensesContract.Expense.COLUMN_EXPENSES_TYPE_ID, expenseModel.getExpenseTypeId());
        contentValues.put(ExpensesContract.Expense.COLUMN_USER_ID, expenseModel.getUserId());

        return contentValues;

    }

    private ExpenseModel getExpenseModelFromCursor(Cursor cursor) {

        ExpenseModel expenseModel = new ExpenseModel();

        long id = cursor.getInt(cursor.getColumnIndex(ExpensesContract.Expense._ID));
        String description = cursor.getString(cursor.getColumnIndex(ExpensesContract.Expense.COLUMN_DESCRIPTION));
        double amount = cursor.getDouble(cursor.getColumnIndex(ExpensesContract.Expense.COLUMN_AMOUNT));
        String dateExpediture = cursor.getString(cursor.getColumnIndex(ExpensesContract.Expense.COLUMN_DATE_EXPEDITURE));
        String nextExit = cursor.getString(cursor.getColumnIndex(ExpensesContract.Expense.COLUMN_NEXT_EXIT));
        long expenseTypeId = cursor.getLong(cursor.getColumnIndex(ExpensesContract.Expense.COLUMN_EXPENSES_TYPE_ID));
        long userId = cursor.getLong(cursor.getColumnIndex(ExpensesContract.Expense.COLUMN_USER_ID));

        expenseModel.setId(id);
        expenseModel.setDescription(description);
        expenseModel.setAmount(amount);
        expenseModel.setDateExpediture(dateExpediture);
        expenseModel.setNextExit(nextExit);
        expenseModel.setExpenseTypeId(expenseTypeId);
        expenseModel.setUserId(userId);

        return expenseModel;

    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
