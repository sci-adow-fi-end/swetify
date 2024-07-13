import dao.UserDao;
import domainmodel.User;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest extends BaseDaoTest {

    UserDao userDatabase;
    User user1 = new User("Esidisi", "jojo");
    User user2 = new User("Kars", "stio2");
    User user3 = new User("Jotaro", "oraoraora");
    User user4 = new User("Ambatukam", "buss");

    public void setUp(){
        super.setUp();
        userDatabase = new UserDao();


    }
}
