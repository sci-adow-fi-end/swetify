package domainmodel;

import java.time.Duration;
import java.util.*;


public abstract class Track{


    public Track() {
    }


    private String title;


    private Duration duration;


    private ArrayList<Artist> artists;


    public void Track(String title, Duration duration, ArrayList<Artist> artists) {
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