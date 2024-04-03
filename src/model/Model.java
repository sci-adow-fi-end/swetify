package model;

import view.View;

import java.util.*;


public abstract class Model {


    private final LinkedList<View> observers= new LinkedList<>();


    public void attach(View observer) {
        observers.add(observer);
    }


    public void detach(View observer) {
        observers.remove(observer);
    }


    public void notifyObservers() {
        for  (View view: observers){
            view.update();
        }
    }

}