package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.ExpenseTypeModel;
import sqlite.ExpenseTypeContract;

public class ExpenseTypeDaoImpl extends SQLiteGlobalDao implements ExpenseTypeDao {

    @Inject
    public ExpenseTypeDaoImpl(Context context) {
        super(context);
    }

    @Override
    public List<ExpenseTypeModel> getExpensesTypes() {

        List<ExpenseTypeModel> expenseTypeModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpenseTypeContract.ExpenseType.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseTypeModel expenseTypeModel = getExpenseTypeModelFromCursor(cursor);

                expenseTypeModelList.add(expenseTypeModel);
                cursor.moveToNext();
            }
        }

        return expenseTypeModelList;
    }

    @Override
    public ExpenseTypeModel getExpenseTypeById(long id) {

        return null;
    }

    private ExpenseTypeModel getExpenseTypeModelFromCursor(Cursor cursor) {

        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();

        long id = cursor.getInt(cursor.getColumnIndex(ExpenseTypeContract.ExpenseType._ID));
        String name = cursor.getString(cursor.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_NAME));
        String description = cursor.getString(cursor.getColumnIndex(ExpenseTypeContract.ExpenseType.COLUMN_DESCRIPTION));

        expenseTypeModel.id = id;
        expenseTypeModel.name = name;
        expenseTypeModel.description = description;

        return expenseTypeModel;

    }

}
