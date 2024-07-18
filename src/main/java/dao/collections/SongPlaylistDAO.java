package dao.collections;

import dao.BaseDAO;
import domainmodel.entities.collections.SongPlaylist;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Artist;
import domainmodel.entities.users.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class SongPlaylistDAO extends BaseDAO<SongPlaylist> {

    @Override
    public Optional<SongPlaylist> get(long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        SongPlaylist result = em.find(SongPlaylist.class, id);
        em.close();
        return Optional.ofNullable(result);
    }

    @Override
    public List<SongPlaylist> getAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<SongPlaylist> resultList = em.createQuery("SELECT s FROM SongPlaylist s", SongPlaylist.class)
                .getResultList();
        em.close();
        return resultList;
    }

    public List<SongPlaylist> getByTitle(String name) {
        EntityManager em = entityManagerFactory.createEntityManager();
        List<SongPlaylist> resultList = em.createQuery("SELECT s FROM SongPlaylist s WHERE LOWER(s.title) LIKE LOWER(:name)", SongPlaylist.class)
                .setParameter("name", "%" + name.toLowerCase() + "%")
                .getResultList();
        em.close();
        return resultList;
    }

    public List<SongPlaylist> getByCustomer(Customer cus) {
        EntityManager em = entityManagerFactory.createEntityManager();
        TypedQuery<SongPlaylist> query = em.createQuery("SELECT sp FROM SongPlaylist sp JOIN p.author a WHERE a = :artist", SongPlaylist.class);
        query.setParameter("artist", cus);
        List<SongPlaylist> result = query.getResultList();
        em.close();
        return result;
    }


}
