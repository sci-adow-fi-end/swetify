package businesslogic;

import dao.Dao;
import dao.UserDao;
import domainmodel.User;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoginHandler extends Handler{

    public String userName;
    public String password;
    private final Dao<User> userDatabase = new UserDao();
    private User usr;

    private void renderChoices() {
        System.out.println("1: Log in Swetify");
        System.out.println("2: Don't have an account? Register now!");
        System.out.println("\n");
    }

    private boolean validateUsername() {
        Scanner input = new Scanner(System.in);

        System.out.println("Enter username: ");
        userName = input.nextLine();
        try {
            usr = userDatabase.getByName(userName).orElseThrow();
            return true;
        } catch (NoSuchElementException e) {
            printError("Username is not correct, try again");
            return false;
        }
    }

    private boolean validatePassword(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter password: ");
        password = input.nextLine();

        return usr.getPassword().equals(password);

    }

    @Override
    public State update(State state) {
        clearScreen();
        renderChoices();
        int navigationChoice;
        boolean validNavigationChoice = false;
        boolean validUsername = false;
        boolean validPassword = false;
        Scanner scanner = new Scanner(System.in);
        while(!validNavigationChoice) {
            validNavigationChoice=true;
            try {
                navigationChoice = scanner.nextInt();
            } catch (NumberFormatException e) {
                printError("Inserted value is not a number");
                validNavigationChoice=false;
                continue;
            }
            switch (navigationChoice) {
                case 1:
                    while (!validPassword) {
                        while (!validUsername) {
                            validUsername = validateUsername();
                        }
                        validPassword = validatePassword();
                        }
                    state.setLoggedUser(usr);
                    navigationManager.switchToController(NavigationManager.HandlerId.HOME);
                    break;
                case 2:
                    navigationManager.switchToController(NavigationManager.HandlerId.REGISTRATION);
                    break;
                default:
                    printError("Inserted choice out of range");
                    validNavigationChoice=false;
            }
        }
        return state;
    }
}

