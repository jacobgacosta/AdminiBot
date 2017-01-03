package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.TypePaymentMethodModel;
import io.dojogeek.adminibot.sqlite.TypesPaymentMethodsContract;

@Deprecated
public class TypesPaymentMethodsDaoImpl extends SQLiteGlobalDao implements TypesPaymentMethodsDao {

    @Inject
    public TypesPaymentMethodsDaoImpl(Context context) {
        super(context);
    }

    @Override
    public List<TypePaymentMethodModel> getPaymentMethods() {

        List<TypePaymentMethodModel> paymentMethodModelList = new ArrayList<>();

        Cursor cursor = mDatabase.query(TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {

            while (cursor.isAfterLast() == false) {

                TypePaymentMethodModel typesPaymentMethodModel = getPaymentMethodModelFromCursor(cursor);

                paymentMethodModelList.add(typesPaymentMethodModel);
                cursor.moveToNext();
            }
        }

        return paymentMethodModelList;
        
    }

    @Override
    public long createPaymentMethod(TypePaymentMethodModel typesPaymentMethods) {

        ContentValues contentValues = createContentValues(typesPaymentMethods);

        long insertedRecordId = mDatabase.insert(TypesPaymentMethodsContract.TypePaymentMethod.TABLE_NAME, TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_NAME_NULLABLE, contentValues);

        return insertedRecordId;
    }

    private TypePaymentMethodModel getPaymentMethodModelFromCursor(Cursor cursor) {

        TypePaymentMethodModel paymentMethodModel = new TypePaymentMethodModel();

        long id = cursor.getInt(cursor.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod._ID));
        String name = cursor.getString(cursor.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_NAME));
        String description = cursor.getString(cursor.getColumnIndex(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_DESCRIPTION));

        /*paymentMethodModel.id = id;
        paymentMethodModel.name = TypePaymentMethodEnum.valueOf(name);
        paymentMethodModel.description = TypePaymentMethodEnum.valueOf(description);*/

        return paymentMethodModel;

    }

    private ContentValues createContentValues(TypePaymentMethodModel paymentMethod) {

        ContentValues contentValues = new ContentValues();
        /*contentValues.put(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_NAME, paymentMethod.name.name());
        contentValues.put(TypesPaymentMethodsContract.TypePaymentMethod.COLUMN_DESCRIPTION, paymentMethod.description.name());*/

        return contentValues;
    }

}
