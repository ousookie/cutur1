package com.url.cutter.services.interfaces;

import com.url.cutter.entities.ShortUrl;

import java.util.List;

public interface IUrlApiService {

    List<ShortUrl> findAll();

}
