package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

import javax.inject.Inject;

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

    private ContentValues createContentValues(MovementIncomeBankCardModel movementIncomeBankCard) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_DESCRIPTION, movementIncomeBankCard.description);
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_AMOUNT, movementIncomeBankCard.amount);
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_INCOME_ID, movementIncomeBankCard.incomeId);
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_BANK_CARD_ID, movementIncomeBankCard.bankCardId);

        return contentValues;
    }
}
