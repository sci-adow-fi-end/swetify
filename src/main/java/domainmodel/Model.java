package domainmodel;


import businesslogic.Handler;
import jakarta.persistence.*;

import java.util.*;

@MappedSuperclass
public abstract class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Transient
    private final List<Handler> observers = new LinkedList<>();

    public void attach(Handler observer) {
        observers.add(observer);
    }

    public void detach(Handler observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (Handler observer : observers) {
            observer.update();
        }
    }

    public void setId(Long id) {
        this.id = id;
        notifyObservers();
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Model model)) return false;
        return Objects.equals(id, model.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}