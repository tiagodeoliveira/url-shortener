package sh.tiago.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by tiago.oliveira on 30/01/17.
 */
@Entity
public class Url {

    @Id
    private String shortUrl;
    private String longUrl;

    public Url() {

    }

    public Url(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }
}
