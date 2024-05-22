package businesslogic;

import dao.Dao;
import dao.UserDao;
import domainmodel.User;

import java.util.Scanner;

public class RegistrationHandler extends Handler{

    public String userName;
    public String password;
    public Dao<User> userDatabase = new UserDao();



    private void renderChoices() {
        clearScreen();
        System.out.println("0: register to access Swetify");
        System.out.println("1: go back to the login page");
        System.out.println("\n");
    }

    private boolean validateUsername(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a username: ");
        userName = scanner.nextLine();
        System.out.println("Choose a password: ");
        password = scanner.nextLine();
        if (userDatabase.get(userName).isPresent()) {
            System.out.println("Username is already taken");
            return false;
        }
        else{
            return true;
        }
    }



    @Override
    public State update(State state) {
        renderChoices();
        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner scanner = new Scanner(System.in);
        while(!validNavigationOption) {
            try {
                navigationOption = scanner.nextInt();
                validNavigationOption = true;
            } catch (NumberFormatException e) {
                continue;
            }
            switch (navigationOption) {
                case 0:
                    boolean validUsername = false;
                    while (!validUsername) {
                        validUsername = validateUsername();
                    }
                    userDatabase.save(new User(userName, password));
                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                    break;
                case 1:
                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                    break;
                default:
                    validNavigationOption = false;
            }
        }
        return state;
    }
}
