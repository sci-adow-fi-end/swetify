import dao.UserDao;
import domainmodel.User;

import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest extends BaseTest {

    UserDao userDatabase;
    User[] users = new User[]{new User("Esidisi", "jojo"),
            new User("Kars", "stio2"),
            new User("Jotaro", "oraoraora")};

    @BeforeEach
    public void setUp(){
        super.setUp();
        userDatabase = new UserDao();

        userDatabase.save(users[0]);
        userDatabase.save(users[1]);
        userDatabase.save(users[2]);
    }

    @Test
    void testGet(){
        User usr = userDatabase.getByName(users[0].getUsername());
        User usr2 = userDatabase.get(usr.getId()).orElseThrow();
        assertEquals(usr.getUsername(), usr2.getUsername());
        assertEquals(usr.getPassword(), usr2.getPassword());
    }

    @Test
    void testGetByName(){
        for (User user : users){
            assertEquals(user.getUsername(), userDatabase.getByName(user.getUsername()).getUsername());
            assertEquals(user.getPassword(), userDatabase.getByName(user.getUsername()).getPassword());
        }

        User user = new User("RyanGosling", "stiò");
        boolean present;
        try{
            userDatabase.getByName(user.getUsername());
            present = true;
        }
        catch(NoResultException e){
            present = false;
        }

        assertFalse(present);
    }

    @Test
    void getAll(){
        List<User> foundUsers = userDatabase.getAll();
        assertEquals(3, foundUsers.size());

        for(int i = 0; i < foundUsers.size(); i++){
            assertEquals(foundUsers.get(i).getUsername(), users[i].getUsername());
            assertEquals(foundUsers.get(i).getPassword(), users[i].getPassword());
        }
    }
}
