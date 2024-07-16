package domainmodel.entities.suggestions;

import domainmodel.entities.User;
import domainmodel.entities.track.Song;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SONG")
public class SongPlaysCount extends TrackPlaysCount {

    public SongPlaysCount() {
    }

    public SongPlaysCount(User customer, Song song, int plays) {
        super(customer, song, plays);
    }
}
