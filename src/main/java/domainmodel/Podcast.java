package domainmodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Podcasts")
public class Podcast extends Track {
    private String theme; // TODO: decide if this should be an enum

    public Podcast() {
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
        notifyObservers();
    }
}