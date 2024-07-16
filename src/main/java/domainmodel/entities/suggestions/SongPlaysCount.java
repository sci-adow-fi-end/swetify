package domainmodel.entities.suggestions;

import domainmodel.entities.tracks.Song;
import domainmodel.entities.users.Customer;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SONG")
public class SongPlaysCount extends TrackPlaysCount {

    public SongPlaysCount() {
    }

    public SongPlaysCount(Customer customer, Song song, int plays) {
        super(customer, song, plays);
    }
}
