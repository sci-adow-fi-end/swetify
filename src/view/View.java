package view;

import controller.Controller;
import controller.NavigationController;

import java.util.*;

public abstract class View {


    public View(NavigationController controller) {
        this.controller=controller;
    }

    protected Controller controller;

    abstract protected Event generateEvent(String input);

    protected void getUserInput() {
        Scanner input = new Scanner(System.in);
        controller.handleEvent(generateEvent(input.nextLine()));
    }

    public abstract void update(); //è proprio proprio quella dell'observer ;-)

}