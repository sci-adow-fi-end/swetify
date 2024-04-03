package model;

import java.util.*;

/**
 * 
 */
public abstract class Track extends Song {

    /**
     * Default constructor
     */
    public Track() {
    }

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private Duration duration;

    /**
     * 
     */
    private ArrayList<Artist> artists;

    /**
     * @param title 
     * @param duration 
     * @param artists
     */
    public void Track(String title, Duration duration, ArrayList<Artist> artists) {
        // TODO implement here
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