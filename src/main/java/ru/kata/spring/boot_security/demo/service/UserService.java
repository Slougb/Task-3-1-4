package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> listUsers();
    User save(User user);
    User getUser(long id);
    void deleteUser(long id);
    User updateUser(User user);
    Optional<User> findByEmail(String email);
}
