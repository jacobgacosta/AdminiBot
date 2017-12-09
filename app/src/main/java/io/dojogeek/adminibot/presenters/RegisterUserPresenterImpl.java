package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.daos.UserDao;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.views.RegisterUser;

public class RegisterUserPresenterImpl implements RegisterUserPresenter {

    public static String TAG = "RegisterUserPresenterImpl";

    private UserDao mUserDao;
    private RegisterUser mRegisterUser;

    public RegisterUserPresenterImpl(RegisterUser registerUser, UserDao userDao) {
        mRegisterUser = registerUser;
        mUserDao = userDao;
    }

    @Override
    public void addUser(UserModel userModel) {

        long isCreated = mUserDao.createUser(userModel);

    }



    @Override
    public void unnusedView() {
    }


}
