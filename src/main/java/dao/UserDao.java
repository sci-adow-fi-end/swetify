package dao;

import domainmodel.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class UserDao extends BaseDao<User> {

    public UserDao() {
        setUp();
    }

    @Override
    public Optional<User> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public User getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //Query query = entityManager.createQuery("SELECT e FROM User e WHERE e.username = :name");
        TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e WHERE e.username = :name", User.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        //Query query = entityManager.createQuery("SELECT e FROM User e");
        TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e", User.class);
        return query.getResultList();
    }
}
