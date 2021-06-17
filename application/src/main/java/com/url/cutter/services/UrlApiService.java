package com.url.cutter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.repositories.IUrlRepository;

import java.util.List;

@Service
public class UrlApiService {

    private final IUrlRepository urlsRepo;

    @Autowired
    public UrlApiService(IUrlRepository urlsRepo) {
        this.urlsRepo = urlsRepo;
    }

    public List<ShortUrl> findAll() {
        return urlsRepo.findAll();
    }
}
