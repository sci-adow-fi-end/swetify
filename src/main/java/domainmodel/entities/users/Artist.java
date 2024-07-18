package domainmodel.entities.users;

import domainmodel.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Artists")
public class Artist extends BaseUser{
    
    private String stageName;

    private String biography;

    private int followers = 0;

    public Artist(){}

    public Artist(String username, String password, String stageName, String biography) {
        this.username = username;
        this.password = password;
        this.stageName = stageName;
        this.biography = biography;
    }


    public String getStageName() {
        return stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;

    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    @Override
    public String toString() {
        return
                "" + stageName + '\'' +
                ", biography='" + biography + '\'';

    }
}