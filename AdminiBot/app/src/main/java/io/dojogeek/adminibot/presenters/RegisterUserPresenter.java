package io.dojogeek.adminibot.presenters;

import io.dojogeek.adminibot.models.UserModel;

public interface RegisterUserPresenter {

    void addUser(UserModel userModel);

    void unnusedView();

}
