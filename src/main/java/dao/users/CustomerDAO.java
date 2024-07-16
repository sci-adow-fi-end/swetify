package dao.users;

import dao.BaseDAO;
import domainmodel.entities.users.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CustomerDAO extends BaseDAO<Customer> {

    @Override
    public Optional<Customer> get(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Customer result = entityManager.find(Customer.class, id);
        entityManager.close();
        return Optional.ofNullable(result);
    }

    @Override
    public Customer getByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery("SELECT e FROM Customer e WHERE e.username = :name", Customer.class);
        query.setParameter("name", name);
        Customer result = query.getSingleResult();
        entityManager.close();
        return result;
    }

    @Override
    public List<Customer> getAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        TypedQuery<Customer> query = entityManager.createQuery("SELECT e FROM Customer e", Customer.class);
        List<Customer> resultList = query.getResultList();
        entityManager.close();
        return resultList;
    }
}
