package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;

import io.dojogeek.adminibot.models.MovementExpenseBankCardModel;
import io.dojogeek.adminibot.sqlite.ExpensesBankCardsContract;

public class MovementExpenseBankCardDaoImpl extends SQLiteGlobalDao implements MovementExpenseBankCardDao {

    public MovementExpenseBankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createMovementExpenseBankCard(MovementExpenseBankCardModel movementExpenseBankCardModel) {

        ContentValues contentValues = createContentValues(movementExpenseBankCardModel);

        long result = mDatabase.insert(ExpensesBankCardsContract.BankCard.TABLE_NAME,
                ExpensesBankCardsContract.BankCard.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    private ContentValues createContentValues(MovementExpenseBankCardModel movementExpenseBankCardModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ExpensesBankCardsContract.BankCard.COLUMN_DESCRIPTION, movementExpenseBankCardModel.description);
        contentValues.put(ExpensesBankCardsContract.BankCard.COLUMN_AMOUNT, movementExpenseBankCardModel.amount);
        contentValues.put(ExpensesBankCardsContract.BankCard.COLUMN_EXPENSE_ID, movementExpenseBankCardModel.expenseId);
        contentValues.put(ExpensesBankCardsContract.BankCard.COLUMN_BANK_CARD_ID, movementExpenseBankCardModel.bankCardId);

        return contentValues;
    }
}
