package domainmodel.entities.tracks;

import domainmodel.entities.users.Artist;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Song extends Track {
    private String lyrics;

    public Song(){}

    public Song(String title, String lyrics, int minutes, int seconds, List<Artist> authors) {
        super(title, minutes, seconds, authors);
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }
}
