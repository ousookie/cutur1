package com.url.cutter.services;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.url.cutter.core.UrlShortCutter;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.exceptions.UrlIsNotValid;
import com.url.cutter.repos.UrlRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UrlService {

    private final UrlRepository urlsRepo;

    @Value("${application.TIME_STAMP_LIMIT}")
    private Long TIME_STAMP_LIMIT;

    @Autowired
    public UrlService(UrlRepository urlsRepo) {
        this.urlsRepo = urlsRepo;
    }

    public boolean isUrlValid(ShortUrl url) {
        return UrlValidator.getInstance().isValid(url.getSrcUrl().trim());
    }

    public boolean isUrlTimeStampValid(ShortUrl url) {
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        return (timeStampSeconds - url.getTimeStamp()) < TIME_STAMP_LIMIT;
    }

    @Transactional
    public ShortUrl getShortUrl(ShortUrl url) {
        return urlsRepo.getShortUrlBySrcUrl(url.getSrcUrl().trim());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveUrl(ShortUrl url) {
        if (isUrlValid(url) && urlsRepo.getShortUrlBySrcUrl(url.getSrcUrl().trim()) == null) {
            Instant instant = Instant.now();
            Long timeStampSeconds = instant.getEpochSecond();
            url.setTimeStamp(timeStampSeconds);
            url.setSrcUrl(url.getSrcUrl().trim());
            urlsRepo.save(url);
            ShortUrl savedUrl = urlsRepo.getShortUrlBySrcUrl(url.getSrcUrl().trim());
            url.setCutUrl(UrlShortCutter.getBase62String(savedUrl.getId()));
            urlsRepo.setCutUrl(UrlShortCutter.getBase62String(savedUrl.getId()), savedUrl.getId());
        } else if (isUrlValid(url) && urlsRepo.getShortUrlBySrcUrl(url.getSrcUrl().trim()) != null) {
            if (!isUrlTimeStampValid(urlsRepo.getShortUrlBySrcUrl(url.getSrcUrl().trim()))) {
                urlsRepo.deleteById(urlsRepo.getShortUrlBySrcUrl(url.getSrcUrl()).getId());
                saveUrl(url);
            }
        }
    }

    public String redirect(String cutUrl) throws UrlIsNotValid {
        if (isUrlTimeStampValid(urlsRepo.getShortUrlByCutUrl(cutUrl))) {
            return urlsRepo.getShortUrlByCutUrl(cutUrl).getSrcUrl();
        } else {
            urlsRepo.deleteById(urlsRepo.getShortUrlByCutUrl(cutUrl).getId());
            throw new UrlIsNotValid();
        }
    }
}

