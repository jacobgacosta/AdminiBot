package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;
import io.dojogeek.adminibot.sqlite.ExpensesBankCardsContract;

public class ExpenseBankCardDaoImpl extends SQLiteGlobalDao implements ExpenseBankCardDao {

    public ExpenseBankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createMovementExpenseBankCard(MovementExpenseBankCardModel movementExpenseBankCardModel) {

        ContentValues contentValues = createContentValues(movementExpenseBankCardModel);

        long result = mDatabase.insert(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME,
                ExpensesBankCardsContract.ExpensesBankCard.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    @Override
    public MovementExpenseBankCardModel getMovementExpenseBankCardById(long movementExpenseBankCardModelId) throws DataException {

        String [] args = {String.valueOf(movementExpenseBankCardModelId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        MovementExpenseBankCardModel movementExpenseBankCardModel = fillMovementExpenseBankCardModel(cursor);

        return movementExpenseBankCardModel;

    }

    @Override
    public List<MovementExpenseBankCardModel> getMovementsExpensesBankCards() {

        List<MovementExpenseBankCardModel> movementExpenseBankCardModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                MovementExpenseBankCardModel movementExpenseBankCardModel = getMovementExpenseBankCardModelFromCursor(cursor);

                movementExpenseBankCardModelList.add(movementExpenseBankCardModel);

                cursor.moveToNext();
            }
        }

        return movementExpenseBankCardModelList;

    }

    @Override
    public long updateMovementExpenseBankCard(MovementExpenseBankCardModel movementExpenseBankCardModel, String where) {
        ContentValues contentValues = createContentValues(movementExpenseBankCardModel);

        long updatedRows = mDatabase.update(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long deleteMovementExpenseBankCard(long movementExpenseBankCardModelId) {

        String [] arg = {String.valueOf(movementExpenseBankCardModelId)};

        int deletedRows = mDatabase.delete(ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME,
                ExpensesBankCardsContract.ExpensesBankCard._ID + "= ?", arg);

        return deletedRows;

    }

    @Override
    public List<MovementExpenseBankCardModel> getMovementsExpensesBankCardsByExpenseId(long expenseId) {

        String [] args = {String.valueOf(expenseId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME +
                " WHERE expense_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<MovementExpenseBankCardModel> movementExpenseBankCardModelList = fillMovementExpenseBankCardModelList(cursor);

        return movementExpenseBankCardModelList;

    }

    @Override
    public List<MovementExpenseBankCardModel> getMovementExpenseBankCardByBankCardId(long bankCardId) {

        String [] args = {String.valueOf(bankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + ExpensesBankCardsContract.ExpensesBankCard.TABLE_NAME +
                " WHERE bank_card_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<MovementExpenseBankCardModel> movementExpenseBankCardModelList = fillMovementExpenseBankCardModelList(cursor);

        return movementExpenseBankCardModelList;
    }

    private ContentValues createContentValues(MovementExpenseBankCardModel movementExpenseBankCardModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DESCRIPTION, movementExpenseBankCardModel.description);
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_AMOUNT, movementExpenseBankCardModel.amount);
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_EXPENSE_ID, movementExpenseBankCardModel.expenseId);
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_BANK_CARD_ID, movementExpenseBankCardModel.bankCardId);
        contentValues.put(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DATE, movementExpenseBankCardModel.date);

        return contentValues;
    }

    private MovementExpenseBankCardModel getMovementExpenseBankCardModelFromCursor(Cursor cursor) {

        String description = cursor.getString(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DESCRIPTION));
        double amount = cursor.getDouble(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_AMOUNT));
        long expenseId = cursor.getLong(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_EXPENSE_ID));
        long bankCardId = cursor.getLong(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_BANK_CARD_ID));
        String date = cursor.getString(cursor.getColumnIndex(ExpensesBankCardsContract.ExpensesBankCard.COLUMN_DATE));

        MovementExpenseBankCardModel movementExpenseBankCardModel = new MovementExpenseBankCardModel();
        movementExpenseBankCardModel.description = description;
        movementExpenseBankCardModel.amount = amount;
        movementExpenseBankCardModel.expenseId = expenseId;
        movementExpenseBankCardModel.bankCardId = bankCardId;
        movementExpenseBankCardModel.date = date;

        return movementExpenseBankCardModel;
    }

    private MovementExpenseBankCardModel fillMovementExpenseBankCardModel(Cursor cursor) {

        MovementExpenseBankCardModel movementExpenseBankCardModel = new MovementExpenseBankCardModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                movementExpenseBankCardModel = getMovementExpenseBankCardModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return movementExpenseBankCardModel;
    }

    private List<MovementExpenseBankCardModel> fillMovementExpenseBankCardModelList(Cursor cursor) {

        List<MovementExpenseBankCardModel> movementExpenseBankCardModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                MovementExpenseBankCardModel movementExpenseBankCardModel = getMovementExpenseBankCardModelFromCursor(cursor);
                movementExpenseBankCardModelList.add(movementExpenseBankCardModel);
                cursor.moveToNext();
            }
        }

        return movementExpenseBankCardModelList;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
