package businesslogic;

import dao.ArtistDao;
import dao.UserDao;
import domainmodel.Artist;
import domainmodel.User;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LoginHandler extends Handler {

    public String userName;
    public String password;
    private final UserDao userDatabase;
    private User usr;
    private final ArtistDao artistDatabase;
    private Artist art;

    public LoginHandler(UserDao userDatabase, ArtistDao artistDatabase) {
        this.userDatabase = userDatabase;
        this.artistDatabase = artistDatabase;
    }

    private void renderChoices() {
        System.out.println("1: Log in Swetify");
        System.out.println("2: Don't have an account? Register now!");
        System.out.println("3: Close Swetify");
        System.out.println("\n");

    }

    private boolean checkArtist() {
        Scanner input = new Scanner(System.in);

        System.out.println("1: Log in as customer");
        System.out.println("2: Log in as artist");
        System.out.println("\n");
        int answer = 0;
        boolean isArtist = false;
        boolean validAnswer = false;


        while (!validAnswer) {
            validAnswer = true;
            try {
                answer = Integer.parseInt(input.nextLine());
                if (ConfigOptions.TEST_MODE) {
                    String nextInput = getRestOfInput(input);
                    System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
                }
            } catch (NumberFormatException e) {
                printError("Inserted value is not a number");
                validAnswer = false;
                continue;
            }

            switch (answer) {

                case 1:
                    isArtist = false;
                    break;
                case 2:
                    isArtist = true;
                    break;
                default:
                    printError("Inserted choice out of range");
                    validAnswer = false;
            }
        }
        return isArtist;
    }

private boolean validateUsername(boolean isArtist) {
    Scanner input = new Scanner(System.in);

    System.out.println("Enter username: ");
    userName = input.nextLine();
    if (ConfigOptions.TEST_MODE) {
        String nextInput = getRestOfInput(input);
        System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
    }

    try {
        if (!isArtist) {
            usr = userDatabase.getByName(userName);
        } else {
            art = artistDatabase.getByName(userName);
        }
        return true;
    } catch (NoSuchElementException e) {
        printError("Username is not correct, try again");
        return false;
    }
}

private boolean validatePassword(boolean isArtist) {
    Scanner input = new Scanner(System.in);
    System.out.println("Enter password: ");
    password = input.nextLine();
    if (ConfigOptions.TEST_MODE) {
        String nextInput = getRestOfInput(input);
        System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
    }

    if (!isArtist) {
        return usr.getPassword().equals(password);
    } else {
        return art.getPassword().equals(password);
    }
}

@Override
public State update(State state) {
    clearScreen();
    renderChoices();
    int navigationChoice;
    boolean validNavigationChoice = false;
    boolean validUsername = false;
    boolean validPassword = false;
    boolean isArtist = false;
    Scanner scanner = new Scanner(System.in);

    while (!validNavigationChoice) {
        validNavigationChoice = true;
        try {
            navigationChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            printError("Inserted value is not a number");
            validNavigationChoice = false;
            continue;
        }
        switch (navigationChoice) {
            case 1:

                if (ConfigOptions.TEST_MODE) {
                    String nextInput = getRestOfInput(scanner);
                    System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
                }

                isArtist = checkArtist();

                while (!validPassword) {
                    while (!validUsername) {
                        validUsername = validateUsername(isArtist);
                    }
                    validPassword = validatePassword(isArtist);
                }
                if(!isArtist){
                    state.setLoggedUser(usr);
                }
                else{
                    state.setLoggedArtist(art);
                }

                if (!isArtist) {
                    navigationManager.switchToController(NavigationManager.HandlerId.HOME);
                } else {
                    navigationManager.switchToController(NavigationManager.HandlerId.ARTIST_HOME);
                }
                break;
            case 2:
                navigationManager.switchToController(NavigationManager.HandlerId.REGISTRATION);
                break;
            case 3:
                navigationManager.stop();
                break;
            default:
                printError("Inserted choice out of range");
                validNavigationChoice = false;
        }
    }
    return state;
}
}

