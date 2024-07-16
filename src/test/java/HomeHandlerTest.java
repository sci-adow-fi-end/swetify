
import businesslogic.utility.NavigationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import businesslogic.utility.State;

import java.io.ByteArrayInputStream;

public class HomeHandlerTest extends BaseTest{

    private final NavigationManager navigationManager = new NavigationManager();
    private final State state = new State();

    @BeforeEach
    public void setUp() {
        super.setUp();
        navigationManager.pushHandler(NavigationManager.HandlerId.HOME);
    }

    @Test
    public void testHomeSearch() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\n-\n5\n".getBytes());
        System.setIn(input);
        navigationManager.run();

        assertEquals(NavigationManager.HandlerId.HOME.ordinal(), navigationManager.getLastId());
    }

    @Test
    public void testHomeViewPlaylist() {
        ByteArrayInputStream input = new ByteArrayInputStream("2\n6\n".getBytes());
        System.setIn(input);
        navigationManager.run();

        assertEquals(NavigationManager.HandlerId.VIEW_PLAYLIST.ordinal(), navigationManager.getLastId());
    }

    //@Test
    public void testHomeSuggestions() {
        ByteArrayInputStream input = new ByteArrayInputStream("3".getBytes());
        System.setIn(input);
        //navigationManager.run();

        assertEquals(NavigationManager.HandlerId.VIEW_SUGGESTIONS.ordinal(), navigationManager.getCurrentHandlerId());
    }
}
