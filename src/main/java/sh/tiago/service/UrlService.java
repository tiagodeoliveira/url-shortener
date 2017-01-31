package sh.tiago.service;

import com.google.common.hash.Hashing;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sh.tiago.entity.Url;
import sh.tiago.repository.UrlRepository;

import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Created by tiago.oliveira on 30/01/17.
 */
@Service
public class UrlService {

    static Logger LOGGER = Logger.getLogger(UrlService.class);

    @Autowired
    private UrlRepository urlRepository;

    /**
     * If there is already a short ID (hash) for a given URL only returns it,
     *  if there is no match, saves the mapping on database.
     * */
    public String getOrCreateShortUrl(String longUrl) {
        String shortUrl = this.getUrlHash(longUrl);

        Optional<Url> urlEntity = Optional.ofNullable(urlRepository.
                findOne(shortUrl).
                orElse(this.urlRepository.save(new Url(shortUrl, longUrl))));

        return urlEntity.get().getShortUrl();
    }

    /**
     * If no id on database matchs with the short id, returns an empty value.
     * */
    public String getLongUrl(String shortUrl) {
        return urlRepository.
                findOne(shortUrl).orElse(new Url("", "")).getLongUrl();
    }

    private String getUrlHash(String longUrl) {
        return Hashing.murmur3_32().hashString(longUrl, Charset.defaultCharset()).toString();
    }

    /**
     * Adds the HTTP in front of the url case that is missing,
     *  this is useful when the 302 REDIRECT response is sent to the client
     * */
    public String getCompleteUrl(String longUrl) {
        String completeUrl = longUrl;
        if (!completeUrl.contains("http")) {
            completeUrl = "http://" + completeUrl;
        }
        return completeUrl;
    }

}
