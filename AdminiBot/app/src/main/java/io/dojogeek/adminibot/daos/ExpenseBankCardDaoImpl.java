package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.ExpenseBankCardModel;
import io.dojogeek.adminibot.sqlite.ExpensesBankCardsContract;

public class ExpenseBankCardDaoImpl extends SQLiteGlobalDao implements ExpenseBankCardDao {

    public ExpenseBankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createMovementExpenseBankCard(ExpenseBankCardModel expenseBankCardModel) {

        openConnection();

        ContentValues contentValues = createContentValues(expenseBankCardModel);

        long result = mDatabase.insert(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME,
                ExpensesBankCardsContract.ExpensesBankCard.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    @Override
    public ExpenseBankCardModel getMovementExpenseBankCardById(long movementExpenseBankCardModelId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(movementExpenseBankCardModelId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        ExpenseBankCardModel expenseBankCardModel = fillMovementExpenseBankCardModel(cursor);

        return expenseBankCardModel;

    }

    @Override
    public List<ExpenseBankCardModel> getMovementsExpensesBankCards() {

        openConnection();

        List<ExpenseBankCardModel> expenseBankCardModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseBankCardModel expenseBankCardModel = getMovementExpenseBankCardModelFromCursor(cursor);

                expenseBankCardModelList.add(expenseBankCardModel);

                cursor.moveToNext();
            }
        }

        return expenseBankCardModelList;

    }

    @Override
    public long updateMovementExpenseBankCard(ExpenseBankCardModel expenseBankCardModel, String where) {

        openConnection();

        ContentValues contentValues = createContentValues(expenseBankCardModel);

        long updatedRows = mDatabase.update(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long deleteMovementExpenseBankCard(long movementExpenseBankCardModelId) {

        openConnection();

        String [] arg = {String.valueOf(movementExpenseBankCardModelId)};

        int deletedRows = mDatabase.delete(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME,
                ExpensesBankCardsContract.ExpensesBankCard._ID + "= ?", arg);

        return deletedRows;

    }

    @Override
    public List<ExpenseBankCardModel> getMovementsExpensesBankCardsByExpenseId(long expenseId) {

        openConnection();

        String [] args = {String.valueOf(expenseId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME +
                " WHERE expense_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<ExpenseBankCardModel> expenseBankCardModelList = fillMovementExpenseBankCardModelList(cursor);

        return expenseBankCardModelList;

    }

    @Override
    public List<ExpenseBankCardModel> getMovementExpenseBankCardByBankCardId(long bankCardId) {

        openConnection();

        String [] args = {String.valueOf(bankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME +
                " WHERE bank_card_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<ExpenseBankCardModel> expenseBankCardModelList = fillMovementExpenseBankCardModelList(cursor);

        return expenseBankCardModelList;
    }

    private ContentValues createContentValues(ExpenseBankCardModel expenseBankCardModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DESCRIPTION, expenseBankCardModel.getDescription());
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_AMOUNT, expenseBankCardModel.getAmount());
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_EXPENSE_ID, expenseBankCardModel.getExpenseId());
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_BANK_CARD_ID, expenseBankCardModel.getBankCardId());
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DATE, expenseBankCardModel.getDate());

        return contentValues;
    }

    private ExpenseBankCardModel getMovementExpenseBankCardModelFromCursor(Cursor cursor) {

        String description = cursor.getString(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DESCRIPTION));
        double amount = cursor.getDouble(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_AMOUNT));
        long expenseId = cursor.getLong(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_EXPENSE_ID));
        long bankCardId = cursor.getLong(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_BANK_CARD_ID));
        String date = cursor.getString(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DATE));

        ExpenseBankCardModel expenseBankCardModel = new ExpenseBankCardModel();
        expenseBankCardModel.setDescription(description);
        expenseBankCardModel.setAmount(amount);
        expenseBankCardModel.setExpenseId(expenseId);
        expenseBankCardModel.setBankCardId(bankCardId);
        expenseBankCardModel.setDate(date);

        return expenseBankCardModel;
    }

    private ExpenseBankCardModel fillMovementExpenseBankCardModel(Cursor cursor) {

        ExpenseBankCardModel expenseBankCardModel = new ExpenseBankCardModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                expenseBankCardModel = getMovementExpenseBankCardModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return expenseBankCardModel;
    }

    private List<ExpenseBankCardModel> fillMovementExpenseBankCardModelList(Cursor cursor) {

        List<ExpenseBankCardModel> expenseBankCardModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                ExpenseBankCardModel expenseBankCardModel = getMovementExpenseBankCardModelFromCursor(cursor);
                expenseBankCardModelList.add(expenseBankCardModel);
                cursor.moveToNext();
            }
        }

        return expenseBankCardModelList;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
