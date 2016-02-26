package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.enums.ExpenseTypeEnum;
import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.sqlite.ExpensesTypesContract;

public class ExpenseTypeDaoImpl extends SQLiteGlobalDao implements ExpenseTypeDao {

    @Inject
    public ExpenseTypeDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createExpenseType(ExpenseTypeModel expenseType) {

        ContentValues contentValues = createContentValues(expenseType);

        long insertedRecordId = mDatabase.insert(ExpensesTypesContract.ExpenseType.TABLE_NAME, ExpensesTypesContract.ExpenseType.COLUMN_NAME_NULLABLE, contentValues);

        return insertedRecordId;
    }

    @Override
    public List<ExpenseTypeModel> getExpensesTypes() {

        List<ExpenseTypeModel> expenseTypeModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpensesTypesContract.ExpenseType.TABLE_NAME, null, null, null, null, null, null);

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

        String [] args = {String.valueOf(id)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesTypesContract.ExpenseType.TABLE_NAME +
                " WHERE _ID = ? ", args);

        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                expenseTypeModel = getExpenseTypeModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return expenseTypeModel;
    }

    @Override
    public long updateExpensetype(ExpenseTypeModel expenseTypeModel, String where) {

        ContentValues contentValues = createContentValues(expenseTypeModel);

        long updatedRecords = mDatabase.update(ExpensesTypesContract.ExpenseType.TABLE_NAME, contentValues, where, null);

        return updatedRecords;
    }

    @Override
    public long deleteExpenseTypeById(long expenseTypeId) {

        String [] arg = {String.valueOf(expenseTypeId)};

        int deletedRows = mDatabase.delete(ExpensesTypesContract.ExpenseType.TABLE_NAME,
                ExpensesTypesContract.ExpenseType._ID + "= ?", arg);

        return deletedRows;
    }

    private ExpenseTypeModel getExpenseTypeModelFromCursor(Cursor cursor) {

        ExpenseTypeModel expenseTypeModel = new ExpenseTypeModel();

        long id = cursor.getInt(cursor.getColumnIndex(ExpensesTypesContract.ExpenseType._ID));
        String name = cursor.getString(cursor.getColumnIndex(ExpensesTypesContract.ExpenseType.COLUMN_NAME));
        String description = cursor.getString(cursor.getColumnIndex(ExpensesTypesContract.ExpenseType.COLUMN_DESCRIPTION));

        expenseTypeModel.id = id;
        expenseTypeModel.name = ExpenseTypeEnum.valueOf(name);
        expenseTypeModel.description = description;

        return expenseTypeModel;

    }

    private ContentValues createContentValues(ExpenseTypeModel expenseTypeModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensesTypesContract.ExpenseType.COLUMN_NAME, expenseTypeModel.name.name());
        contentValues.put(ExpensesTypesContract.ExpenseType.COLUMN_DESCRIPTION, expenseTypeModel.description);

        return contentValues;
    }

}
