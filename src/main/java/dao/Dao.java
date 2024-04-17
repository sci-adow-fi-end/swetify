package dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(long id);

    Optional<T> get(String name);

    List<T> getAll();

    void save(T t);

    void update(T t);

    void delete(T t);
}