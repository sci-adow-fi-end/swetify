package domainmodel;

import jakarta.persistence.*;

@Entity
@Table(name = "Songs")
public class Song extends Track {
    private String lyrics;

    public Song() {
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
        notifyObservers();
    }
}
