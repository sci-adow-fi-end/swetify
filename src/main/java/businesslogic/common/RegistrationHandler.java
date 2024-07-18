package businesslogic.common;

import businesslogic.utility.ConfigOptions;
import businesslogic.utility.Handler;
import businesslogic.utility.NavigationManager;
import businesslogic.utility.Session;
import dao.users.ArtistDAO;
import dao.users.CustomerDAO;
import domainmodel.entities.users.Artist;
import domainmodel.entities.users.Customer;
import jakarta.persistence.NoResultException;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

public class RegistrationHandler extends Handler {

    public String userName;
    public String password;
    public String stageName;
    private final CustomerDAO userDatabase;
    private final ArtistDAO artistDatabase;
    private boolean isArtist = false;

    public RegistrationHandler(CustomerDAO userDatabase, ArtistDAO artistDatabase) {
        this.userDatabase = userDatabase;
        this.artistDatabase = artistDatabase;
    }

    private void renderChoices() {
        clearScreen();
        System.out.println("1: register to access Swetify");
        System.out.println("2: go back to the login page");
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


    private boolean validateUsername() {

        isArtist = checkArtist();

        System.out.println("Choose a username: ");
        Scanner scanner = new Scanner(System.in);
        userName = scanner.nextLine();

        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(scanner);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }

        System.out.println("Choose a password: ");
        scanner = new Scanner(System.in);
        password = scanner.nextLine();

        if (ConfigOptions.TEST_MODE) {
            String nextInput = getRestOfInput(scanner);
            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
        }

        if (isArtist) {
            System.out.println("Choose a stage name: ");
            scanner = new Scanner(System.in);
            stageName = scanner.nextLine();

            if (ConfigOptions.TEST_MODE) {
                String nextInput = getRestOfInput(scanner);
                System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
            }
        }

        if (isArtist) {
            try {
                artistDatabase.getByUserName(userName);
                System.out.println("Username is already taken");
                return false;
            } catch (NoResultException e) {
                return true;
            }
        } else {
            try {
                userDatabase.getByUsername(userName);
                System.out.println("Username is already taken");
                return false;
            } catch (NoResultException e) {
                return true;
            }
        }
    }


    @Override
    public Session update(Session session) {
        renderChoices();
        int navigationOption = -1;
        boolean validNavigationOption = false;
        Scanner scanner = new Scanner(System.in);
        while (!validNavigationOption) {
            try {
                navigationOption = Integer.parseInt(scanner.nextLine());
                validNavigationOption = true;
            } catch (NumberFormatException e) {
                continue;
            }
            switch (navigationOption) {
                case 1:
                    boolean validUsername = false;
                    while (!validUsername) {

                        if (ConfigOptions.TEST_MODE) {
                            String nextInput = getRestOfInput(scanner);
                            System.setIn(new ByteArrayInputStream(nextInput.getBytes()));
                        }

                        validUsername = validateUsername();
                    }
                    if (!isArtist)
                        userDatabase.save(new Customer(userName, password));
                    else {
                        Artist art = new Artist();
                        art.setUsername(userName);
                        art.setPassword(password);
                        art.setStageName(stageName);
                        artistDatabase.save(art);
                    }

                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                    break;
                case 2:
                    navigationManager.switchToController(NavigationManager.HandlerId.LOGIN);
                    break;
                default:
                    validNavigationOption = false;
            }
        }
        return session;
    }
}
