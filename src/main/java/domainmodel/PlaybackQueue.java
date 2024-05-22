package domainmodel;

import java.util.*;


public class PlaybackQueue {

    private final Deque<Track> queue = new ArrayDeque<Track>();

    public PlaybackQueue() {
    }


    public void PlaybackQueue() {
        // TODO implement here
    }


    public void addTrackOnTop(Track track) {
        queue.addFirst(track);
    }

    public void addTrackAtBottom(Track track){
        queue.addLast(track);
    }

    public void addPlaylistOnTop(Playlist playlist) {
        for (Track t : playlist){

        }

    }

    public void addPlaylist(Playlist playlist) {
        // TODO implement here

    }


    public void clear() {
        // TODO implement here
    }

}