package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.R;
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

        mUserDao.openConection();

        boolean isCreated = mUserDao.createUser(userModel);

        if (isCreated) {
            mRegisterUser.showNotification(R.string.register_ok);
        } else {
            mRegisterUser.showNotification(R.string.register_failed);
        }

    }



    @Override
    public void unnusedView() {
        mUserDao.closeConection();
    }


}
