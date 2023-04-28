package com.example.SpringBootPostgresCRUD.service;

import com.example.SpringBootPostgresCRUD.entity.User;
import com.example.SpringBootPostgresCRUD.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("No se encontró User con id " + id);
        }
    }

    public User getUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new NoSuchElementException("No se encontró User con email " + email);
        }
    }

    public boolean saveOrUpdateUser(User user) {
        User usr = userRepository.save(user);
        if (userRepository.findById(usr.getId()).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        if (userRepository.findById(id).isPresent()) {
            return true;
        }
        return false;
    }

}
