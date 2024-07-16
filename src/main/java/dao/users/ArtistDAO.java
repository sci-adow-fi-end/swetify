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

    public List<Artist> getByStageName(String stageName) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a WHERE LOWER(a.stageName) LIKE LOWER(:name)", Artist.class);
        query.setParameter("name", "%" + stageName.toLowerCase() + "%");
        List<Artist> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    public Artist getByUserName(String username) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Artist> query = entityManager.createQuery("SELECT a FROM Artist a WHERE a.username = :username", Artist.class);
        query.setParameter("username", username);
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
