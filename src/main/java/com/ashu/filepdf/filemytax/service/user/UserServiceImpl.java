package com.ashu.filepdf.filemytax.service.user;

import com.ashu.filepdf.filemytax.entity.User;
import com.ashu.filepdf.filemytax.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailUser(email);
    }

    @Override
    public User findByUserId(String uuid) {
        System.out.println(uuid);
        User user = userRepository.findUser(uuid);
        System.out.println(user + uuid);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
