package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;

public abstract class BaseDAO<T> {


    protected static final EntityManagerFactory entityManagerFactory;

    static {
        Properties properties = HibernateConfig.getHibernateProperties();
        entityManagerFactory = Persistence.createEntityManagerFactory("swetifyPersistenceUnit", properties);
    }

    public static EntityManagerFactory getEntityManagerFactory(){
        return entityManagerFactory;
    }

    public abstract Optional<T> get(long id);

    public abstract List<T> getAll();

    public void save(T t) {
        executeInsideTransaction(entityManager -> entityManager.persist(t));
    }

    public void update(T t) {
        executeInsideTransaction(entityManager -> entityManager.merge(t));
    }

    public void delete(T t) {
        executeInsideTransaction(entityManager -> entityManager.remove(t));
    }

    protected void executeInsideTransaction(Consumer<EntityManager> work) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            work.accept(entityManager);
            transaction.commit();
        }
        catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            entityManager.close();
        }
    }
}