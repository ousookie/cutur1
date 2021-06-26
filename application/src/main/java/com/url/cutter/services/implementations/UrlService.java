package com.url.cutter.services.implementations;

import com.url.cutter.exceptions.UrlIsNotValid;
import com.url.cutter.utilities.UrlShortCutter;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.exceptions.UrlIsAlreadySaved;
import com.url.cutter.exceptions.UrlSourceValueIsNotValid;
import com.url.cutter.exceptions.UrlTimeStampIsNotValid;
import com.url.cutter.repositories.interfaces.IUrlRepository;
import com.url.cutter.services.interfaces.IUrlService;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class UrlService implements IUrlService {

    private IUrlRepository urlRepository;

    @Value(value = "${TIME_STAMP_LIMIT}")
    private long TIME_STAMP_LIMIT;

    @Autowired
    public UrlService(IUrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public UrlService() {

    }

    public void setUrlRepository(IUrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void checkUrlSourceValue(ShortUrl shortUrl) throws UrlSourceValueIsNotValid {
        if (!UrlValidator.getInstance().isValid(shortUrl.getSrcUrl().trim())) {
            throw new UrlSourceValueIsNotValid();
        }
    }

    public void checkUrlTimeStamp(ShortUrl shortUrl) throws UrlTimeStampIsNotValid {
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        if ((timeStampSeconds - shortUrl.getTimeStamp()) >= TIME_STAMP_LIMIT) {
            throw new UrlTimeStampIsNotValid();
        }
    }

    @Transactional(readOnly = true)
    public void checkAlreadySavedUrl(ShortUrl shortUrl) throws UrlIsAlreadySaved {
        if (getShortUrl(shortUrl) != null) {
            throw new UrlIsAlreadySaved();
        }
    }

    @Transactional(readOnly = true)
    public ShortUrl getShortUrl(ShortUrl url) {
        return urlRepository.getShortUrlBySrcUrl(url.getSrcUrl().trim());
    }

    @Transactional(readOnly = true)
    public int getLastInsertEntityId() {
        return urlRepository.getLastInsertEntityId().intValue();
    }

    @Transactional
    public void processingUrl(ShortUrl url) throws RuntimeException {
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        try {
            checkUrlSourceValue(url);
            checkAlreadySavedUrl(url);
            url.setSrcUrl(url.getSrcUrl().trim());
            url.setTimeStamp(timeStampSeconds);
            url.setCutUrl(UrlShortCutter.getBase62String(getLastInsertEntityId()));
        } catch (UrlSourceValueIsNotValid ignored) {
            throw new UrlSourceValueIsNotValid();
        } catch (UrlIsAlreadySaved urlIsAlreadySaved) {
            try {
                ShortUrl alreadySavedUrl = getShortUrl(url);
                checkUrlTimeStamp(alreadySavedUrl);
                throw new UrlIsAlreadySaved();
            } catch (UrlTimeStampIsNotValid urlTimeStampIsNotValid) {
                deleteById(url);
                processingUrl(url);
            }
        }
    }

    @Transactional
    public ShortUrl saveUrl(ShortUrl shortUrl) {
        try {
            processingUrl(shortUrl);
            return urlRepository.save(shortUrl);
        } catch (UrlIsAlreadySaved urlIsAlreadySaved) {
            return getShortUrl(shortUrl);
        }
    }

    @Transactional
    public void deleteById(ShortUrl shortUrl) {
        urlRepository.deleteById(getShortUrl(shortUrl).getId());
    }

    @Transactional(noRollbackFor = UrlIsNotValid.class)
    public String redirect(String cutUrl) throws UrlIsNotValid {
        try {
            getShortUrl(urlRepository.getShortUrlByCutUrl(cutUrl));
            checkUrlTimeStamp(urlRepository.getShortUrlByCutUrl(cutUrl));
            return urlRepository.getShortUrlByCutUrl(cutUrl).getSrcUrl();
        } catch (NullPointerException nullPointerException) {
            throw new UrlIsNotValid();
        } catch (UrlTimeStampIsNotValid urlTimeStampIsNotValid) {
            urlRepository.deleteById(urlRepository.getShortUrlByCutUrl(cutUrl).getId());
            throw new UrlTimeStampIsNotValid();
        }
    }

}

