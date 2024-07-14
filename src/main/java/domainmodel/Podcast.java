package domainmodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "Podcasts")
public class Podcast extends Track {
    private String theme;

    public Podcast(String title, String theme, int minutes, int seconds, List<Artist> authors) {
        super(title, minutes, seconds, authors);
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
        notifyObservers();
    }
}