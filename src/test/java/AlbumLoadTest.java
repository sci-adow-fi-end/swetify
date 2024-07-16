import businesslogic.utility.NavigationManager;
import domainmodel.entities.users.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlbumLoadTest extends BaseTest{

    NavigationManager nm = new NavigationManager();


    @BeforeEach
    public void setup(){
        super.setUp();
        nm.pushHandler(NavigationManager.HandlerId.LOAD_ALBUM);
        Artist a = new Artist("paolino","paperino","paperinik","ero un gran figo",0);
        nm.getCurrentState().setLoggedArtist(a);
    }

    @Test
    public void testSuccessfulAlbumLoad(){
        ByteArrayInputStream input = new ByteArrayInputStream("1\nstio\n1\nstio\nstio\n12\n13\n2\n2\n3\n".getBytes());
        System.setIn(input);
        nm.run();

        assertEquals(NavigationManager.HandlerId.LOAD_ALBUM.ordinal(), nm.getLastId());
    }


}
