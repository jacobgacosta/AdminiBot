package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.ExpenseTypeModel;
import io.dojogeek.adminibot.sqlite.ExpenseTypesContract;

public class ExpenseTypeDaoImpl extends SQLiteGlobalDao implements ExpenseTypeDao {

    @Inject
    public ExpenseTypeDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createExpenseType(ExpenseTypeModel expenseType) {

        ContentValues contentValues = createContentValues(expenseType);

        long insertedRecordId = mDatabase.insert(ExpenseTypesContract.ExpenseType.TABLE_NAME, ExpenseTypesContract.ExpenseType.COLUMN_NAME_NULLABLE, contentValues);

        return insertedRecordId;
    }

    @Override
    public List<ExpenseTypeModel> getExpensesTypes() {

        List<ExpenseTypeModel> expenseTypeModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpenseTypesContract.ExpenseType.TABLE_NAME, null, null, null, null, null, null);

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

        long id = cursor.getInt(cursor.getColumnIndex(ExpenseTypesContract.ExpenseType._ID));
        int name = cursor.getInt(cursor.getColumnIndex(ExpenseTypesContract.ExpenseType.COLUMN_NAME));
        int description = cursor.getInt(cursor.getColumnIndex(ExpenseTypesContract.ExpenseType.COLUMN_DESCRIPTION));

        expenseTypeModel.id = id;
        expenseTypeModel.name = name;
        expenseTypeModel.description = description;

        return expenseTypeModel;

    }

    private ContentValues createContentValues(ExpenseTypeModel expenseTypeModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpenseTypesContract.ExpenseType.COLUMN_NAME, expenseTypeModel.name);
        contentValues.put(ExpenseTypesContract.ExpenseType.COLUMN_DESCRIPTION, expenseTypeModel.description);

        return contentValues;
    }

}
