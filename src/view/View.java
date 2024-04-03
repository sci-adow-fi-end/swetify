package view;

import controller.Controller;
import controller.NavigationController;

import java.util.*;

public abstract class View {


    public View(NavigationController controller) {
        this.controller=controller;
    }

    protected Controller controller;

    protected void getUserInput() {
        Scanner input = new Scanner(System.in);
        controller.handleEvent(input.nextLine());
    }

    public abstract void update(); //è proprio proprio quella dell'observer ;-)

}