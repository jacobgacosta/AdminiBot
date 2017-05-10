package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.joda.time.format.DateTimeFormat;

import java.math.BigDecimal;
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
    public long create(IncomeBankCardModel movementIncomeBankCard) {

        openConnection();

        ContentValues contentValues = createContentValues(movementIncomeBankCard);

        long response = mDatabase.insert(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME,
                IncomesBankCardsContract.IncomesBankCards.COLUMN_NAME_NULLABLE, contentValues);

        return response;
    }

    @Override
    public IncomeBankCardModel getById(long id) throws DataException {

        openConnection();

        String [] args = {String.valueOf(id)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + IncomesBankCardsContract.IncomesBankCards.TABLE_NAME +
                " WHERE _ID = ? ", args);

        if (isEmptyResult(cursor)) {
            throw new DataException("no data!");
        }

        IncomeBankCardModel incomeBankCardModel = fillMovementIncomeBankCardModel(cursor);

        return incomeBankCardModel;
    }

    @Override
    public List<IncomeBankCardModel> get() {

        openConnection();

        List<IncomeBankCardModel> incomeBankCardModels = new ArrayList<>();

        Cursor cursor = mDatabase.query(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeBankCardModel incomeBankCardModel = getIncomeBankCardFromCursor(cursor);

                incomeBankCardModels.add(incomeBankCardModel);

                cursor.moveToNext();
            }
        }

        return incomeBankCardModels;
    }

    @Override
    public List<IncomeBankCardModel> getByIncomeId(long incomeId) {

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
    public List<IncomeBankCardModel> getByBankCardId(long bankCardId) {

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
    public long update(IncomeBankCardModel incomeBankCardModel,
                       String where) {

        openConnection();

        ContentValues contentValues = createContentValues(incomeBankCardModel);

        long updatedRows = mDatabase.update(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME, contentValues, where, null);

        return updatedRows;
    }

    @Override
    public long delete(long movementIncomeBankCardId) {

        openConnection();

        String [] arg = {String.valueOf(movementIncomeBankCardId)};

        int deletedRows = mDatabase.delete(IncomesBankCardsContract.IncomesBankCards.TABLE_NAME,
                IncomesBankCardsContract.IncomesBankCards._ID + "= ?", arg);

        return deletedRows;
    }

    private ContentValues createContentValues(IncomeBankCardModel movementIncomeBankCard) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_AMOUNT,
                movementIncomeBankCard.getAmount().toString());
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_INCOME_ID,
                movementIncomeBankCard.getIncomeId());
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_BANK_CARD_ID,
                movementIncomeBankCard.getBankCardId());
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_CREATED_AT,
                movementIncomeBankCard.getCreatedAt().toString("yyyy-MM-dd HH:mm:ss"));
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_UPDATED_AT,
                movementIncomeBankCard.getUpdatedAt().toString("yyyy-MM-dd HH:mm:ss"));
        contentValues.put(IncomesBankCardsContract.IncomesBankCards.COLUMN_DELETED_AT,
                movementIncomeBankCard.getDeletedAt().toString("yyyy-MM-dd HH:mm:ss"));

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

                incomeBankCardModel = getIncomeBankCardFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return incomeBankCardModel;
    }

    private IncomeBankCardModel getIncomeBankCardFromCursor(Cursor cursor) {

        String amount = cursor.getString(
                cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_AMOUNT));
        long incomeId = cursor.getLong(
                cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_INCOME_ID));
        long bankCardId = cursor.getLong(
                cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_BANK_CARD_ID));
        String createdAt = cursor.getString(
                cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_CREATED_AT));
        String updatedAt = cursor.getString(
                cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_UPDATED_AT));
        String deletedAt = cursor.getString(
                cursor.getColumnIndex(IncomesBankCardsContract.IncomesBankCards.COLUMN_DELETED_AT));

        IncomeBankCardModel incomeBankCardModel = new IncomeBankCardModel();
        incomeBankCardModel.setAmount(new BigDecimal(amount));
        incomeBankCardModel.setIncomeId(incomeId);
        incomeBankCardModel.setBankCardId(bankCardId);
        incomeBankCardModel.setCreatedAt(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").
                parseDateTime(createdAt));
        incomeBankCardModel.setUpdatedAt(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").
                parseDateTime(updatedAt));
        incomeBankCardModel.setDeletedAt(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").
                parseDateTime(deletedAt));

        return incomeBankCardModel;
    }

    private List<IncomeBankCardModel> fillMovementIncomeBankCardModelList(Cursor cursor) {

        List<IncomeBankCardModel> movementExpenseBankCardModelList = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                IncomeBankCardModel incomeBankCardModel = getIncomeBankCardFromCursor(cursor);
                movementExpenseBankCardModelList.add(incomeBankCardModel);
                cursor.moveToNext();
            }
        }

        return movementExpenseBankCardModelList;
    }
}
