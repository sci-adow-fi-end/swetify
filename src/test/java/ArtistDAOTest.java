import dao.users.ArtistDAO;
import domainmodel.entities.users.Artist;
import jakarta.persistence.NoResultException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ArtistDAOTest extends BaseTest {

    ArtistDAO artistDatabase;

    Artist[] artists = new Artist[]{new Artist(), new Artist()};

    @BeforeEach
    public void setUp(){
        super.setUp();
        artistDatabase = new ArtistDAO();

        artists[0].setUsername("artistUN1");
        artists[0].setStageName("artist1");
        artists[0].setFollowers(5000);
        artists[0].setBiography("fuck it we ball");
        artists[1].setUsername("artistUN2");
        artists[1].setStageName("artist2");
        artists[1].setFollowers(3000);
        artists[1].setBiography("God dammit OpenBoard!");

        for (Artist artist : artists){
            artistDatabase.save(artist);
        }
    }

    @Test
    void testGet(){
        Artist artist1 = artistDatabase.getByStageName(artists[0].getStageName()).getFirst();
        Artist artist2 = artistDatabase.get(artist1.getId()).orElseThrow();

        assertEquals(artist1.getStageName(), artist2.getStageName());
        assertEquals(artist1.getFollowers(), artist2.getFollowers());
        assertEquals(artist1.getBiography(), artist2.getBiography());
    }

    @Test
    void testGetByUserName() {
        for (Artist artist : artists){
            assertEquals(artist.getStageName(), artistDatabase.getByUserName(artist.getUsername()).getStageName());
            assertEquals(artist.getFollowers(), artistDatabase.getByUserName(artist.getUsername()).getFollowers());
            assertEquals(artist.getBiography(), artistDatabase.getByUserName(artist.getUsername()).getBiography());
        }

        Artist artist = new Artist();
        artist.setUsername("artistUN3");
        artist.setFollowers(5000);
        artist.setBiography("kuru kuru");

        boolean present;
        try {
            artistDatabase.getByUserName(artist.getUsername());
            present = true;
        } catch (NoResultException e) {
            present = false;
        }

        assertFalse(present);
    }

    @Test
    void testGetByStageName() {
        Artist artist1 = new Artist();
        artist1.setStageName("artist1");
        Artist artist2 = new Artist();
        artist2.setStageName("artist1");

        artistDatabase.save(artist1);
        artistDatabase.save(artist2);

        List<Artist> foundArtists = artistDatabase.getByStageName("artist1");
        assertEquals(3, foundArtists.size());
    }

    @Test
    void testGetAll(){
        List<Artist> foundArtists = artistDatabase.getAll();

        assertEquals(2, foundArtists.size());

        for(int i = 0; i < foundArtists.size(); i++){
            assertEquals(foundArtists.get(i).getStageName(), artists[i].getStageName());
            assertEquals(foundArtists.get(i).getFollowers(), artists[i].getFollowers());
            assertEquals(foundArtists.get(i).getBiography(), artists[i].getBiography());
        }
    }
}
