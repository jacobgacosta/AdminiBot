package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.sqlite.CardDetailContract;

public class CardDetailDaoImpl extends SQLiteGlobalDao implements CardDetailDao  {

    @Inject
    public CardDetailDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createCardDetail(CardDetailModel cardDetailModel) {

        openConnection();

        ContentValues contentValues = createContentValues(cardDetailModel);

        long result = mDatabase.insert(CardDetailContract.CardDetail.TABLE_NAME, CardDetailContract.CardDetail.COLUMN_NAME_NULLABLE,
                contentValues);

        return result;
    }

    @Override
    public CardDetailModel getCardDetailByBankCardId(long bankCardId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(bankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + CardDetailContract.CardDetail.TABLE_NAME +
                " WHERE bank_card_id = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        CardDetailModel cardDetailModel = new CardDetailModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                cardDetailModel = getCardDetailModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return cardDetailModel;
    }

    @Override
    public long updateCardDetail(CardDetailModel cardDetailModel, String where) {

        openConnection();

        ContentValues contentValues = createContentValues(cardDetailModel);

        long updatedRows = mDatabase.update(CardDetailContract.CardDetail.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long deleteCardDetail(long cardDetailId) {

        openConnection();

        String [] arg = {String.valueOf(cardDetailId)};

        int deletedRows = mDatabase.delete(CardDetailContract.CardDetail.TABLE_NAME,
                CardDetailContract.CardDetail._ID + "= ?", arg);

        return deletedRows;
    }

    private ContentValues createContentValues(CardDetailModel cardDetailModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CREDIT_LIMIT, cardDetailModel.getCreditLimit());
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CURRENT_BALANCE, cardDetailModel.getCurrentBalance());
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CUTOFF_DATE, cardDetailModel.getCuttoffDate());
        contentValues.put(CardDetailContract.CardDetail.COLUMN_PAY_DAY_LIMIT, cardDetailModel.getPayDayLimit());
        contentValues.put(CardDetailContract.CardDetail.COLUMN_BANK_CARD_ID, cardDetailModel.getBankCardId());

        return contentValues;
    }

    private CardDetailModel getCardDetailModelFromCursor(Cursor cursor) {

        CardDetailModel cardDetailModel = new CardDetailModel();

        long id = cursor.getLong(cursor.getColumnIndex(CardDetailContract.CardDetail.COLUMN_BANK_CARD_ID));
        double creditLimit = cursor.getDouble(cursor.getColumnIndex(CardDetailContract.CardDetail.COLUMN_CREDIT_LIMIT));
        double currentBalance = cursor.getDouble(cursor.getColumnIndex(CardDetailContract.CardDetail.COLUMN_CURRENT_BALANCE));
        String cutoffDate = cursor.getString(cursor.getColumnIndex(CardDetailContract.CardDetail.COLUMN_CUTOFF_DATE));
        String payDayLimit = cursor.getString(cursor.getColumnIndex(CardDetailContract.CardDetail.COLUMN_PAY_DAY_LIMIT));
        long bankCardId = cursor.getLong(cursor.getColumnIndex(CardDetailContract.CardDetail.COLUMN_BANK_CARD_ID));

        cardDetailModel.setId(id);
        cardDetailModel.setCreditLimit(creditLimit);
        cardDetailModel.setCurrentBalance(currentBalance);
        cardDetailModel.setCuttoffDate(cutoffDate);
        cardDetailModel.setPayDayLimit(payDayLimit);
        cardDetailModel.setBankCardId(bankCardId);

        return cardDetailModel;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
