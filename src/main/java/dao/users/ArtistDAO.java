package dao.users;

import dao.BaseDAO;
import domainmodel.entities.users.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ArtistDAO extends BaseDAO<Artist> {

    @Override
    public Optional<Artist> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Artist result = entityManager.find(Artist.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    @Override
    public Artist getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a WHERE a.stageName = :name", Artist.class);
        query.setParameter("name", name);
        Artist result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    public List<Artist> getAllByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a WHERE a.stageName = :name", Artist.class);
        query.setParameter("name", name);
        List<Artist> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public Artist getByUserName(String userName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a WHERE a.username = :username", Artist.class);
        query.setParameter("username", userName);
        Artist result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    @Override
    public List<Artist> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a", Artist.class);
        List<Artist> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }
}
