package controller;

import java.util.HashMap;
import java.util.Map;

public class NavigationController implements Controller{
    private final Map<String, Controller> controllers;
    private Controller currentController;

    public NavigationController() {
        this.controllers = new HashMap<>();
    }

    void registerController(String name, Controller controller) {
        controllers.put(name, controller);
    }

    void switchToController(String name) {
        if (controllers.containsKey(name)) {
            currentController = controllers.get(name);
        } else {
            throw new IllegalArgumentException("No controller registered with name: " + name);
        }
    }

    @Override
    public void handleEvent(String data) {
        if (currentController != null) {
            currentController.handleEvent(data);
        } else {
            throw new IllegalStateException("No controller is currently active");
        }
    }
}
