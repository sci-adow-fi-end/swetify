package model;

import java.util.*;

/**
 * 
 */
public abstract class Playlist {

    /**
     * Default constructor
     */
    public Playlist() {
    }

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private LinkedList<T> tracks;

    /**
     * @param track 
     * @return
     */
    public void addTrack(T track) {
        // TODO implement here
        return null;
    }

    /**
     * @return
     */
    public String getTitle() {
        // TODO implement here
        return "";
    }

    /**
     * @param value
     */
    public void setTitle(String value) {
        // TODO implement here
    }

}