package com.url.cutter.services.interfaces;

import com.url.cutter.entities.ShortUrl;

import java.util.List;

public interface IUrlApiService {

    /**
     * @return list of all saved url entities
     */
    List<ShortUrl> findAll();

}
