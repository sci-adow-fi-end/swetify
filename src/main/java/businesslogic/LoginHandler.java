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

    @Override
    protected void pullData(){}

    @Override
    protected void renderChoices() {
        clearScreen();
        System.out.println("\n");
        System.out.println("1: Log in Swetify");
        System.out.println("2: Don't have an account? Register now!");
    }

    @Override
    protected boolean handleInput() {
        int convInput;
        try{
            Scanner scanner = new Scanner(System.in);
            convInput = scanner.nextInt();
        }
        catch(NumberFormatException e){
            return false;
        }
        switch(convInput){
            case 1:
                System.out.println("Enter username: ");
                Scanner input = new Scanner(System.in);
                userName = input.nextLine();
                System.out.println("Enter password: ");
                password = input.nextLine();
                User usr = null;
                try{
                    usr = userDatabase.get(userName).orElseThrow();
                }
                catch(NoSuchElementException e){
                    System.out.println(ANSI_RED+"Username is not correct, try again"+ANSI_RESET);
                    //TODO: decide whether to create a while loop or pass control again to LoginHandler
                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                }
                if(usr != null && usr.getPassword().equals(password)){
                    navigationManager.setSavedUser(usr);
                    navigationManager.switchToController(NavigationManager.HandlerId.HOME);
                }
                else{
                    System.out.println("Password is not correct, try again");
                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                }
                break;
            case 2:
                navigationManager.switchToController(NavigationManager.HandlerId.REGISTRATION);
                break;
            default:
                return false;
        }
        return true;
    }
}
