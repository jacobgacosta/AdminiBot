package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.CardDetailModel;
import io.dojogeek.adminibot.sqlite.CardDetailContract;

public class CardDetailDaoImpl extends SQLiteGlobalDao implements CardDetailDao  {

    @Inject
    public CardDetailDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createCardDetail(CardDetailModel cardDetailModel) {

        ContentValues contentValues = createContentValues(cardDetailModel);

        long result = mDatabase.insert(CardDetailContract.CardDetail.TABLE_NAME, CardDetailContract.CardDetail.COLUMN_NAME_NULLABLE,
                contentValues);

        return result;
    }

    @Override
    public CardDetailModel getCardDetailById(long cardDetailId) {

        String [] args = {String.valueOf(cardDetailId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + CardDetailContract.CardDetail.TABLE_NAME +
                " WHERE _ID = ? ", args);

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
    public CardDetailModel getCardDetailByBankCardId(long bankCardId) {
        String [] args = {String.valueOf(bankCardId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + CardDetailContract.CardDetail.TABLE_NAME +
                " WHERE bank_card_id = ? ", args);

        CardDetailModel cardDetailModel = new CardDetailModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                cardDetailModel = getCardDetailModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return cardDetailModel;
    }

    private ContentValues createContentValues(CardDetailModel cardDetailModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CREDIT_LIMIT, cardDetailModel.creditLimit);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CURRENT_BALANCE, cardDetailModel.currentBalance);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CUTOFF_DATE, cardDetailModel.cuttoffDate);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_PAY_DAY_LIMIT, cardDetailModel.payDayLimit);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_BANK_CARD_ID, cardDetailModel.bankCardId);

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

        cardDetailModel.id = id;
        cardDetailModel.creditLimit = creditLimit;
        cardDetailModel.currentBalance = currentBalance;
        cardDetailModel.cuttoffDate = cutoffDate;
        cardDetailModel.payDayLimit = payDayLimit;
        cardDetailModel.bankCardId = bankCardId;

        return cardDetailModel;
    }
}
