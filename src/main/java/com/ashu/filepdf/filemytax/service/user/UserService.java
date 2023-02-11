package com.ashu.filepdf.filemytax.service.user;

import com.ashu.filepdf.filemytax.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public User findUserByEmail(String email);

    public User findByUserId(String uuid);

    public List<User> findAllUsers();

}
