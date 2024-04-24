package businesslogic;

public class HomeHandler extends Handler {


    @Override
    protected void pullData() {
    }

    @Override
    protected void render() {
        clearScreen();
        System.out.println("Welcome to swetify, the engineer's music streaming service!");
        System.out.println("\n");
        System.out.println("1: Search for a track, artist or playlist");
        System.out.println("2: View your playlists");
        System.out.println("3: View suggested songs");
        System.out.println("4: Log out");
    }
    @Override
    protected void handleInput() {
    }
}
