package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.sqlite.IncomesContract;

public class IncomeDaoImpl extends SQLiteGlobalDao implements IncomeDao {

    @Inject
    public IncomeDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createIncome(IncomeModel incomeModel) {

        ContentValues contentValues = createContentValues(incomeModel);

        long insertedIncomeId = mDatabase.insert(IncomesContract.Incomes.TABLE_NAME, IncomesContract.Incomes.COLUMN_NAME_NULLABLE, contentValues);

        return insertedIncomeId;
    }

    @Override
    public IncomeModel getIncomeById(long incomeId) {

        String [] args = {String.valueOf(incomeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesContract.Incomes.TABLE_NAME +
                " WHERE _ID = ? ", args);

        IncomeModel incomeModel = new IncomeModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                incomeModel = getIncomeModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return incomeModel;
    }

    @Override
    public List<IncomeModel> getIncomes() {

        List<IncomeModel> incomeModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(IncomesContract.Incomes.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeModel incomeModel = getIncomeModelFromCursor(cursor);

                incomeModelList.add(incomeModel);

                cursor.moveToNext();
            }
        }

        return incomeModelList;
    }

    private ContentValues createContentValues(IncomeModel incomeModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesContract.Incomes.COLUMN_DESCRIPTION, incomeModel.description);
        contentValues.put(IncomesContract.Incomes.COLUMN_AMOUNT, incomeModel.amount);
        contentValues.put(IncomesContract.Incomes.COLUMN_DATE, incomeModel.date);
        contentValues.put(IncomesContract.Incomes.COLUMN_NEXT_ENTRY, incomeModel.nextDate);
        contentValues.put(IncomesContract.Incomes.COLUMN_USER_ID, incomeModel.userId);

        return contentValues;
    }

    private IncomeModel getIncomeModelFromCursor(Cursor cursor) {

        String description = cursor.getString(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_DESCRIPTION));
        double amount = cursor.getDouble(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_AMOUNT));
        String date = cursor.getString(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_DATE));
        String nextEntry = cursor.getString(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_NEXT_ENTRY));
        long userId = cursor.getLong(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_USER_ID));

        IncomeModel incomeModel = new IncomeModel();
        incomeModel.description = description;
        incomeModel.amount = amount;
        incomeModel.date = date;
        incomeModel.nextDate = nextEntry;
        incomeModel.userId = userId;

        return incomeModel;
    }
}
