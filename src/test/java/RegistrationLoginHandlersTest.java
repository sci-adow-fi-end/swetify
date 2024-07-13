import businesslogic.NavigationManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import businesslogic.State;
import domainmodel.User;

import java.io.ByteArrayInputStream;

//TODO: execute and verify tests when the database is ready

//TODO: create two separate tests for login and registration (use @Order)

public class RegistrationLoginHandlersTest {

    private final NavigationManager navigationManager = new NavigationManager();
    private final State state = new State();
    String testString = "0\n1\nStio\nNelChill\n1\n1\nStio\nNelChill\n5\n"; //Registration -> Login -> Home

    @BeforeEach
    void setUp() {
        navigationManager.pushHandler(NavigationManager.HandlerId.REGISTRATION);
        navigationManager.setCurrentState(state);
    }

    @Test
    void testRegistrationLogin(){
        ByteArrayInputStream input = new ByteArrayInputStream(testString.getBytes());
        System.setIn(input);
        navigationManager.run();

        User usr = state.getLoggedUser();
        assertEquals("Stio", usr.getUsername());
        assertEquals("NelChill", usr.getPassword());
    }

}
