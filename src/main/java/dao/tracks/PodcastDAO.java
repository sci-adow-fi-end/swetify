package dao.tracks;

import dao.BaseDAO;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.users.Artist;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class PodcastDAO extends BaseDAO<Podcast> {

    @Override
    public Optional<Podcast> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Podcast result = entityManager.find(Podcast.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    public List<Podcast> getByTitle(String title) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p WHERE LOWER(p.title) LIKE LOWER(:title)", Podcast.class);
        query.setParameter("title", "%" + title.toLowerCase() + "%");
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

    public List<Podcast> getByArtist(Artist artist) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Podcast> query = entityManager.createQuery("SELECT p FROM Podcast p JOIN p.authors a WHERE a = :artist", Podcast.class);
        query.setParameter("artist", artist);
        List<Podcast> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }

}
