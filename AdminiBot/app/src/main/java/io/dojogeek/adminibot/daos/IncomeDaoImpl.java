package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

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

    private ContentValues createContentValues(IncomeModel incomeModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesContract.Incomes.COLUMN_DESCRIPTION, incomeModel.description);
        contentValues.put(IncomesContract.Incomes.COLUMN_AMOUNT, incomeModel.amount);
        contentValues.put(IncomesContract.Incomes.COLUMN_DATE, incomeModel.date);
        contentValues.put(IncomesContract.Incomes.COLUMN_NEXT_ENTRY, incomeModel.nextDate);
        contentValues.put(IncomesContract.Incomes.COLUMN_USER_ID, incomeModel.userId);

        return contentValues;
    }
}
