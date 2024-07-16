package domainmodel.entities.suggestions;

import domainmodel.entities.User;
import domainmodel.entities.track.Podcast;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PODCAST")
public class PodcastPlaysCount extends TrackPlaysCount {

    public PodcastPlaysCount() {
    }

    public PodcastPlaysCount(User customer, Podcast podcast, int plays) {
        super(customer, podcast, plays);
    }
}
