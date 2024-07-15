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
        User result = entityManager.find(User.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    @Override
    public User getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e WHERE e.username = :name", User.class);
        query.setParameter("name", name);
        User result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    @Override
    public List<User> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<User> query = entityManager.createQuery("SELECT e FROM User e", User.class);
        List<User> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }
}
