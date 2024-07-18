import businesslogic.customer.PlaybackHandler;
import businesslogic.utility.Session;
import domainmodel.entities.tracks.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class PlaybackHandlerTest extends BaseTest{

    private final Session session = new Session();
    private final Song track1 = new Song();
    private final Song track2 = new Song();
    private final PlaybackHandler playbackHandler = new PlaybackHandler();
    private PlaybackHandler.PlaybackThread playbackThread;

    @BeforeEach
    public void setUp() {
        super.setUp();
        track1.setTitle("Song 1");
        track1.setDuration(Duration.ofSeconds(1)); // 10 seconds
        track2.setTitle("Song 2");
        track2.setDuration(Duration.ofSeconds(20)); // 20 seconds

        session.setPlayingTrack(track1);
        session.getQueue().addTrackAtBottom(track2);

        playbackThread = playbackHandler.createPlaybackQueue(session);
    }

    @Test
    public void testInitialPlaybackState() {
        playbackThread.start();
        assertTrue(playbackThread.isAlive());
        assertEquals(track1, session.getPlayingTrack());
        assertTrue(playbackThread.isPaused());

        playbackThread.interrupt();
        try{
            playbackThread.join();
        }
        catch(Exception ignored){}
    }

    @Test
    public void testPlayPauseFunctionality() {
        assertTrue(playbackThread.isPaused());
        playbackThread.playPause();
        assertFalse(playbackThread.isPaused());
    }

    @Test
    public void testSkipFunctionality() {
        assertFalse(playbackThread.isSkipped());
        playbackThread.skip();
        assertTrue(playbackThread.isSkipped());
    }

    @Test
    public void testTrackSwitching() throws InterruptedException {
        playbackThread.start();

        // Wait a bit for the thread to start and switch tracks
        Thread.sleep(2000);  // Adjust the sleep duration as needed

        assertEquals(track2, session.getPlayingTrack());
        assertFalse(playbackThread.isSkipped());

        playbackThread.interrupt();
        try{
            playbackThread.join();
        }
        catch(Exception ignored){}
    }

    @Test
    public void testHandler() throws InterruptedException {
        ByteArrayInputStream input = new ByteArrayInputStream("1".getBytes());
        System.setIn(input);
        playbackHandler.update(session);

        Thread.sleep(2000);
    }
}
