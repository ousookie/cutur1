package test.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.test.entities.Url;
import test.test.services.UrlsRestService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestControllerClass {
    private final UrlsRestService urlsRestService;

    @Autowired
    public RestControllerClass(UrlsRestService urlsRestService) {
        this.urlsRestService = urlsRestService;
    }

    @GetMapping(value = {"", "/"})
    public List<Url> findAll() {
        return urlsRestService.findAll();
    }
}
