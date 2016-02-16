package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

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

    private ContentValues createContentValues(CardDetailModel cardDetailModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CREDIT_LIMIT, cardDetailModel.creditLimit);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CURRENT_BALANCE, cardDetailModel.currentBalance);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_CUTOFF_DATE, cardDetailModel.cuttoffDate);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_PAY_DAY_LIMIT, cardDetailModel.payDayLimit);
        contentValues.put(CardDetailContract.CardDetail.COLUMN_BANK_CARD_ID, cardDetailModel.bankCardId);

        return contentValues;
    }
}
