package domainmodel;

import businesslogic.Handler;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

import java.util.LinkedList;
import java.util.List;

@MappedSuperclass
public class Observable {
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
}
