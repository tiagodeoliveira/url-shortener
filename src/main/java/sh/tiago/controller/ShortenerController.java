package sh.tiago.controller;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sh.tiago.service.UrlService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tiago.oliveira on 30/01/17.
 * Deals with the shortener functions, responsible for generating the short URL and getting back the original one.
 */
@RestController
public class ShortenerController {

    static Logger LOGGER = Logger.getLogger(ShortenerController.class);
    private UrlValidator validator = new UrlValidator();

    @Autowired
    private UrlService urlService;

    @RequestMapping(value = "/shorten", method = RequestMethod.POST)
    public String createShortenedUrl(@RequestParam(value = "url", required = true) String url, HttpServletResponse response) throws IOException {
        LOGGER.info("Getting short url for " + url);
        String completeUrl = urlService.getCompleteUrl(url);

        if (!this.validator.isValid(completeUrl)) {
            response.sendError(400, "Invalid URL");
        }

        return this.urlService.getOrCreateShortUrl(completeUrl);
    }

    @RequestMapping(value = "/{shortUrl}", method = RequestMethod.GET)
    public void navigateToUrlId(@PathVariable("shortUrl") String shortUrl, HttpServletResponse response) throws IOException {
        LOGGER.info("Getting long url for " + shortUrl);
        String longUrl = this.urlService.getLongUrl(shortUrl);

        if (longUrl.isEmpty()) {
            LOGGER.info("No URL found for" + shortUrl);
            response.sendRedirect("/notFound/" + shortUrl);
        } else {
            String completeUrl = urlService.getCompleteUrl(longUrl);
            LOGGER.info("Redirecting to " + completeUrl);
            response.sendRedirect(completeUrl);
        }
    }
}
