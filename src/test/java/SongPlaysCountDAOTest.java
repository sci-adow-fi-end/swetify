import dao.BaseDAO;
import dao.suggestions.SongPlaysCountDAO;
import domainmodel.entities.suggestions.SongPlaysCount;
import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SongPlaysCountDAOTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private SongPlaysCountDAO songPlaysCountDao;

    @BeforeAll
    static void setUpBeforeClass() {
        entityManagerFactory = BaseDAO.getEntityManagerFactory();
    }

    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        songPlaysCountDao = new SongPlaysCountDAO();
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
    }

    @Test
    void testIncrementPlays() {
        Customer user = new Customer();
        user.setUsername("testUser");
        Song song = new Song("testSong", "testLyrics", 3, 30, null); // Assuming appropriate constructor and other fields
        SongPlaysCount spc = new SongPlaysCount(user, song, 10);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.persist(song);
        entityManager.persist(spc);
        entityManager.getTransaction().commit();

        long spcId = spc.getId();
        songPlaysCountDao.incrementPlays(spcId);

        Optional<SongPlaysCount> updatedSpc = songPlaysCountDao.get(spcId);
        assertTrue(updatedSpc.isPresent());
        assertEquals(11, updatedSpc.get().getPlays());
    }
}
