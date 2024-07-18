import businesslogic.utility.NavigationManager;
import dao.users.ArtistDAO;
import dao.users.CustomerDAO;
import domainmodel.entities.users.Artist;
import domainmodel.entities.users.Customer;
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
        String testString = "1\n1\nStio\nNelChill\n3\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        boolean existOne;
        try {
            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.getByUsername("Stio");
            existOne = true;
        } catch (NoResultException | NonUniqueResultException e) {
            existOne = false;
        }

        assertTrue(existOne);
    }

    @Test
    void testRegistrationArtist() {
        String testString = "1\n2\nStio\nNelChill\nKuruKuru\n3\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        boolean existOne;
        try {
            ArtistDAO artistDAO = new ArtistDAO();
            artistDAO.getByUserName("Stio");
            existOne = true;
        } catch (NoResultException | NonUniqueResultException e) {
            existOne = false;
        }

        assertTrue(existOne);
    }

    @Test
    void testLoginCustomer() {
        String testString = "1\n1\nStio\nNelChill\n1\n1\nStio\nNelChill\n5\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        Customer usr = navigationManager.getSession().getLoggedUser();
        assertEquals("Stio", usr.getUsername());
        assertEquals("NelChill", usr.getPassword());
    }

    @Test
    void testLoginArtist() {
        String testString = "1\n2\nStio\nNelChill\nKuruKuru\n1\n2\nStio\nNelChill\n4\n";
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);

        navigationManager.run();

        Artist artist = navigationManager.getSession().getLoggedArtist();
        assertEquals("Stio", artist.getUsername());
        assertEquals("NelChill", artist.getPassword());
        assertEquals("KuruKuru", artist.getStageName());
    }

}
