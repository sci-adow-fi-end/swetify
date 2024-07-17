import businesslogic.utility.NavigationManager;
import dao.users.ArtistDAO;
import domainmodel.entities.collections.Album;
import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.users.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PodcastLoadHandlerTest extends BaseTest{

    NavigationManager nm = new NavigationManager();


    @BeforeEach
    public void setup() {
        super.setUp();
        ArtistDAO adao = new ArtistDAO();
        nm.pushHandler(NavigationManager.HandlerId.LOAD_PODCAST);
        Artist a = new Artist("paolino", "paperino", "paperinik", "ero un gran figo");
        nm.getCurrentState().setLoggedArtist(a);
        adao.save(a);
    }

    @Test
    public void testSuccessfulAlbumLoad() {
        ByteArrayInputStream input = new ByteArrayInputStream("1\nstio\n1\nstio\nstio\n12\n13\n2\n2\n3\n".getBytes());
        System.setIn(input);
        nm.run();
        Podcast loadedPodcast = new Podcast();

        for (Podcast pod: nm.getCurrentState().getLoggedArtist().getPodcasts()) {
            if (pod.getTitle().equals("stio")) {
                loadedPodcast = pod;
                break;
            }
        }

        assertEquals("stio", loadedPodcast.getTitle());

    }
}
