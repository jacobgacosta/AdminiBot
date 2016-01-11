package io.dojogeek.adminibot.daos;

import io.dojogeek.adminibot.models.UserModel;

public interface UserDao extends ConectionDao {

    boolean createUser(UserModel userModel);

    UserModel getUser();

}
