package com.url.cutter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.repos.UrlRepository;

import java.util.List;

@Service
public class UrlApiService {

    private final UrlRepository urlsRepo;

    @Autowired
    public UrlApiService(UrlRepository urlsRepo) {
        this.urlsRepo = urlsRepo;
    }

    public List<ShortUrl> findAll() {
        return urlsRepo.findAll();
    }
}
