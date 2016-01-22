package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import dagger.Module;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.AdminiBotSQLiteOpenHelper;
import io.dojogeek.adminibot.sqlite.utils.UserDataUtilTest;
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

    @Before
    public void setup() {
        Context context = getTargetContext();
        context.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
        mUserDao = new UserDaoImpl(context);
        mUserDao.openConection();
    }

    @After
    public void finishTest() {
        mUserDao.closeConection();
    }

    @Test
    public void userDao_createUser_isTrue() {

        boolean isInserted = mUserDao.createUser(CreatorModels.createUserModel());

        assertTrue(isInserted);
    }

    @Test
    public void userDao_getUser_isTrue() {

        UserModel userModel = CreatorModels.createUserModel();

        mUserDao.createUser(userModel);

        List<UserModel> users = mUserDao.getUsers();

        assertNotNull(users);
        assertTrue(!users.isEmpty());
        assertEquals(UNIQUE_USER, users.size());
        compareResultUserModel(userModel, users);

    }

    private void compareResultUserModel(UserModel userModel, List<UserModel> users) {

        for (UserModel user : users) {
            assertEquals(userModel.name, user.name);
            assertEquals(userModel.lastName, user.lastName);
            assertEquals(userModel.email, user.email);
        }
    }

}
