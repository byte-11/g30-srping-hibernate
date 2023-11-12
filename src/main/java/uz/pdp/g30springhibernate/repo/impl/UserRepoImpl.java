package uz.pdp.g30springhibernate.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import uz.pdp.g30springhibernate.entity.User;
import uz.pdp.g30springhibernate.repo.UserRepository;
import uz.pdp.g30springhibernate.utils.DBUtils;

import java.util.List;
import java.util.UUID;

public class UserRepoImpl implements UserRepository {

    private static UserRepository instance = null;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepoImpl();
        }
        return instance;
    }

    @Override
    public User save(User user) {
        return DBUtils.persist(User.class, user);
    }

    @Override
    public User getById(UUID uuid) {
        return DBUtils.findById(User.class, uuid);
    }

    @Override
    public User update(User user) {
        try (EntityManager entityManager = DBUtils.ENTITY_MANAGER_FACTORY.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.merge(user);
            entityManager.getTransaction().commit();
            return user;
        }
    }
    @Override
    public boolean deleteById(UUID uuid) {
        return false;
    }

    @Override
    public User getByUsername(String username) {
        try (EntityManager entityManager = DBUtils.ENTITY_MANAGER_FACTORY.createEntityManager()) {
            return entityManager
                    .createNamedQuery("find_by_username", User.class)
                    .setParameter("username", username).getSingleResult();
        }
    }

    @Override
    public List<User> getUserList() {
        try (EntityManager entityManager = DBUtils.ENTITY_MANAGER_FACTORY.createEntityManager()) {
            final Query selectUser = entityManager.createQuery("select u from User u");
            @SuppressWarnings("unchecked") final List<User> resultList = (List<User>) selectUser.getResultList();
            return resultList;
        }
    }

    @Override
    public User getByEmail(String email) {
        try (EntityManager entityManager = DBUtils.ENTITY_MANAGER_FACTORY.createEntityManager()) {
            return entityManager
                    .createNamedQuery("find_by_email", User.class)
                    .setParameter(1, email).getSingleResult();
        }
    }
}
