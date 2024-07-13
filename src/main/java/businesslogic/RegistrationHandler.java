package businesslogic;

import dao.ArtistDao;
import dao.UserDao;
import domainmodel.User;
import jakarta.persistence.NoResultException;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class RegistrationHandler extends Handler {

    public String userName;
    public String password;
    private final UserDao userDatabase;
    private final ArtistDao artistDatabase;

    public RegistrationHandler(UserDao userDatabase, ArtistDao artistDatabase) {
        this.userDatabase = userDatabase;
        this.artistDatabase = artistDatabase;
    }

    private void renderChoices() {
        clearScreen();
        System.out.println("0: register to access Swetify");
        System.out.println("1: go back to the login page");
        System.out.println("\n");
    }


    private boolean checkArtist() {
        Scanner input = new Scanner(System.in);

        System.out.println("1: Register as customer");
        System.out.println("2: Register as artist");
        System.out.println("\n");
        int answer = 0;
        boolean isArtist = false;
        boolean validAnswer = false;

        while (!validAnswer) {
            validAnswer = true;
            try {
                answer = input.nextInt();
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


    private boolean validateUsername() {

        Scanner scanner = new Scanner(System.in);
        boolean isArtist;

        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(scanner);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }
        isArtist = checkArtist();
        scanner = new Scanner(System.in);

        System.out.println("Choose a username: ");
        userName = scanner.nextLine();

        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(scanner);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }

        System.out.println("Choose a password: ");
        scanner = new Scanner(System.in);
        password = scanner.nextLine();

        if (isArtist) {
            try {
                artistDatabase.getByName(userName);
                System.out.println("Username is already taken");
                return false;
            } catch (NoResultException e) {
                return true;
            }
        } else {
            try {
                userDatabase.getByName(userName);
                System.out.println("Username is already taken");
                return false;
            } catch (NoResultException e) {
                return true;
            }
        }
    }


    @Override
    public State update(State state) {
        renderChoices();
        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner scanner = new Scanner(System.in);
        while (!validNavigationOption) {
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

                        if (ConfigOptions.TEST_MODE) {
                            String nextInput = getRestOfInput(scanner);
                            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
                        }

                        validUsername = validateUsername();
                    }
                    userDatabase.save(new User(userName, password));

                    if (ConfigOptions.TEST_MODE) {
                        String nextInput = getRestOfInput(scanner);
                        System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
                    }

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
