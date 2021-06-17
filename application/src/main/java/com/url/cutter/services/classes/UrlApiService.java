package com.url.cutter.services.classes;

import com.url.cutter.services.interfaces.IUrlApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.repositories.interfaces.IUrlRepository;

import java.util.List;

@Service
public class UrlApiService implements IUrlApiService {

    private final IUrlRepository urlsRepo;

    @Autowired
    public UrlApiService(IUrlRepository urlsRepo) {
        this.urlsRepo = urlsRepo;
    }

    public List<ShortUrl> findAll() {
        return urlsRepo.findAll();
    }

}
