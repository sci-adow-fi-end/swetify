import businesslogic.utility.NavigationManager;
import domainmodel.entities.users.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeHandlerTest extends BaseTest{

    private final NavigationManager navigationManager = new NavigationManager();

    @BeforeEach
    public void setUp() {
        super.setUp();
        navigationManager.pushHandler(NavigationManager.HandlerId.HOME);
        navigationManager.getSession().setLoggedUser(new Customer());
    }

    @Test
    public void testHomeSearch() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\n-\n5\n".getBytes());
        System.setIn(input);
        navigationManager.run();

        assertEquals(NavigationManager.HandlerId.HOME.ordinal(), navigationManager.getLastId());
    }

    @Test
    public void testHomeViewAllPlaylists() {
        ByteArrayInputStream input = new ByteArrayInputStream("2\n4\n".getBytes());
        System.setIn(input);
        navigationManager.getSession().setLoggedUser(new Customer());
        navigationManager.run();

        assertEquals(NavigationManager.HandlerId.VIEW_ALL_PLAYLISTS.ordinal(), navigationManager.getLastId());
    }

    @Test
    public void testHomeSuggestions() {
        ByteArrayInputStream input = new ByteArrayInputStream("3\n4\n".getBytes());
        System.setIn(input);
        navigationManager.run();

        assertEquals(NavigationManager.HandlerId.VIEW_SUGGESTIONS.ordinal(), navigationManager.getLastId());
    }
}
