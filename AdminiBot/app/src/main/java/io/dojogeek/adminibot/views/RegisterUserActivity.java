package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.models.UserModel;
import io.dojogeek.adminibot.presenters.RegisterUserPresenter;
import io.dojogeek.adminibot.utils.AlertDialogs;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.UserValidator;

public class RegisterUserActivity extends BaseActivity2 implements RegisterUser, View.OnClickListener {

    @Inject
    public RegisterUserPresenter registerUserPresenter;

    private EditText mName;
    private EditText mLastName;
    private EditText mEmail;
    private UserModel mUser;

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.user_register_link:
                LaunchIntents.launchIntentClearTop(this, RegisterUserActivity.class);
                break;
            case R.id.about:
                AlertDialogs.showChangeLangDialog(this);
                break;
            case R.id.ok:
                processInputData();
                break;
            default:
                break;
        }
    }

    @Override
    public void showNotification(int message) {

        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, message, duration);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        registerUserPresenter.unnusedView();
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        appComponent.plus(new AdminiBotModule(this)).inject(this);

    }

    @Override
    protected void loadViews() {

        mName = (EditText) findViewById(R.id.name);
        mLastName = (EditText) findViewById(R.id.last_name);
        mEmail = (EditText) findViewById(R.id.email);

    }

    @Override
    protected void addListenersToViews() {
        findViewById(R.id.about).setOnClickListener(this);
        findViewById(R.id.ok).setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

    }

    @Override
    protected int getLayoutActivity() {
        return R.layout.register;
    }

    @Override
    protected void closeConnections() {

    }

    private void processInputData() {

        UserValidator userValidator = getUserValidator();

        boolean isValidUser = userValidator.validate();

        if (isValidUser) {
            registerUserPresenter.addUser(mUser);
            LaunchIntents.launchIntentClearTop(this, LoginActivity.class);
        } else {
            showErrors(userValidator);
        }
    }

    private UserValidator getUserValidator() {

        mUser = fillUser();

        UserValidator userValidator = new UserValidator();
        userValidator.setName(mUser.getName());
        userValidator.setLastName(mUser.getLastName());
        userValidator.setEmail(mUser.getEmail());

        return userValidator;
    }

    private UserModel fillUser() {

        UserModel userModel = new UserModel();
        userModel.setName(mName.getText().toString());
        userModel.setLastName(mLastName.getText().toString());
        userModel.setEmail(mEmail.getText().toString());

        return userModel;

    }

    private void showErrors(UserValidator userValidator) {

        if (!userValidator.isValidName()) {
            showError(mName, userValidator.getErrorMessageName());
        } else if (!userValidator.isValidLastName()) {
            showError(mLastName, userValidator.getErrorMessageLastName());
        } else if (!userValidator.isValidEmail()) {
            showError(mEmail, userValidator.getErrorMessageEmail());
        }

    }

    private void showError(EditText editText, int messageResourceError) {
        editText.setError(getString(messageResourceError));
        editText.requestFocus();
    }

}
