package dao;

import domainmodel.User;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<User> {

    public UserDao() {
    }

    @Override
    public Optional<User> get(long id) {
        // code to select the user from the database
    }

    @Override
    public Optional<User> get(String name) {
        // code to select the user from the database
    }

    @Override
    public List<User> getAll() {
        // code to select all users from the database
    }

    @Override
    public void save(User user) {
        // code to save the user into the database
    }

    @Override
    public void update(User user) {
        // code to update the user in the database
    }

    @Override
    public void delete(User user) {
        // code to delete the user from the database
    }
}
