package domainmodel.entities.tracks;

import domainmodel.entities.users.Artist;
import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class Podcast extends Track {
    private String theme;

    public Podcast(){}

    public Podcast(String title, String theme, int minutes, int seconds, List<Artist> authors) {
        super(title, minutes, seconds, authors);
        this.theme = theme;
    }


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        StringBuilder authors = new StringBuilder();
        for (Artist artist : getAuthors()) {
            authors.append(artist.getStageName()).append("\n");
        }
        return "Title: " + getTitle() + "\n" + "Authors: " + authors + "Theme " + getTheme() + "\n" + "Duration: " + getDuration();
    }
}
