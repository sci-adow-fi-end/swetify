import dao.SongDao;
import domainmodel.Song;
import jakarta.persistence.NoResultException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class SongDaoTest extends BaseDaoTest {

    SongDao songDatabase;
    Song song1 = new Song();
    Song song2 = new Song();
    Song song3 = new Song();
    Song song4 = new Song();

    @BeforeEach
    public void setUp(){
        super.setUp();
        songDatabase = new SongDao();

        System.out.println("Stio");
        song1.setTitle("title1");
        song1.setDuration(Duration.ofSeconds(10));
        song2.setTitle("title2");
        song2.setDuration(Duration.ofSeconds(20));
        song3.setTitle("title3");
        song3.setDuration(Duration.ofSeconds(15));
        songDatabase.save(song1);
        songDatabase.save(song2);
        songDatabase.save(song3);
    }

    @Test
    void testGet(){
        Song song = songDatabase.getByName(song1.getTitle());
        Song song2 = songDatabase.get(song.getId()).orElseThrow();
        assertEquals(song.getTitle(), song2.getTitle());
    }

    @Test
    void testGetByName(){

        assertSame(song1.getTitle(), songDatabase.getByName(song1.getTitle()).getTitle());
        assertSame(song2.getTitle(), songDatabase.getByName(song2.getTitle()).getTitle());
        assertSame(song3.getTitle(), songDatabase.getByName(song3.getTitle()).getTitle());

        Song song = new Song();
        song.setTitle("stio");
        song.setDuration(Duration.ofSeconds(10));

        boolean present;
        try{
            songDatabase.getByName(song.getTitle());
            present = true;
        }
        catch(NoResultException e){
            present = false;
        }
        assertFalse(present);
    }

    @Test
    void testGetAllByName(){
        song4.setTitle("title1");
        song4.setDuration(Duration.ofSeconds(15));
        songDatabase.save(song4);
        List<Song> foundSongs = songDatabase.getAllByName(song1.getTitle());
        assertEquals(2, foundSongs.size());
    }

}