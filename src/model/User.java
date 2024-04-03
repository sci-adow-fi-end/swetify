package model;

import java.util.*;

/**
 * 
 */
public class User {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private LinkedList<Playlist> playlists;

    /**
     * 
     */
    private Playlist favourites;

    /**
     * 
     */
    private Suggestions suggestions;

    /**
     * 
     */
    private LinkedList<Artist> followedArtits;

    /**
     * @param username
     */
    public void User(String username) {
        // TODO implement here
    }

    /**
     * @return
     */
    public String getUsername() {
        // TODO implement here
        return "";
    }

    /**
     * @param value
     */
    public void setUsername(String value) {
        // TODO implement here
    }

}