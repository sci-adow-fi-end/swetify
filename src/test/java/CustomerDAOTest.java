import dao.users.CustomerDAO;
import domainmodel.entities.users.Customer;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CustomerDAOTest extends BaseTest {

    CustomerDAO userDatabase;
    Customer[] users = new Customer[]{new Customer("Esidisi", "jojo"),
            new Customer("Kars", "stio2"),
            new Customer("Jotaro", "oraoraora")};

    @BeforeEach
    public void setUp(){
        super.setUp();
        userDatabase = new CustomerDAO();

        userDatabase.save(users[0]);
        userDatabase.save(users[1]);
        userDatabase.save(users[2]);
    }

    @Test
    void testGet(){
        Customer usr = userDatabase.getByUsername(users[0].getUsername());
        Customer usr2 = userDatabase.get(usr.getId()).orElseThrow();
        assertEquals(usr.getUsername(), usr2.getUsername());
        assertEquals(usr.getPassword(), usr2.getPassword());
    }

    @Test
    void testGetByUsername() {
        for (Customer user : users) {
            assertEquals(user.getUsername(), userDatabase.getByUsername(user.getUsername()).getUsername());
            assertEquals(user.getPassword(), userDatabase.getByUsername(user.getUsername()).getPassword());
        }

        Customer user = new Customer("RyanGosling", "stiò");
        boolean present;
        try{
            userDatabase.getByUsername(user.getUsername());
            present = true;
        }
        catch(NoResultException e){
            present = false;
        }

        assertFalse(present);
    }

    @Test
    void getAll(){
        List<Customer> foundCustomers = userDatabase.getAll();
        assertEquals(3, foundCustomers.size());

        for (int i = 0; i < foundCustomers.size(); i++) {
            assertEquals(foundCustomers.get(i).getUsername(), users[i].getUsername());
            assertEquals(foundCustomers.get(i).getPassword(), users[i].getPassword());
        }
    }
}
