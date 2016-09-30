package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.sqlite.BankCardsContract;

public class BankCardDaoImpl extends SQLiteGlobalDao implements BankCardDao {

    @Inject
    public BankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createBankCard(BankCardModel bankCardModel) {

        openConnection();

        ContentValues contentValues = createContentValues(bankCardModel);

        long result = mDatabase.insert(BankCardsContract.BankCard.TABLE_NAME,
                BankCardsContract.BankCard.COLUMN_NAME_NULLABLE, contentValues);

        return result;
    }

    @Override
    public BankCardModel getBankCardById(long cardBankId) throws DataException {

        openConnection();

        String [] args = {String.valueOf(cardBankId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + BankCardsContract.BankCard.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

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

        openConnection();

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
    public long updateBankCard(BankCardModel bankCardModel, long id) {

        openConnection();

        ContentValues contentValues = createContentValues(bankCardModel);

        String where = BankCardsContract.BankCard._ID + "= " +  id;

        long updatedRows = mDatabase.update(BankCardsContract.BankCard.TABLE_NAME, contentValues, where, null);

        return updatedRows;

    }

    @Override
    public long deleteBankCard(long bankCardId) {

        openConnection();

        String [] arg = {String.valueOf(bankCardId)};

        int deletedRows = mDatabase.delete(BankCardsContract.BankCard.TABLE_NAME,
                BankCardsContract.BankCard._ID + "= ?", arg);

        return deletedRows;
    }

    @Override
    public List<BankCardModel> getBankCardByCartType(CardTypeEnum cardType) {

        openConnection();

        String [] args = {cardType.name()};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + BankCardsContract.BankCard.TABLE_NAME +
                " WHERE card_type_id = ? ", args);


        List<BankCardModel> bankCardModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                BankCardModel bankCardModel = getBankCardModelFromCursor(cursor);
                bankCardModelList.add(bankCardModel);
                cursor.moveToNext();
            }
        }

        return bankCardModelList;
    }

    private ContentValues createContentValues(BankCardModel bankCardModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(BankCardsContract.BankCard.COLUMN_NAME, bankCardModel.getName());
        contentValues.put(BankCardsContract.BankCard.COLUMN_NUMBER, bankCardModel.getNumber());
        contentValues.put(BankCardsContract.BankCard.COLUMN_BANK_ID, bankCardModel.getBankId());
        contentValues.put(BankCardsContract.BankCard.COLUMN_BRAND, bankCardModel.getBrand());
        contentValues.put(BankCardsContract.BankCard.COLUMN_CREDIT_AVAILABLE, bankCardModel.getAvailableCredit());
        contentValues.put(BankCardsContract.BankCard.COLUMN_CARD_TYPE, bankCardModel.getCardType().name());
        contentValues.put(BankCardsContract.BankCard.COLUMN_USER_ID, bankCardModel.getUserId());

        return contentValues;
    }

    private BankCardModel getBankCardModelFromCursor(Cursor cursor) {

        BankCardModel bankCardModel = new BankCardModel();

        long id = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard._ID));
        String name = cursor.getString(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_NAME));
        String number = cursor.getString(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_NUMBER));
        long bankId = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_BANK_ID));
        String brand = cursor.getString(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_BRAND));
        double availableCredit = cursor.getDouble(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_CREDIT_AVAILABLE));
        String cardType = cursor.getString(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_CARD_TYPE));
        long userId = cursor.getLong(cursor.getColumnIndex(BankCardsContract.BankCard.COLUMN_USER_ID));

        bankCardModel.setId(id);
        bankCardModel.setName(name);
        bankCardModel.setNumber(number);
        bankCardModel.setBankId(bankId);
        bankCardModel.setBrand(brand);
        bankCardModel.setAvailableCredit(availableCredit);
        bankCardModel.setCardType(CardTypeEnum.valueOf(cardType));
        bankCardModel.setUserId(userId);

        return bankCardModel;

    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
