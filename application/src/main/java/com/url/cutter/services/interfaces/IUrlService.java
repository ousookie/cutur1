package com.url.cutter.services.interfaces;

import com.url.cutter.entities.ShortUrl;
import com.url.cutter.exceptions.UrlIsAlreadySaved;
import com.url.cutter.exceptions.UrlSourceValueIsNotValid;
import com.url.cutter.exceptions.UrlTimeStampIsNotValid;

public interface IUrlService {

    /**
     * @param shortUrl: source url entity
     * @throws UrlSourceValueIsNotValid: url source value is not valid
     */
    void checkUrlSourceValue(ShortUrl shortUrl) throws UrlSourceValueIsNotValid;

    /**
     * @param shortUrl: source url entity
     * @throws UrlTimeStampIsNotValid: url time stamp is not valid
     */
    void checkUrlTimeStamp(ShortUrl shortUrl) throws UrlTimeStampIsNotValid;

    /**
     * @param shortUrl: source url entity
     * @throws UrlIsAlreadySaved: current url is already saved in repository
     */
    void checkAlreadySavedUrl(ShortUrl shortUrl) throws UrlIsAlreadySaved;

    /**
     * @param url: source url entity
     * @return url by source url value
     */
    ShortUrl getShortUrl(ShortUrl url);

    /**
     * @param url: source url entity
     */
    void saveUrl(ShortUrl url) throws RuntimeException;

    /**
     * @param cutUrl: cut url value
     * @return redirect view
     */
    String redirect(String cutUrl);

}
