package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.sqlite.utils.UserDataUtilTest;
import io.dojogeek.adminibot.utiltest.CreatorModels;

import static android.support.test.InstrumentationRegistry.getTargetContext;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UserDaoImplTest {

    private UserDao mUserDao;

    @Before
    public void setup() {
        mUserDao = new UserDaoImpl(getTargetContext());
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

}
