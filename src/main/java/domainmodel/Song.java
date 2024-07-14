package domainmodel;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Songs")
public class Song extends Track {
    private String lyrics;

    public Song(String title, String lyrics, int minutes, int seconds, List<Artist> authors) {
        super(title, minutes, seconds, authors);
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
        notifyObservers();
    }
}
