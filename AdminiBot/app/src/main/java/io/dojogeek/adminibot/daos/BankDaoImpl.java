package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.exceptions.DataException;
import io.dojogeek.adminibot.models.BankModel;
import io.dojogeek.adminibot.sqlite.BankCardsContract;
import io.dojogeek.adminibot.sqlite.BanksContract;

public class BankDaoImpl extends SQLiteGlobalDao implements BankDao {

    @Inject
    public BankDaoImpl(Context context) {
        super(context);
    }

    @Override
    public List<BankModel> getBanks() {

        openConnection();

        List<BankModel> banks = new ArrayList<>();

        Cursor cursor = mDatabase.query(BanksContract.Bank.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                BankModel bankModel = getBankModelFromCursor(cursor);
                banks.add(bankModel);
                cursor.moveToNext();
            }
        }

        return banks;
    }

    @Override
    public BankModel getBankById(long bankId) {

        openConnection();

        String [] args = {String.valueOf(bankId)};

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM " + BanksContract.Bank.TABLE_NAME +
                " WHERE _ID = ? ", args);

        BankModel bankModel = new BankModel();

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                bankModel = getBankModelFromCursor(cursor);
                cursor.moveToNext();
            }
        }

        return bankModel;
    }

    private BankModel getBankModelFromCursor(Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndex(BanksContract.Bank._ID));
        int name = cursor.getInt(cursor.getColumnIndex(BanksContract.Bank.COLUMN_NAME));
        String imageName = cursor.getString(cursor.getColumnIndex(BanksContract.Bank.COLUMN_IMAGE_NAME));

        BankModel bankModel = new BankModel();
        bankModel.setId(id);
        bankModel.setName(name);
        bankModel.setImageName(imageName);

        return bankModel;
    }

    private boolean isEmptyResult(Cursor cursor) {

        if (cursor.getCount() == NO_DATA) {
            return true;
        }

        return false;
    }
}
