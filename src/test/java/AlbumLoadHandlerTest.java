import businesslogic.utility.NavigationManager;
import dao.users.ArtistDAO;
import domainmodel.entities.collections.Album;
import domainmodel.entities.users.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlbumLoadHandlerTest extends BaseTest {

    NavigationManager nm = new NavigationManager();


    @BeforeEach
    public void setup() {
        super.setUp();
        ArtistDAO adao = new ArtistDAO();
        nm.pushHandler(NavigationManager.HandlerId.LOAD_ALBUM);
        Artist a = new Artist("paolino", "paperino", "paperinik", "ero un gran figo");
        nm.getCurrentState().setLoggedArtist(a);
        adao.save(a);
    }

    @Test
    public void testSuccessfulAlbumLoad() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\nstio\n1\nstio\nstio\n12\n13\n2\n2\n3\n".getBytes());
        System.setIn(input);
        nm.run();
        Album loadedAlbum = new Album();

        for (Album album : nm.getCurrentState().getLoggedArtist().getAlbums()) {
            if (album.getPlaylist().getTitle().equals("stio")) {
                loadedAlbum = album;
                break;
            }
        }

        assertEquals(1, loadedAlbum.getPlaylist().getTracks().size());
        assertEquals("stio", loadedAlbum.getPlaylist().getTracks().getFirst().getTitle());

    }


}
