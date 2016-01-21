package io.dojogeek.adminibot.utiltest;

import io.dojogeek.adminibot.models.UserModel;

public class CreatorModels {

    public static UserModel createUserModel() {

        UserModel userModel = new UserModel();
        userModel.name = "Jacob";
        userModel.lastName = "Guzman";
        userModel.email = "jgacosta@dojogeek.io";

        return userModel;
    }

}
