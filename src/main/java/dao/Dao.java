package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class Dao<T> {

    protected EntityManagerFactory entityManagerFactory;

    public abstract Optional<T> get(long id);

    public abstract Optional<T> get(String name);

    public abstract List<T> getAll();

    public abstract void save(T t);

    public abstract void update(T t);

    public abstract void delete(T t);

    void executeInsideTransaction(Consumer<EntityManager> work) {
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

    protected void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("swetifyPersistenceUnit");
    }



}