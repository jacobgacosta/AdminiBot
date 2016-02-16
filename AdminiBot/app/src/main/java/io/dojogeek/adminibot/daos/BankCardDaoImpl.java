package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public BankCardModel getBankCardById(long cardBankId) {

        String [] args = {String.valueOf(cardBankId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + BankCardsContract.BankCard.TABLE_NAME +
                " WHERE _ID = ? ", args);

        BankCardModel bankCardModel = new BankCardModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                bankCardModel = getBankCardModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return bankCardModel;
    }

    @Override
    public List<BankCardModel> getBankCards() {

        List<BankCardModel> bankCardModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(BankCardsContract.BankCard.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                BankCardModel bankCardModel = getBankCardModelFromCursor(cursor);

                bankCardModelList.add(bankCardModel);

                cursor.moveToNext();
            }
        }

        return bankCardModelList;
    }

    @Override
    public long updateBankCard(BankCardModel bankCardModel, String where) {

        ContentValues contentValues = createContentValues(bankCardModel);

        long updatedRows = mDatabase.update(BankCardsContract.BankCard.TABLE_NAME, contentValues, where, null);

        return updatedRows;

    }

    @Override
    public long deleteBankCard(long bankCardId) {
        String [] arg = {String.valueOf(bankCardId)};

        int deletedRows = mDatabase.delete(BankCardsContract.BankCard.TABLE_NAME,
                BankCardsContract.BankCard._ID + "= ?", arg);

        return deletedRows;
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

    private BankCardModel getBankCardModelFromCursor(Cursor cursor) {

        BankCardModel bankCardModel = new BankCardModel();

        long id = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard._ID));
        String name = cursor.getString(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_NAME));
        String number = cursor.getString(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_NUMBER));
        long bankId = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_BANK_ID));
        long trademarkId = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_TRADEMARK_ID));
        double availableCredit = cursor.getDouble(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_CREDIT_AVAILABLE));
        long cardTypeId = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_CARD_TYPE_ID));
        long userId = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_USER_ID));

        bankCardModel.id = id;
        bankCardModel.name = name;
        bankCardModel.number = number;
        bankCardModel.bankId = bankId;
        bankCardModel.trademarkId = trademarkId;
        bankCardModel.availableCredit = availableCredit;
        bankCardModel.cardTypeId = cardTypeId;
        bankCardModel.userId = userId;

        return bankCardModel;

    }
}
