import businesslogic.NavigationManager;
import domainmodel.entities.Artist;
import domainmodel.entities.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationLoginHandlersTest extends BaseTest{

    private final NavigationManager navigationManager = new NavigationManager();

    @BeforeEach
    public void setUp() {
        super.setUp();
        navigationManager.pushHandler(NavigationManager.HandlerId.REGISTRATION);
    }

    @Test
    void testRegistrationCustomer() {
        String testString = "0\n1\nStio\nNelChill\n3\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        boolean existOne;
        try {
            navigationManager.getDaoById(NavigationManager.DaoId.USER).getByName("Stio");
            existOne = true;
        } catch (NoResultException | NonUniqueResultException e) {
            existOne = false;
        }

        assertTrue(existOne);
    }

    @Test
    void testRegistrationArtist() {
        String testString = "0\n2\nStio\nNelChill\nKuruKuru\n3\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        boolean existOne;
        try {
            navigationManager.getDaoById(NavigationManager.DaoId.ARTIST).getByName("KuruKuru");
            existOne = true;
        } catch (NoResultException | NonUniqueResultException e) {
            existOne = false;
        }

        assertTrue(existOne);
    }

    @Test
    void testLoginCustomer() {
        String testString = "0\n1\nStio\nNelChill\n1\n1\nStio\nNelChill\n5\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        User usr = navigationManager.getCurrentState().getLoggedUser();
        assertEquals("Stio", usr.getUsername());
        assertEquals("NelChill", usr.getPassword());
    }

    @Test
    void testLoginArtist() {
        String testString = "0\n2\nStio\nNelChill\nKuruKuru\n1\n2\nStio\nNelChill\n4\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        Artist artist = navigationManager.getCurrentState().getLoggedArtist();
        assertEquals("Stio", artist.getUsername());
        assertEquals("NelChill", artist.getPassword());
        assertEquals("KuruKuru", artist.getStageName());
    }

}
