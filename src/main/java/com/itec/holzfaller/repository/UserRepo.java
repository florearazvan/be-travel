package com.itec.holzfaller.repository;

import com.itec.holzfaller.entities.User;

import javax.persistence.TypedQuery;
import java.util.List;

public class UserRepo extends Repository<User, Long> {

    public UserRepo() {
        super(User.class);
    }

    public User findByUsernameAndPassword(String username, String password) {
        return executeWithReturn(entityManager -> {
            TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username = :username and u.password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<User> result = query.getResultList();
            return result.size() == 0 ? null : result.get(0);
        });
    }

    public User findByUsername(String username) {
        return executeWithReturn(entityManager -> {
            TypedQuery<User> query = entityManager.createQuery("select u from User u where u.username = :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.getResultList();
            return result.size() == 0 ? null : result.get(0);
        });
    }
}
