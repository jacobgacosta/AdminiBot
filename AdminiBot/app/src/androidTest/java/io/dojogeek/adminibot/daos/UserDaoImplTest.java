package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.UsersContract;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserDaoImplTest {

    private static int UNIQUE_USER = 1;
    private UserDao mUserDao;
    private Context mContext;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mUserDao = new UserDaoImpl(mContext);
        mUserDao.openConection();
    }

    @After
    public void finishTest() {
        mUserDao.removeAllUsers();
        mUserDao.closeConection();
    }

    @Test
    public void userDao_creationUser_isTrue() {

        UserModel user = CreatorModels.createUserModel();

        long insertedRecordId = mUserDao.createUser(user);

        assertEquals(UNIQUE_USER, insertedRecordId);
    }

    @Test
    public void userDao_creationAndObtainingUser_isTrue() {

        UserModel expectedUser = CreatorModels.createUserModel();

        mUserDao.createUser(expectedUser);

        List<UserModel> actualUsers = mUserDao.getUsers();

        verifyGetUsersResult(actualUsers);
        compareResultUserModel(expectedUser, actualUsers);

    }

    @Test
    public void userDao_creationAndupdatingUser_isTrue() {

        UserModel userModel = CreatorModels.createUserModel();

        long insertedRecordId = mUserDao.createUser(userModel);

        String where = UsersContract.User._ID + "= " + insertedRecordId;

        UserModel newUserModel = changeUserModelValues(userModel);

        long updatedRows = mUserDao.updateUser(newUserModel, where);

        assertEquals(UNIQUE_USER, updatedRows);

    }

    private UserModel changeUserModelValues(UserModel userModel) {
        userModel.name = "Junit";
        userModel.email = "updatetest@gmail.com";
        userModel.lastName = "Jacoco";

        return userModel;
    }

    private void verifyGetUsersResult(List<UserModel> userModelList) {

        assertNotNull(userModelList);
        assertTrue(!userModelList.isEmpty());
        assertEquals(UNIQUE_USER, userModelList.size());

    }

    private void compareResultUserModel(UserModel userModel, List<UserModel> actualUsers) {

        for (UserModel user : actualUsers) {
            assertEquals(userModel.name, user.name);
            assertEquals(userModel.lastName, user.lastName);
            assertEquals(userModel.email, user.email);
        }
    }

}
