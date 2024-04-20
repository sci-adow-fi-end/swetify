package domainmodel;


import businesslogic.Handler;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.*;


public abstract class Model {

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