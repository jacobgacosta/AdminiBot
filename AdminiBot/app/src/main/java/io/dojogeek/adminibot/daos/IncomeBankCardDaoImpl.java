package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.IncomeBankCardModel;
import io.dojogeek.adminibot.sqlite.IncomesBankCardsContract;

public class IncomeBankCardDaoImpl extends SQLiteGlobalDao implements IncomeBankCardDao {

    @Inject
    public IncomeBankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createMovementIncomeBankCard(IncomeBankCardModel movementIncomeBankCard) {

        openConnection();

        ContentValues contentValues = createContentValues(movementIncomeBankCard);

        long response = mDatabase.insert(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME,
                IncomesBankCardsContract.IncomesBankCards.COLUMN_NAME_NULLABLE, contentValues);

        return response;
    }

    @Override
    public IncomeBankCardModel getMovementIncomeBankCardById(long movementIncomeBankCardId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(movementIncomeBankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        IncomeBankCardModel incomeBankCardModel = fillMovementIncomeBankCardModel(cursor);

        return incomeBankCardModel;
    }

    @Override
    public List<IncomeBankCardModel> getMovementsIncomesBankCards() {

        openConnection();

        List<IncomeBankCardModel> incomeBankCardModels = new ArrayList<>();

        Cursor cursor = mDatabase.query(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeBankCardModel incomeBankCardModel = getMovementIncomeBankCardModelFromCursor(cursor);

                incomeBankCardModels.add(incomeBankCardModel);

                cursor.moveToNext();
            }
        }

        return incomeBankCardModels;
    }

    @Override
    public List<IncomeBankCardModel> getMovementsIncomesBankCardsByIncomeId(long incomeId) {

        openConnection();

        String [] args = {String.valueOf(incomeId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE income_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomeBankCardModel> movementExpenseBankCardModelList = fillMovementIncomeBankCardModelList(cursor);

        return movementExpenseBankCardModelList;
    }

    @Override
    public List<IncomeBankCardModel> getMovementsIncomesBankCardsByBankCardId(long bankCardId) {

        openConnection();

        String [] args = {String.valueOf(bankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE bank_card_id = ? ", args);

        if (isEmptyResult(cursor)) {
            return new ArrayList<>();
        }

        List<IncomeBankCardModel> incomeBankCardModelList = fillMovementIncomeBankCardModelList(cursor);

        return incomeBankCardModelList;
    }

    @Override
    public long updateMovementIncomeBankCard(IncomeBankCardModel incomeBankCardModel,
                                             String where) {

        openConnection();

        ContentValues contentValues = createContentValues(incomeBankCardModel);

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

    private ContentValues createContentValues(IncomeBankCardModel movementIncomeBankCard) {

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

    private IncomeBankCardModel fillMovementIncomeBankCardModel(Cursor cursor) {

        IncomeBankCardModel incomeBankCardModel = new IncomeBankCardModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                incomeBankCardModel = getMovementIncomeBankCardModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return incomeBankCardModel;
    }

    private IncomeBankCardModel getMovementIncomeBankCardModelFromCursor(Cursor cursor) {

        String description = cursor.getString(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_DESCRIPTION));
        double amount = cursor.getDouble(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_AMOUNT));
        long incomeId = cursor.getLong(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_INCOME_ID));
        long bankCardId = cursor.getLong(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_BANK_CARD_ID));
        String date = cursor.getString(cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_DATE));

        IncomeBankCardModel incomeBankCardModel = new IncomeBankCardModel();
        incomeBankCardModel.setDescription(description);
        incomeBankCardModel.setAmount(amount);
        incomeBankCardModel.setIncomeId(incomeId);
        incomeBankCardModel.setBankCardId(bankCardId);
        incomeBankCardModel.setDate(date);

        return incomeBankCardModel;
    }

    private List<IncomeBankCardModel> fillMovementIncomeBankCardModelList(Cursor cursor) {

        List<IncomeBankCardModel> movementExpenseBankCardModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeBankCardModel incomeBankCardModel = getMovementIncomeBankCardModelFromCursor(cursor);
                movementExpenseBankCardModelList.add(incomeBankCardModel);
                cursor.moveToNext();
            }
        }

        return movementExpenseBankCardModelList;
    }
}
