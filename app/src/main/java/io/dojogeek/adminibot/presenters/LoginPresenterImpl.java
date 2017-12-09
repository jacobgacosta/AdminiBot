package io.dojogeek.adminibot.presenters;

import android.util.Log;

import io.dojogeek.adminibot.views.Login;

public class LoginPresenterImpl implements LoginPresenter {

    private static final String TAG = "LoginPresenterImpl";

    private Login mLogin;

    public LoginPresenterImpl(Login login) {
        mLogin = login;
    }

    @Override
    public void login(String email, String password) {
        Log.v(TAG, " " + email + " " + password);
    }
}
