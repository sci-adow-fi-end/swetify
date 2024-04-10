package domainmodel;

import java.util.*;


public abstract class Playlist<T> {


    public Playlist() {
    }


    private String title;


    private LinkedList<T> tracks;


    public void addTrack(T track) {
        // TODO implement here
    }

    public String getTitle() {
        // TODO implement here
        return "";
    }

    public void setTitle(String value) {
        // TODO implement here
    }

}