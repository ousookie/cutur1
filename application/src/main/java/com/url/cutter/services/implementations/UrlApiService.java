package com.url.cutter.services.implementations;

import com.url.cutter.services.interfaces.IUrlApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.repositories.interfaces.IUrlRepository;

import java.util.List;

@Service
public class UrlApiService implements IUrlApiService {

    private final IUrlRepository urlRepository;

    @Autowired
    public UrlApiService(IUrlRepository urlsRepo) {
        this.urlRepository = urlsRepo;
    }

    public List<ShortUrl> findAll() {
        return urlRepository.findAll();
    }

}
