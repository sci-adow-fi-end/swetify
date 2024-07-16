package businesslogic.customer;

import businesslogic.utility.Handler;
import businesslogic.utility.State;
import dao.collections.AlbumDAO;
import domainmodel.entities.collections.Album;
import domainmodel.entities.tracks.Song;

import java.util.List;

public class AlbumsHandler extends Handler {

    private final AlbumDAO albumDAO;
    private List<Album> albums;

    public AlbumsHandler(AlbumDAO albumDAO) {
        this.albumDAO = albumDAO;
    }

    private void renderChoices() {
        System.out.println("Type the number of an album to view it or type 0 to go back");
    }

    @Override
    public State update(State state) {

        clearScreen();
        albums = state.getLoggedArtist().getAlbums();
        int index = 1;

        System.out.println("Albums:");
        for (Album a : albums) {
            System.out.println(index + ") " + a.getPlaylist().getTitle());
            index++;
        }

        renderChoices();
        int choice = askNumberInRange(0, index - 1);

        if (choice == 0) {
            navigationManager.previousState();
        } else {
            List<Song> albumSongs = albums.get(choice - 1).getPlaylist().getTracks();

            for (int i = 0; i < albumSongs.size(); i++) {
                System.out.println(i + ") " + albumSongs.get(i));
            }
        }
        return state;
    }
}
