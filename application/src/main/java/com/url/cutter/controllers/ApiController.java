package com.url.cutter.controllers;

import com.url.cutter.entities.ShortUrl;
import com.url.cutter.services.implementations.UrlApiService;
import com.url.cutter.services.interfaces.IUrlApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    //TODO: delete ffs
    private final IUrlApiService urlApiService;

    @Autowired
    public ApiController(UrlApiService urlsApiService) {
        this.urlApiService = urlsApiService;
    }

    @GetMapping(value = {"", "/"})
    public List<ShortUrl> findAll() {
        return urlApiService.findAll();
    }

}
