package io.dojogeek.adminibot.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.UsersContract;

public class UserDaoImpl extends SQLiteGlobalDao implements UserDao {

    @Inject
    public UserDaoImpl(Context context) {
        super(context);
    }

    @Override
    public long createUser(UserModel userModel) {

        ContentValues contentValues = createContentValues(userModel);

        long response = mDatabase.insert(UsersContract.User.TABLE_NAME, UsersContract.User.COLUMN_NAME_NULLABLE, contentValues);

        return response;
    }

    @Override
    public List<UserModel> getUsers() {

        Cursor cursor = mDatabase.query(UsersContract.User.TABLE_NAME, null, null, null, null, null, null);

        List<UserModel> userModels = new ArrayList<>();

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                UserModel user = getUserModelFromCursor(cursor);
                userModels.add(user);
                cursor.moveToNext();
            }
        }
        return userModels;
    }

    @Override
    public void removeAllUsers() {
        mDatabase.execSQL("DELETE FROM " + UsersContract.User.TABLE_NAME);
    }

    private ContentValues createContentValues(UserModel user) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(UsersContract.User.COLUMN_NAME, user.name);
        contentValues.put(UsersContract.User.COLUMN_LAST_NAME, user.lastName);
        contentValues.put(UsersContract.User.COLUMN_EMAIL, user.email);

        return contentValues;

    }

    private UserModel getUserModelFromCursor(Cursor cursor) {

        UserModel userModel = new UserModel();

        long id = cursor.getInt(cursor.getColumnIndex(UsersContract.User._ID));
        String name = cursor.getString(cursor.getColumnIndex(UsersContract.User.COLUMN_NAME));
        String lastName = cursor.getString(cursor.getColumnIndex(UsersContract.User.COLUMN_LAST_NAME));
        String email = cursor.getString(cursor.getColumnIndex(UsersContract.User.COLUMN_EMAIL));

        userModel.id = id;
        userModel.name = name;
        userModel.lastName = lastName;
        userModel.email = email;

        return userModel;

    }


}
