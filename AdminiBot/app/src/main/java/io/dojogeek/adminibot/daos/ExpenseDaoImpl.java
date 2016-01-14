package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.ExpenseModel;
import sqlite.ExpenseContract;

public class ExpenseDaoImpl extends SQLiteGlobalDao implements ExpenseDao {

    @Inject
    public ExpenseDaoImpl(Context context) {
        super(context);
    }

    @Override
    public boolean createExpense(ExpenseModel expenseModel) {

        ContentValues contentValues = createContentValues(expenseModel);

        long response = mDatabase.insert(ExpenseContract.Expense.TABLE_NAME, ExpenseContract.Expense.COLUMN_NAME_NULLABLE, contentValues);

        return isValidResponse(response);

    }

    @Override
    public List<ExpenseModel> getExpenses() {

        List<ExpenseModel> expenseTypeModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpenseContract.Expense.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseModel expenseModel = getExpenseModelFromCursor(cursor);

                expenseTypeModelList.add(expenseModel);

                cursor.moveToNext();
            }
        }

        return expenseTypeModelList;
    }

    private ContentValues createContentValues(ExpenseModel expenseModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseContract.Expense.COLUMN_NAME, expenseModel.name);
        contentValues.put(ExpenseContract.Expense.COLUMN_USER_ID, expenseModel.userId);
        contentValues.put(ExpenseContract.Expense.COLUMN_EXPENSES_TYPE_ID, expenseModel.expenseTypeId);
        contentValues.put(ExpenseContract.Expense.COLUMN_AMOUNT, expenseModel.totalAmount);
        contentValues.put(ExpenseContract.Expense.COLUMN_DATE_EXPEDITURE, expenseModel.dataExpediture);

        return contentValues;

    }

    private ExpenseModel getExpenseModelFromCursor(Cursor cursor) {

        ExpenseModel expenseTypeModel = new ExpenseModel();

        long id = cursor.getInt(cursor.getColumnIndex(ExpenseContract.Expense._ID));
        String name = cursor.getString(cursor.getColumnIndex(ExpenseContract.Expense.COLUMN_NAME));
        String amount = cursor.getString(cursor.getColumnIndex(ExpenseContract.Expense.COLUMN_AMOUNT));
        String dateExpediture = cursor.getString(cursor.getColumnIndex(ExpenseContract.Expense.COLUMN_DATE_EXPEDITURE));
        long expenseTypeId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.Expense.COLUMN_EXPENSES_TYPE_ID));
        long userId = cursor.getLong(cursor.getColumnIndex(ExpenseContract.Expense.COLUMN_USER_ID));

        expenseTypeModel.id = id;
        expenseTypeModel.name = name;
        expenseTypeModel.totalAmount = amount;
        expenseTypeModel.dataExpediture = dateExpediture;
        expenseTypeModel.expenseTypeId = expenseTypeId;
        expenseTypeModel.userId = userId;

        return expenseTypeModel;

    }

}
