package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeModel;
import io.dojogeek.adminibot.sqlite.IncomesContract;

public class IncomeDaoImpl extends SQLiteGlobalDao implements IncomeDao {

    @Inject
    public IncomeDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createIncome(IncomeModel incomeModel) {

        openConnection();

        ContentValues contentValues = createContentValues(incomeModel);

        long insertedIncomeId = mDatabase.insertOrThrow(IncomesContract.Incomes.TABLE_NAME, IncomesContract.Incomes.COLUMN_NULLABLE, contentValues);

        return insertedIncomeId;

    }

    @Override
    public IncomeModel getIncomeById(long incomeId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(incomeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesContract.Incomes.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

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

        openConnection();

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

    @Override
    public long updateIncome(IncomeModel incomeModel, String where) {

        openConnection();

        ContentValues contentValues = createContentValues(incomeModel);

        long updatedRows = mDatabase.update(IncomesContract.Incomes.TABLE_NAME, contentValues, where, null);

        return updatedRows;

    }

    @Override
    public long deleteIncome(long incomeId) {

        openConnection();

        String [] arg = {String.valueOf(incomeId)};

        int deletedRows = mDatabase.delete(IncomesContract.Incomes.TABLE_NAME,
                IncomesContract.Incomes._ID + "= ?", arg);

        return deletedRows;

    }

    private ContentValues createContentValues(IncomeModel incomeModel) {

        String nextEntry = incomeModel.getNextEntry() != null ? incomeModel.getNextEntry().toString("yyyy-MM-dd HH:mm:ss")
                                                              : null;

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesContract.Incomes.COLUMN_NAME, incomeModel.getName());
        contentValues.put(IncomesContract.Incomes.COLUMN_CREATED_AT,
                new DateTime().toString("yyyy-MM-dd HH:mm:ss"));
        contentValues.put(IncomesContract.Incomes.COLUMN_NEXT_ENTRY, nextEntry);

        if (incomeModel.getTotalAmount() != null) {
            contentValues.put(IncomesContract.Incomes.COLUMN_AMOUNT, incomeModel.getTotalAmount().toString());
        }

        return contentValues;

    }

    private IncomeModel getIncomeModelFromCursor(Cursor cursor) {

        String name = cursor.getString(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_NAME));
        String amount = cursor.getString(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_AMOUNT));
        String createdAt = cursor.getString(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_CREATED_AT));
        String nextEntry = cursor.getString(cursor.getColumnIndex(IncomesContract.Incomes.COLUMN_NEXT_ENTRY));

        IncomeModel incomeModel = new IncomeModel();
        incomeModel.setName(name);
        incomeModel.setTotalAmount(new BigDecimal(amount));
        incomeModel.setCreatedAt(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(createdAt));
        incomeModel.setNextEntry(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(nextEntry));

        return incomeModel;

    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;

    }
}
