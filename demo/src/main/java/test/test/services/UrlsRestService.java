package test.test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.test.entities.Url;
import test.test.repos.UrlsRepo;

import java.util.List;

@Service
public class UrlsRestService {

    private final UrlsRepo urlsRepo;

    @Autowired
    public UrlsRestService(UrlsRepo urlsRepo) {
        this.urlsRepo = urlsRepo;
    }

    public List<Url> findAll() {
        return urlsRepo.findAll();
    }
}
