import businesslogic.ConfigOptions;
import dao.BaseDao;
import jakarta.persistence.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public abstract class BaseTest {
    protected static EntityManagerFactory entityManagerFactory = BaseDao.getEntityManagerFactory();
    protected EntityManager entityManager;

    @BeforeAll
    public static void setOptions(){
        ConfigOptions.TEST_MODE = true;
    }

    @BeforeEach
    public void setUp() {
        entityManager = entityManagerFactory.createEntityManager();


        try {

            entityManager.getTransaction().begin();
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
            Query query = entityManager.createNativeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA='PUBLIC'");
            List<String> tableNames = query.getResultList();

            for (String tableName : tableNames) {
                entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            }
            entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();

            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        } finally {
            entityManager.close();
        }
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }
        entityManager.close();
    }

    @AfterAll
    public static void tearDownAfterAll() {
        //entityManagerFactory.close();
    }
}
