package dao;

import domainmodel.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.List;
import java.util.Optional;

public class UserDao extends Dao<User> {
    @Override
    public Optional<User> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> get(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM User e WHERE e.username = :name");
        query.setParameter("name", name);
        return Optional.ofNullable((User) query.getSingleResult());
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Query query = entityManager.createQuery("SELECT e FROM User e");
        return query.getResultList();
    }
}