package businesslogic;

import dao.Dao;
import dao.UserDao;
import domainmodel.User;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class RegistrationHandler extends Handler{

    public String userName;
    public String password;
    public Dao<User> userDatabase = new UserDao();



    private void renderChoices() {
        clearScreen();
        System.out.println("\n");
        //System.out.println("insert your desired name and password or press 1 to go back to the login page");
        System.out.println("0: register to access Swetify");
        System.out.println("1: go back to the login page");
    }

    @Override
    public void update() {
        int conv_input;
        try{
            Scanner scanner = new Scanner(System.in);
            conv_input = scanner.nextInt();
        }
        catch(NumberFormatException e){
            return false;
        }
        switch(conv_input){
            case 0:
                boolean isTaken = true;
                while(isTaken){
                    isTaken = false;
                    System.out.println("Choose a username: ");
                    Scanner scanner = new Scanner(System.in);
                    userName = scanner.nextLine();
                    System.out.println("Choose a password: ");
                    password = scanner.nextLine();
                    if(userDatabase.get(userName).isPresent()){
                        System.out.println("Username is already taken");
                        isTaken = true;
                    }
                }
                userDatabase.save(new User(userName, password));
                navigationManager.setSavedUser(new User(userName, password));
                navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                break;
            case 1:
                navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                break;
            default:
                return false;
        }
        return true;
    }
}
