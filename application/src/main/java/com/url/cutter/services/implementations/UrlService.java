package com.url.cutter.services.implementations;

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

    private final IUrlRepository urlRepository;

    @Value(value = "${TIME_STAMP_LIMIT}")
    private Long TIME_STAMP_LIMIT;

    @Autowired
    public UrlService(IUrlRepository urlsRepo) {
        this.urlRepository = urlsRepo;
    }

    //TODO: check log4j

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

    public void checkAlreadySavedUrl(ShortUrl shortUrl) throws UrlIsAlreadySaved {
        if (getShortUrl(shortUrl) != null) {
            throw new UrlIsAlreadySaved();
        }
    }

    @Transactional(readOnly = true)
    public ShortUrl getShortUrl(ShortUrl url) {
        return urlRepository.getShortUrlBySrcUrl(url.getSrcUrl().trim());
    }


    public void saveUrl(ShortUrl url) throws RuntimeException {
        Instant instant = Instant.now();
        long timeStampSeconds = instant.getEpochSecond();
        try {
            checkUrlSourceValue(url);
            checkAlreadySavedUrl(url);
            url.setSrcUrl(url.getSrcUrl().trim());
            url.setTimeStamp(timeStampSeconds);
            url.setCutUrl(UrlShortCutter.getBase62String(urlRepository.getLastInsertEntityId().intValue()));
            urlRepository.save(url);
        } catch (UrlSourceValueIsNotValid ignored) {

        } catch (UrlIsAlreadySaved urlIsAlreadySaved) {
            try {
                ShortUrl alreadySavedUrl = getShortUrl(url);
                checkUrlTimeStamp(alreadySavedUrl);
            } catch (UrlTimeStampIsNotValid urlTimeStampIsNotValid) {
                urlRepository.deleteById(getShortUrl(url).getId());
                saveUrl(url);
            }
        }
    }

    public String redirect(String cutUrl) throws UrlTimeStampIsNotValid {
        try {
            checkUrlTimeStamp(urlRepository.getShortUrlByCutUrl(cutUrl));
        } catch (UrlTimeStampIsNotValid urlTimeStampIsNotValid) {
            urlRepository.deleteById(urlRepository.getShortUrlByCutUrl(cutUrl).getId());
            throw new UrlTimeStampIsNotValid();
        }
        return urlRepository.getShortUrlByCutUrl(cutUrl).getSrcUrl();
    }

}

