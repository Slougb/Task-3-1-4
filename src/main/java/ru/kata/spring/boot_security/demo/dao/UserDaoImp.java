package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @PersistenceContext
   private EntityManager entityManager;

   @Override
   public void save(User user) {
      entityManager.persist(user);
   }

   @Override
   public User getUser(long id) {
      User user = entityManager.find(User.class, id);
      return user;
   }

   @Override
   public void deleteUser(long id) {
      User user = entityManager.find(User.class, id);
      entityManager.remove(user);
   }

   @Override
   public User updateUser(User user) {
      entityManager.merge(user);
      return user;
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      List<User> users = entityManager.createQuery("from User").getResultList();
      return users;
   }



}
