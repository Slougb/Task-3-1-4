package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserRepository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {


   private final PasswordEncoder passwordEncoder;


   private final UserDao userDao;



   @Autowired
   public UserServiceImp(PasswordEncoder passwordEncoder, UserDao userDao, UserRepository userRepository) {
      this.passwordEncoder = passwordEncoder;
      this.userDao = userDao;
   }

   @Transactional
   @Override
   public void save(User user) {
      String encodedPassword = passwordEncoder.encode(user.getPassword());
      user.setPassword(encodedPassword);
      userDao.save(user);
   }

   @Transactional(readOnly = true)
   @Override
   public List<User> listUsers() {
      return userDao.listUsers();
   }

   @Transactional
   @Override
   public User getUser(long id) {
      return userDao.getUser(id);
   }

   @Transactional
   @Override
   public void deleteUser(long id) {
      userDao.deleteUser(id);
   }

   @Transactional
   @Override
   public User updateUser(User editUser) {
      User oldUser = userDao.getUser(editUser.getId());
      editUser.setPassword(oldUser.getPassword());
      return userDao.updateUser(editUser);
   }

}
