package io.dojogeek.adminibot.views;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import javax.inject.Inject;

import dagger.AdminiBotModule;
import dagger.AppComponent;
import io.dojogeek.adminibot.R;
import io.dojogeek.adminibot.presenters.LoginPresenter;
import io.dojogeek.adminibot.utils.LaunchIntents;
import io.dojogeek.adminibot.validators.LoginValidator;

public class LoginActivity extends BaseActivity implements Login, View.OnClickListener {

    @Inject
    public LoginPresenter loginPresenter;

    private EditText mEmail;
    private EditText mPassword;
    private TextView mLink;
    private Button mButton;

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.user_register_link:
                LaunchIntents.launchIntentClearTop(this, RegisterUserActivity.class);
                break;
            case R.id.enter:
                //processInputData();
                LaunchIntents.launchIntentClearTop(this, MainActivity.class);
                break;
            default:
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setupComponent(AppComponent appComponent) {
        appComponent.plus(new AdminiBotModule(this)).inject(this);
    }

    @Override
    protected void loadViews() {

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mLink = (TextView) findViewById(R.id.user_register_link);
        mButton = (Button) findViewById(R.id.enter);

    }

    @Override
    protected void addListenersToViews() {
        mLink.setOnClickListener(this);
        mButton.setOnClickListener(this);
    }

    @Override
    protected void loadDataView() {

    }

    @Override
    protected int getLayoutActivity() {

        return R.layout.login;

    }

    @Override
    protected void closeConnections() {

    }

    private void processInputData() {

        loadViews();

        LoginValidator loginValidator = getLoginValidator();

        boolean isValidLoginData = loginValidator.validate();

        if (isValidLoginData) {

            loginPresenter.login(mEmail.getText().toString(), mPassword.getText().toString());

        } else {
            showErrors(loginValidator);
        }
    }

    private LoginValidator getLoginValidator() {

        LoginValidator loginValidator = new LoginValidator();
        loginValidator.setEmail(mEmail.getText().toString());
        loginValidator.setPassword(mPassword.getText().toString());

        return loginValidator;
    }

    private void showErrors(LoginValidator loginValidator) {

        if (!loginValidator.isValidEmail()) {
            showError(mEmail, loginValidator.getErrorMessageEmail());
        } else if (!loginValidator.isValidPassword()) {
            showError(mPassword, loginValidator.getErrorMessagePassword());
        }

    }

    private void showError(EditText editText, int messageResourceError) {
        editText.setError(getString(messageResourceError));
        editText.requestFocus();
    }
}
