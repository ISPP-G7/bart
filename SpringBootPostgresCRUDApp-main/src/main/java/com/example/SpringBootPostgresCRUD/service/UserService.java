package com.example.SpringBootPostgresCRUD.service;

import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(user -> userList.add(user));

        return userList;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public boolean saveOrUpdateUser(User user) {
        User usr = userRepository.save(user);
        if (userRepository.findById(usr.getId()) != null) {
            return true;
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        if (userRepository.findById(id) != null) {
            return true;
        }
        return false;
    }

}
