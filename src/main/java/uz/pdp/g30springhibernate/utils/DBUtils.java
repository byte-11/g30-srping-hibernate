package uz.pdp.g30springhibernate.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DBUtils {
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate-orm");

    public static <T> T persist(Class<T> tClass, Object o) {
        try (EntityManager entityManager = ENTITY_MANAGER_FACTORY   .createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
            return tClass.cast(o);
        }
    }

    public static <T> T findById(Class<T> tClass, Object id) {
        try (EntityManager entityManager = ENTITY_MANAGER_FACTORY   .createEntityManager()) {
            return entityManager.find(tClass, id);
        }
    }


}
