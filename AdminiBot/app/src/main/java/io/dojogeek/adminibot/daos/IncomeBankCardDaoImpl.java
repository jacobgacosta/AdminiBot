package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.MovementIncomeBankCardModel;
import io.dojogeek.adminibot.sqlite.IncomesBankCardsContract;

public class IncomeBankCardDaoImpl extends SQLiteGlobalDao implements IncomeBankCardDao {

    @Inject
    public IncomeBankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createMovementIncomeBankCard(MovementIncomeBankCardModel movementIncomeBankCard) {

        openConnection();

        ContentValues contentValues = createContentValues(movementIncomeBankCard);

        long response = mDatabase.insert(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME,
                IncomesBankCardsContract.IncomesBankCards.COLUMN_NAME_NULLABLE, contentValues);

        return response;
    }

    @Override
    public MovementIncomeBankCardModel getMovementIncomeBankCardById(long movementIncomeBankCardId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(movementIncomeBankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        MovementIncomeBankCardModel movementIncomeBankCardModel = fillMovementIncomeBankCardModel(cursor);

        return movementIncomeBankCardModel;
    }

    @Override
    public List<MovementIncomeBankCardModel> getMovementsIncomesBankCards() {

        openConnection();

        List<MovementIncomeBankCardModel> movementIncomeBankCardModels = new ArrayList<>();

        Cursor cursor = mDatabase.query(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                MovementIncomeBankCardModel movementIncomeBankCardModel = getMovementIncomeBankCardModelFromCursor(cursor);

                movementIncomeBankCardModels.add(movementIncomeBankCardModel);

                cursor.moveToNext();
            }
        }

        return movementIncomeBankCardModels;
    }

    @Override
    public List<MovementIncomeBankCardModel> getMovementsIncomesBankCardsByIncomeId(long incomeId) {

        openConnection();

        String [] args = {String.valueOf(incomeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE income_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<MovementIncomeBankCardModel> movementExpenseBankCardModelList = fillMovementIncomeBankCardModelList(cursor);

        return movementExpenseBankCardModelList;
    }

    @Override
    public List<MovementIncomeBankCardModel> getMovementsIncomesBankCardsByBankCardId(long bankCardId) {

        openConnection();

        String [] args = {String.valueOf(bankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE bank_card_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<MovementIncomeBankCardModel> movementIncomeBankCardModelList = fillMovementIncomeBankCardModelList(cursor);

        return movementIncomeBankCardModelList;
    }

    @Override
    public long updateMovementIncomeBankCard(MovementIncomeBankCardModel movementIncomeBankCardModel,
                                             String where) {

        openConnection();

        ContentValues contentValues = createContentValues(movementIncomeBankCardModel);

        long updatedRows = mDatabase.update(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long deleteMovementIncomeBankCard(long movementIncomeBankCardId) {

        openConnection();

        String [] arg = {String.valueOf(movementIncomeBankCardId)};

        int deletedRows = mDatabase.delete(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME,
                IncomesBankCardsContract.IncomesBankCards._ID + "= ?", arg);

        return deletedRows;
    }

    private ContentValues createContentValues(MovementIncomeBankCardModel movementIncomeBankCard) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_DESCRIPTION, movementIncomeBankCard.getDescription());
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_AMOUNT, movementIncomeBankCard.getAmount());
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_INCOME_ID, movementIncomeBankCard.getIncomeId());
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_BANK_CARD_ID, movementIncomeBankCard.getBankCardId());
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_DATE, movementIncomeBankCard.getDate());

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
        movementIncomeBankCardModel.setDescription(description);
        movementIncomeBankCardModel.setAmount(amount);
        movementIncomeBankCardModel.setIncomeId(incomeId);
        movementIncomeBankCardModel.setBankCardId(bankCardId);
        movementIncomeBankCardModel.setDate(date);

        return movementIncomeBankCardModel;
    }

    private List<MovementIncomeBankCardModel> fillMovementIncomeBankCardModelList(Cursor cursor) {

        List<MovementIncomeBankCardModel> movementExpenseBankCardModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                MovementIncomeBankCardModel movementIncomeBankCardModel = getMovementIncomeBankCardModelFromCursor(cursor);
                movementExpenseBankCardModelList.add(movementIncomeBankCardModel);
                cursor.moveToNext();
            }
        }

        return movementExpenseBankCardModelList;
    }
}
