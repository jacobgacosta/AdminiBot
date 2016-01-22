package io.dojogeek.adminibot.daos;

import java.util.List;

import io.dojogeek.adminibot.models.UserModel;

public interface UserDao extends ConectionDao {

    boolean createUser(UserModel userModel);

    List<UserModel> getUsers();

}
