package dao;

import domainmodel.entities.track.Podcast;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class PodcastDao extends BaseDao<Podcast> {

    @Override
    public Optional<Podcast> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Podcast result = entityManager.find(Podcast.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    @Override
    public Podcast getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p WHERE LOWER(p.title) LIKE LOWER(:title)", Podcast.class);
        query.setParameter("title", "%" + name.toLowerCase() + "%");
        Podcast result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    public List<Podcast> getAllByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p WHERE LOWER(p.title) LIKE LOWER(:title)", Podcast.class);
        query.setParameter("title", "%" + name.toLowerCase() + "%");
        List<Podcast> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

    @Override
    public List<Podcast> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p", Podcast.class);
        List<Podcast> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }
}
