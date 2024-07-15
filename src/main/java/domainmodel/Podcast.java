package domainmodel;

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
        notifyObservers();
    }
}
