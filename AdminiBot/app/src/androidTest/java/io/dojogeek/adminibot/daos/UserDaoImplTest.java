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
import static org.junit.Assert.assertNotEquals;
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
        mUserDao.closeConection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void userDao_creationUser_isTrue() {

        UserModel userModel = CreatorModels.createUserModel();

        long insertedRecordId = createUser(userModel);

        assertEquals(UNIQUE_USER, insertedRecordId);

    }

    @Test
    public void userDao_creationAndObtainingUser_isTrue() {

        UserModel expectedUserModel = CreatorModels.createUserModel();

        createUser(expectedUserModel);

        List<UserModel> userModelList = mUserDao.getUsers();

        assertNotNull(userModelList);
        assertTrue(!userModelList.isEmpty());
        assertEquals(UNIQUE_USER, userModelList.size());

        for (UserModel actualUserModel : userModelList) {
            compareUserModels(actualUserModel, expectedUserModel);
        }

    }

    @Test
    public void userDao_creationUpdatingAndObtainingUser_isTrue() {

        UserModel expectedUserModel = CreatorModels.createUserModel();

        long insertedRecordId = createUser(expectedUserModel);

        UserModel updatedUserModel = changeUserModelValues(expectedUserModel);

        String where = UsersContract.User._ID + "= " + insertedRecordId;

        mUserDao.updateUser(updatedUserModel, where);

        List<UserModel> userModelList = mUserDao.getUsers();

        compareUserModels(updatedUserModel, userModelList.get(0));

    }

    private long createUser(UserModel userModel) {

        long insertedRecordId = mUserDao.createUser(userModel);

        return insertedRecordId;
    }

    private void compareUserModels(UserModel expectedUserModel, UserModel actualUserModel) {

        assertEquals(expectedUserModel.name, actualUserModel.name);
        assertEquals(expectedUserModel.email, actualUserModel.email);
        assertEquals(expectedUserModel.lastName, actualUserModel.lastName);

    }

    private UserModel changeUserModelValues(UserModel userModel) {
        userModel.name = "Jacoco";
        userModel.lastName = "Test";
        userModel.email = "update@dojogeek.io";

        return userModel;
    }
}
