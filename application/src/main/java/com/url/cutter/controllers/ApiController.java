package com.url.cutter.controllers;

import com.url.cutter.entities.ShortUrl;
import com.url.cutter.services.UrlApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    private final UrlApiService urlsRestService;

    @Autowired
    public ApiController(UrlApiService urlsRestService) {
        this.urlsRestService = urlsRestService;
    }

    @GetMapping(value = {"", "/"})
    public List<ShortUrl> findAll() {
        return urlsRestService.findAll();
    }
}
