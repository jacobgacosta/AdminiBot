package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import io.dojogeek.adminibot.models.TrademarkModel;
import io.dojogeek.adminibot.sqlite.TrademarkContract;

public class TrademarkDaoImpl extends SQLiteGlobalDao implements TrademarkDao{

    public TrademarkDaoImpl(Context context) {
        super(context);
    }

    @Override
    public List<TrademarkModel> getTrademarks() {

        openConnection();

        List<TrademarkModel> trademarkModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(TrademarkContract.Trademark.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while(cursor.isAfterLast() == false) {
                TrademarkModel trademarkModel = getTrademarkFromCursor(cursor);

                trademarkModelList.add(trademarkModel);

                cursor.moveToNext();
            }
        }

        return trademarkModelList;
    }

    @Override
    public TrademarkModel getTrademarkById(long id) {
        return null;
    }

    private TrademarkModel getTrademarkFromCursor(Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndex(TrademarkContract.Trademark._ID));
        String name = cursor.getString(cursor.getColumnIndex(TrademarkContract.Trademark.COLUMN_NAME));
        String imageName = cursor.getString(cursor.getColumnIndex(TrademarkContract.Trademark.COLUMN_IMAGE_NAME));

        TrademarkModel trademarkModel = new TrademarkModel();
        trademarkModel.setId(id);
        trademarkModel.setName(name);
        trademarkModel.setImageName(imageName);

        return trademarkModel;
    }
}
