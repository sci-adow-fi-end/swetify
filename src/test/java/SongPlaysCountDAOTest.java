import dao.BaseDao;
import dao.SongPlaysCountDAO;
import domainmodel.entities.User;
import domainmodel.entities.suggestions.SongPlaysCount;
import domainmodel.entities.track.Song;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SongPlaysCountDAOTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private SongPlaysCountDAO songPlaysCountDao;

    @BeforeAll
    static void setUpBeforeClass() {
        entityManagerFactory = BaseDao.getEntityManagerFactory();
    }

    @AfterAll
    static void tearDownAfterClass() {
        entityManagerFactory.close();
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
        User user = new User();
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
