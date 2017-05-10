package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.enums.CardTypeEnum;
import io.dojogeek.adminibot.enums.TypePaymentMethodEnum;
import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankCardModel;
import io.dojogeek.adminibot.sqlite.BankCardsContract;

public class BankCardDaoImpl extends SQLiteGlobalDao implements BankCardDao {

    @Inject
    public BankCardDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long create(BankCardModel bankCardModel) throws SQLException {

        openConnection();

        ContentValues contentValues = createContentValues(bankCardModel);
        contentValues.put(BankCardsContract.BankCard.COLUMN_CREATED_AT,
                new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        return mDatabase.insertOrThrow(BankCardsContract.BankCard.TABLE_NAME,
                BankCardsContract.BankCard.COLUMN_NAME_NULLABLE, contentValues);
    }

    @Override
    public List<BankCardModel> getByCartType(String type) {

        openConnection();

        String [] args = {type};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + BankCardsContract.BankCard.TABLE_NAME +
                " WHERE card_type_id = ? ", args);


        List<BankCardModel> bankCardModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                BankCardModel bankCardModel = getBankCardModelFromCursor(cursor);
                bankCardModelList.add(bankCardModel);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return bankCardModelList;
    }

    @Override
    public List<TypePaymentMethodEnum> getRegisteredTypes() {

        openConnection();

        List<TypePaymentMethodEnum> paymentMethods = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("SELECT card_type FROM bank_cards GROUP BY card_type", null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                String bankCardType = cursor.getString(cursor.
                        getColumnIndex(BankCardsContract.BankCard.COLUMN_CARD_TYPE));

                paymentMethods.add(TypePaymentMethodEnum.valueOf(bankCardType));

                cursor.moveToNext();
            }
        }

        cursor.close();

        return paymentMethods;
    }

    @Override
    public BankCardModel getById(long id) throws DataException {

        openConnection();

        String [] args = {String.valueOf(id)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + BankCardsContract.BankCard.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (cursor.getCount() == NO_DATA) {
            throw new DataException("no data!");
        }

        BankCardModel bankCardModel = new BankCardModel();

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                bankCardModel = getBankCardModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        cursor.close();

        return bankCardModel;
    }

    @Override
    public List<BankCardModel> getAll() {

        openConnection();

        List<BankCardModel> bankCardModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(BankCardsContract.BankCard.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                BankCardModel bankCardModel = getBankCardModelFromCursor(cursor);

                bankCardModelList.add(bankCardModel);

                cursor.moveToNext();
            }
        }

        cursor.close();

        return bankCardModelList;
    }

    @Override
    public long update(BankCardModel bankCardModel, long id) {

        openConnection();

        bankCardModel.setUpdatedAt(new DateTime());

        ContentValues contentValues = createContentValues(bankCardModel);
        contentValues.put(BankCardsContract.BankCard.COLUMN_UPDATED_AT,
                new DateTime().toString("yyyy-MM-dd HH:mm:ss"));

        String where = BankCardsContract.BankCard._ID + "= " +  id;

        return (long) mDatabase.update(BankCardsContract.BankCard.TABLE_NAME, contentValues, where, null);
    }

    @Override
    public long delete(long id) {

        openConnection();

        String [] arg = {String.valueOf(id)};

        return mDatabase.delete(BankCardsContract.BankCard.TABLE_NAME,
                BankCardsContract.BankCard._ID + "= ?", arg);
    }

    private ContentValues createContentValues(BankCardModel bankCardModel) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(BankCardsContract.BankCard.COLUMN_NAME, bankCardModel.getName());
        contentValues.put(BankCardsContract.BankCard.COLUMN_NUMBER, bankCardModel.getNumber());
        contentValues.put(BankCardsContract.BankCard.COLUMN_BANK_ID, bankCardModel.getBankId());
        contentValues.put(BankCardsContract.BankCard.COLUMN_BRAND, bankCardModel.getBrand());
        contentValues.put(BankCardsContract.BankCard.COLUMN_CARD_TYPE, bankCardModel.getCardType());
        contentValues.put(BankCardsContract.BankCard.COLUMN_CREDIT_AVAILABLE,
                bankCardModel.getAvailableCredit().toString());

        return contentValues;
    }

    private BankCardModel getBankCardModelFromCursor(Cursor cursor) {

        long id = cursor.getLong(cursor.
                getColumnIndex(BankCardsContract.BankCard._ID));
        String name = cursor.getString(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_NAME));
        String number = cursor.getString(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_NUMBER));
        long bankId = cursor.getLong(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_BANK_ID));
        String brand = cursor.getString(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_BRAND));
        String availableCredit = cursor.getString(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_CREDIT_AVAILABLE));
        String type = cursor.getString(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_CARD_TYPE));
        String createdAt = cursor.getString(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_CREATED_AT));
        String updatedAt = cursor.getString(cursor.
                getColumnIndex(BankCardsContract.BankCard.COLUMN_UPDATED_AT));

        DateTime updated =
                updatedAt == null? null :
                        DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(updatedAt);

        BankCardModel bankCardModel = new BankCardModel();
        bankCardModel.setId(id);
        bankCardModel.setName(name);
        bankCardModel.setNumber(number);
        bankCardModel.setBankId(bankId);
        bankCardModel.setBrand(brand);
        bankCardModel.setCardType(type);
        bankCardModel.setCreatedAt(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").
                parseDateTime(createdAt));
        bankCardModel.setUpdatedAt(updated);
        bankCardModel.setAvailableCredit(new BigDecimal(availableCredit));

        return bankCardModel;
    }
}
