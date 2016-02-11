package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.UserModel;

public interface UserDao extends ConectionDao {

    long createUser(UserModel userModel);

    List<UserModel> getUsers();

    long updateUser(UserModel userModel, String where);

    void removeAllUsers();
}
