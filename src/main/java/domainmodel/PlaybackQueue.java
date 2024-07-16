package domainmodel;

import domainmodel.entities.collections.Playlist;
import domainmodel.entities.tracks.Track;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;


public class PlaybackQueue implements Iterable<Track>{

    private final Deque<Track> queue = new ArrayDeque<>();

    public PlaybackQueue() {

    }

    public void addTrackOnTop(Track track) {
        queue.addFirst(track);
    }

    public void addTrackAtBottom(Track track){
        queue.addLast(track);
    }

    public void addPlaylistOnTop(Playlist<Track> playlist) {
        ListIterator<Track> listIterator = playlist.getTracks().
                listIterator(playlist.getTracks().size());
        while (listIterator.hasPrevious()){
            queue.addFirst(listIterator.previous());
        }
    }

    public void addPlaylistAtBottom(Playlist<Track> playlist) {
        for (Track t : playlist){
            queue.addLast(t);
        }

    }

    public Track getNextSong(){
        return queue.pop();
    }


    public void clear() {
        queue.clear();
    }

    @Override
    public Iterator<Track> iterator() {
        return queue.iterator();
    }
}