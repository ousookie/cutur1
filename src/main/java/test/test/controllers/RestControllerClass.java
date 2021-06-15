package test.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.test.entities.Url;
import test.test.repos.UrlsRepo;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestControllerClass {
    private final UrlsRepo urlsRepo;

    @Autowired
    public RestControllerClass(UrlsRepo urlsRepo) {
        this.urlsRepo = urlsRepo;
    }

    @GetMapping(value = {"", "/"})
    public List<Url> findAll() {
        return urlsRepo.findAll();
    }
}
