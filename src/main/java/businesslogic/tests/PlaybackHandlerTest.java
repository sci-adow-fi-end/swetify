package businesslogic.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import domainmodel.Song;
import businesslogic.PlaybackHandler;
import businesslogic.State;

import java.io.ByteArrayInputStream;
import java.time.Duration;

public class PlaybackHandlerTest {

    private final State state = new State();
    private final Song track1 = new Song();
    private final Song track2 = new Song();
    private final PlaybackHandler playbackHandler = new PlaybackHandler();
    private PlaybackHandler.PlaybackThread playbackThread;

    @BeforeEach
    public void setUp() {
        track1.setTitle("Song 1");
        track1.setDuration(Duration.ofSeconds(10)); // 5 minutes
        track2.setTitle("Song 2");
        track2.setDuration(Duration.ofSeconds(20)); // 3 minutes 20 seconds

        state.setPlayingTrack(track1);
        state.getQueue().addTrackAtBottom(track2);

        playbackThread = playbackHandler.createPlaybackQueue(state);
    }

    @Test
    public void testInitialPlaybackState() {
        playbackThread.start();
        assertTrue(playbackThread.isAlive());
        assertEquals(track1, state.getPlayingTrack());
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
        Thread.sleep(11000);  // Adjust the sleep duration as needed

        assertEquals(track2, state.getPlayingTrack());
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
        playbackHandler.update(state);

        Thread.sleep(2000);
    }

    // Add more tests to cover other behaviors and edge cases
}
