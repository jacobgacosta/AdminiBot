package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementIncomeBankCardModel;
import io.dojogeek.adminibot.sqlite.IncomesBankCardsContract;

public class MovementIncomeBankCardDaoImpl extends SQLiteGlobalDao implements MovementIncomeBankCardDao {

    @Inject
    public MovementIncomeBankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createMovementIncomeBankCard(MovementIncomeBankCardModel movementIncomeBankCard) {

        ContentValues contentValues = createContentValues(movementIncomeBankCard);

        long response = mDatabase.insert(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME,
                IncomesBankCardsContract.IncomesBankCards.COLUMN_NAME_NULLABLE, contentValues);

        return response;
    }

    @Override
    public MovementIncomeBankCardModel getMovementIncomeBankCardById(long movementIncomeBankCardId) throws DataException {

        String [] args = {String.valueOf(movementIncomeBankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        MovementIncomeBankCardModel movementIncomeBankCardModel = fillMovementIncomeBankCardModel(cursor);

        return movementIncomeBankCardModel;
    }

    private ContentValues createContentValues(MovementIncomeBankCardModel movementIncomeBankCard) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_DESCRIPTION, movementIncomeBankCard.description);
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_AMOUNT, movementIncomeBankCard.amount);
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_INCOME_ID, movementIncomeBankCard.incomeId);
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_BANK_CARD_ID, movementIncomeBankCard.bankCardId);
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_DATE, movementIncomeBankCard.date);

        return contentValues;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }

    private MovementIncomeBankCardModel fillMovementIncomeBankCardModel(Cursor cursor) {

        MovementIncomeBankCardModel movementIncomeBankCardModel = new MovementIncomeBankCardModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                movementIncomeBankCardModel = getMovementIncomeBankCardModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return movementIncomeBankCardModel;
    }

    private MovementIncomeBankCardModel getMovementIncomeBankCardModelFromCursor(Cursor cursor) {

        String description = cursor.getString(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_DESCRIPTION));
        double amount = cursor.getDouble(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_AMOUNT));
        long incomeId = cursor.getLong(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_INCOME_ID));
        long bankCardId = cursor.getLong(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_BANK_CARD_ID));
        String date = cursor.getString(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_DATE));

        MovementIncomeBankCardModel movementIncomeBankCardModel = new MovementIncomeBankCardModel();
        movementIncomeBankCardModel.description = description;
        movementIncomeBankCardModel.amount = amount;
        movementIncomeBankCardModel.incomeId = incomeId;
        movementIncomeBankCardModel.bankCardId = bankCardId;
        movementIncomeBankCardModel.date = date;

        return movementIncomeBankCardModel;
    }
}
