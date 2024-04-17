package businesslogic;

public class HomeHandler extends Handler {


    @Override
    protected void pullData() {
    }

    @Override
    protected void render() {
        clearScreen();
        System.out.println("welcome to swetify, the engeneer's music streaming service!");
        System.out.println("\n");
        System.out.println("press 1 to search for a track, artist or playlist");
        System.out.println("press 2 to view your playlists");
        System.out.println("press 3 to view suggested songs");
        System.out.println("press 4 to log out");
    }
    @Override
    protected void handleInput() {
    }
}
