package domainmodel.entities.tracks;

import domainmodel.entities.users.Artist;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Song extends Track {
    private String genre;

    public Song(){}

    public Song(String title, String genre, int minutes, int seconds, List<Artist> authors) {
        super(title, minutes, seconds, authors);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        StringBuilder authors = new StringBuilder();
        for (Artist artist : getAuthors()) {
            authors.append(artist.getStageName()).append("\n");
        }
        return "Title: " + getTitle() + "\n" + "Authors: " + authors + "Genre " + getGenre() + "\n" + "Duration: " + getDuration();
    }
}
