package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.sqlite.BankCardsContract;

public class BankCardDaoImpl extends SQLiteGlobalDao implements BankCardDao {

    public BankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createBankCard(BankCardModel bankCardModel) {

        ContentValues contentValues = createContentValues(bankCardModel);

        long result = mDatabase.insert(BankCardsContract.BankCard.TABLE_NAME,
                BankCardsContract.BankCard.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    private ContentValues createContentValues(BankCardModel bankCardModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(BankCardsContract.BankCard.COLUMN_NAME, bankCardModel.name);
        contentValues.put(BankCardsContract.BankCard.COLUMN_NUMBER, bankCardModel.number);
        contentValues.put(BankCardsContract.BankCard.COLUMN_BANK_ID, bankCardModel.bankId);
        contentValues.put(BankCardsContract.BankCard.COLUMN_TRADEMARK_ID, bankCardModel.trademarkId);
        contentValues.put(BankCardsContract.BankCard.COLUMN_CREDIT_AVAILABLE, bankCardModel.availableCredit);
        contentValues.put(BankCardsContract.BankCard.COLUMN_CARD_TYPE_ID, bankCardModel.cardTypeId);
        contentValues.put(BankCardsContract.BankCard.COLUMN_USER_ID, bankCardModel.userId);

        return contentValues;
    }
}
