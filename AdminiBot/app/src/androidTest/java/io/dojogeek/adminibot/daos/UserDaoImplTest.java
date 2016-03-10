package io.dojogeek.adminibot.daos;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.CoreMatchers.*;

@RunWith(AndroidJUnit4.class)
public class UserDaoImplTest {

    private static final long SUCCESS_OPERATION = 1;
    private static final long NO_OPERATION = 0;
    private static final long OPERATIONAL_ERROR = -1;
    private static int UNIQUE_USER = 1;
    private UserDao mUserDao;
    private Context mContext;

    @Before
    public void setup() {

        mContext = getTargetContext();

        mUserDao = new UserDaoImpl(mContext);
    }

    @After
    public void finishTest() {
        ((UserDaoImpl) mUserDao).closeConnection();
        mContext.deleteDatabase(AdminiBotSQLiteOpenHelper.DATABASE_NAME);
    }

    @Test
    public void testCreateUser_successInsertion() {

        UserModel userModel = CreatorModels.createUserModel();

        long insertedRecordId = createUser(userModel);

        assertEquals(UNIQUE_USER, insertedRecordId);

    }

    @Test(expected = NullPointerException.class)
    public void testCreateUser_withNullModel_isException() {

        UserModel userModel = null;
        createUser(userModel);
    }

    @Test
    public void testCreateUser_withNullRequiredField_noInsertion() {

        UserModel userModel = CreatorModels.createUserModel();
        userModel.setName(null);

        long insertedRecordId = createUser(userModel);

        assertThat(insertedRecordId, is(OPERATIONAL_ERROR));
    }

    @Test
    public void testGetUsers_successObtainingSingleUser() {

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
    public void testGetUsers_withNoRecords_noUser() {

        List<UserModel> actualUserModelList = mUserDao.getUsers();

        assertThat(actualUserModelList.isEmpty(), is(true));
    }

    @Test
    public void testUpdateUser_successUpdating() {

        UserModel expectedUserModel = CreatorModels.createUserModel();

        long insertedRecordId = createUser(expectedUserModel);

        UserModel updatedUserModel = changeUserModelValues(expectedUserModel);

        String where = UsersContract.User._ID + "= " + insertedRecordId;

        mUserDao.updateUser(updatedUserModel, where);

        List<UserModel> userModelList = mUserDao.getUsers();

        compareUserModels(updatedUserModel, userModelList.get(0));

    }

    @Test(expected = NullPointerException.class)
    public void testUpdateUser_withNullModel_isException() {

        UserModel userModel = CreatorModels.createUserModel();

        long insertedRecordId = createUser(userModel);

        String where = UsersContract.User._ID + "= " + insertedRecordId;

        mUserDao.updateUser(null, where);

    }

    @Test
    public void testUpdateUser_withNullWhereSentence_noUpdating() {

        UserModel userModel = CreatorModels.createUserModel();

        createUser(userModel);

        String where = null;

        userModel.setName("Jacocoman");

        long updatedRows = mUserDao.updateUser(userModel, where);

        assertThat(updatedRows, is(SUCCESS_OPERATION));
    }

    @Test
    public void testUpdateUser_withNonExistentId_noUpdate() {

        int nonExistentId = 3;

        String where = UsersContract.User._ID + "= " + nonExistentId;

        UserModel userModel = CreatorModels.createUserModel();

        long updatedRows = mUserDao.updateUser(userModel, where);

        assertThat(updatedRows, is(NO_OPERATION));
    }

    @Test(expected = SQLiteConstraintException.class)
    public void testUpdateUser_withNullRequiredField_isException() {

        UserModel userModel = CreatorModels.createUserModel();

        long insertedRecordId = createUser(userModel);

        String where = UsersContract.User._ID + "= " + insertedRecordId;

        userModel.setName(null);

        long updatedRows = mUserDao.updateUser(userModel, where);

    }

    private long createUser(UserModel userModel) {

        long insertedRecordId = mUserDao.createUser(userModel);

        return insertedRecordId;
    }

    private void compareUserModels(UserModel expectedUserModel, UserModel actualUserModel) {

        assertEquals(expectedUserModel.getName(), actualUserModel.getName());
        assertEquals(expectedUserModel.getEmail(), actualUserModel.getEmail());
        assertEquals(expectedUserModel.getLastName(), actualUserModel.getLastName());

    }

    private UserModel changeUserModelValues(UserModel userModel) {
        userModel.setName("Jacoco");
        userModel.setLastName("Test");
        userModel.setEmail("update@dojogeek.io");

        return userModel;
    }
}
