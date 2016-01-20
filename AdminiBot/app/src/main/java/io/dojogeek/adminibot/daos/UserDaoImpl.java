package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.UserContract;

public class UserDaoImpl extends SQLiteGlobalDao implements UserDao {

    @Inject
    public UserDaoImpl(Context context) {
        super(context);
    }

    @Override
    public boolean createUser(UserModel userModel) {

        ContentValues contentValues = createContentValues(userModel);

        long response = mDatabase.insert(UserContract.User.TABLE_NAME, UserContract.User.COLUMN_NAME_NULLABLE, contentValues);

        return isValidResponse(response);
    }

    @Override
    public UserModel getUser() {

        Cursor cursor = mDatabase.query(UserContract.User.TABLE_NAME, null, null, null, null, null, null);

        cursor.moveToFirst();

        UserModel user = getUserModelFromCursor(cursor);

        return user;
    }

    private ContentValues createContentValues(UserModel user) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.User.COLUMN_NAME, user.name);
        contentValues.put(UserContract.User.COLUMN_LAST_NAME, user.lastName);
        contentValues.put(UserContract.User.COLUMN_EMAIL, user.email);

        return contentValues;

    }

    private UserModel getUserModelFromCursor(Cursor cursor) {

        UserModel userModel = new UserModel();

        long id = cursor.getInt(cursor.getColumnIndex(UserContract.User._ID));
        String name = cursor.getString(cursor.getColumnIndex(UserContract.User.COLUMN_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(UserContract.User.COLUMN_LAST_NAME));
        String email = cursor.getString(cursor.getColumnIndex(UserContract.User.COLUMN_EMAIL));

        userModel.id = id;
        userModel.name = name;
        userModel.lastName = lastName;
        userModel.email = email;

        return userModel;

    }


}
