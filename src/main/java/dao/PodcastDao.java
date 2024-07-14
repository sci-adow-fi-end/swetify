package dao;

import domainmodel.Podcast;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class PodcastDao extends Dao<Podcast> {

    public PodcastDao(){
        setUp();
    }

    @Override
    public Optional<Podcast> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        return Optional.ofNullable(entityManager.find(Podcast.class, id));
    }

    @Override
    public Podcast getByName(String name){
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p WHERE p.title = :title", Podcast.class);
        query.setParameter("title", name);
        return query.getSingleResult();
    }

    public List<Podcast> getAllByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p WHERE p.title = :title", Podcast.class);
        query.setParameter("title", name);
        return query.getResultList();
    }

    @Override
    public List<Podcast> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p", Podcast.class);
        return query.getResultList();
    }
}
