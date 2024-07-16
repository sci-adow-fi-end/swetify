import businesslogic.utility.ConfigOptions;
import businesslogic.utility.NavigationManager;

public class Main {
    public static void main(String[] args) {
        ConfigOptions.TEST_MODE = false;
        NavigationManager nm = new NavigationManager();
    }
}
