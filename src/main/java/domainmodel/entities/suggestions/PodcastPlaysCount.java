package domainmodel.entities.suggestions;

import domainmodel.entities.tracks.Podcast;
import domainmodel.entities.users.Customer;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PODCAST")
public class PodcastPlaysCount extends TrackPlaysCount {

    public PodcastPlaysCount() {
    }

    public PodcastPlaysCount(Customer customer, Podcast podcast, int plays) {
        super(customer, podcast, plays);
    }
}
