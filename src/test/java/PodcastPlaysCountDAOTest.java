import dao.BaseDAO;
import dao.suggestions.PodcastPlaysCountDAO;
import domainmodel.entities.suggestions.PodcastPlaysCount;
import domainmodel.entities.tracks.Podcast;
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

class PodcastPlaysCountDAOTest {

    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private PodcastPlaysCountDAO podcastPlaysCountDao;

    @BeforeAll
    static void setUpBeforeClass() {
        entityManagerFactory = BaseDAO.getEntityManagerFactory();
    }


    @BeforeEach
    void setUp() {
        entityManager = entityManagerFactory.createEntityManager();
        podcastPlaysCountDao = new PodcastPlaysCountDAO();
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
    }

    @Test
    void testIncrementPlays() {
        Customer user = new Customer();
        user.setUsername("testUser");
        Podcast podcast = new Podcast("testPodcast", "testTheme", 60, 30, null); // Assuming appropriate constructor and other fields
        PodcastPlaysCount ppc = new PodcastPlaysCount(user, podcast, 5);

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.persist(podcast);
        entityManager.persist(ppc);
        entityManager.getTransaction().commit();

        long ppcId = ppc.getId();
        podcastPlaysCountDao.incrementPlays(ppcId);

        Optional<PodcastPlaysCount> updatedPpc = podcastPlaysCountDao.get(ppcId);
        assertTrue(updatedPpc.isPresent());
        assertEquals(6, updatedPpc.get().getPlays());
    }
}
