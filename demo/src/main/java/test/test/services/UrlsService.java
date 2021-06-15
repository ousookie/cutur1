package test.test.services;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import test.test.core.UrlCutter;
import test.test.entities.Url;
import test.test.exceptions.UrlIsNotValid;
import test.test.repos.UrlsRepo;

import java.time.Instant;

@Service
public class UrlsService {

    private final UrlsRepo urlsRepo;
    @Value("${application.HOST}")
    private String HOST;
    @Value("${application.TIMESTAMP_LIMIT}")
    private Long TIMESTAMP_LIMIT;

    @Autowired
    public UrlsService(UrlsRepo urlsRepo) {
        this.urlsRepo = urlsRepo;
    }

    public boolean isValid(Url url) {
        return UrlValidator.getInstance().isValid(url.getSrcUrl().trim());
    }

    public boolean isTimeStampValid(Url url) {
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        return (timeStampSeconds - url.getSavedTime()) < TIMESTAMP_LIMIT;
    }

    public void saveUrl(Url url, Model model) {
        model.addAttribute("host", HOST);
        if (isValid(url) && urlsRepo.getUrlBySrcUrl(url.getSrcUrl().trim()) == null) {
            Instant instant = Instant.now();
            long timeStampSeconds = instant.getEpochSecond();
            url.setSavedTime(timeStampSeconds);
            url.setSrcUrl(url.getSrcUrl().trim());
            urlsRepo.save(url);
            Url savedUrl = urlsRepo.getUrlBySrcUrl(url.getSrcUrl().trim());
            urlsRepo.setCutUrl(UrlCutter.getBase62String(savedUrl.getId()), savedUrl.getId());
            savedUrl.setCutUrl(UrlCutter.getBase62String(savedUrl.getId()));
            model.addAttribute("savedUrl", savedUrl);
        } else if (isValid(url) && urlsRepo.getUrlBySrcUrl(url.getSrcUrl().trim()) != null) {
            if (isTimeStampValid(urlsRepo.getUrlBySrcUrl(url.getSrcUrl().trim()))) {
                model.addAttribute("savedUrl", urlsRepo.getUrlBySrcUrl(url.getSrcUrl().trim()));
            } else {
                urlsRepo.deleteById(urlsRepo.getUrlBySrcUrl(url.getSrcUrl()).getId());
                saveUrl(url, model);
            }
        }
    }

    public String redirect(String cutUrl) {
        if (isTimeStampValid(urlsRepo.getUrlByCutUrl(cutUrl))) {
            return urlsRepo.getUrlByCutUrl(cutUrl).getSrcUrl();
        } else {
            urlsRepo.deleteById(urlsRepo.getUrlByCutUrl(cutUrl).getId());
            throw new UrlIsNotValid();
        }
    }
}

