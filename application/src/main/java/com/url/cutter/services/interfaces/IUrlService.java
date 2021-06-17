package com.url.cutter.services.interfaces;

import com.url.cutter.entities.ShortUrl;

public interface IUrlService {

    boolean isUrlValid(ShortUrl url);

    boolean isUrlTimeStampValid(ShortUrl url);

    ShortUrl getShortUrl(ShortUrl url);

    void saveUrl(ShortUrl url);

    String redirect(String cutUrl);

}
