package com.url.cutter;

import com.url.cutter.entities.ShortUrl;
import com.url.cutter.exceptions.UrlIsAlreadySaved;
import com.url.cutter.exceptions.UrlSourceValueIsNotValid;
import com.url.cutter.exceptions.UrlTimeStampIsNotValid;
import com.url.cutter.repositories.interfaces.IUrlRepository;
import com.url.cutter.services.implementations.UrlService;
import com.url.cutter.utilities.UrlShortCutter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UrlService urlService;

    @Autowired
    private IUrlRepository iUrlRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void getShortUrl() {
        int id = iUrlRepository.getLastInsertEntityId().intValue() + 1;
        ShortUrl shortUrl = new ShortUrl(id, "http://test.ru", UrlShortCutter.getBase62String(id), 0L);
        urlService.saveUrl(shortUrl);
        ShortUrl copiedShortUrl = urlService.getShortUrl(shortUrl);
        assert copiedShortUrl.equals(shortUrl);
    }

    @Test
    public void checkAlreadySavedUrl() throws UrlIsAlreadySaved {
        try {
            ShortUrl alreadySavedUrl = iUrlRepository.getShortUrlBySrcUrl("https://lk.sut.ru/cabinet/?login=yes");
            urlService.checkAlreadySavedUrl(alreadySavedUrl);
        } catch (UrlIsAlreadySaved ignored) {
        }
    }

    @Test
    public void checkUrlTimeStamp() throws UrlTimeStampIsNotValid {
        try {
            ShortUrl shortUrl = new ShortUrl(iUrlRepository.getLastInsertEntityId().intValue() + 1, "src", "cut", 0L);
            urlService.checkUrlTimeStamp(shortUrl);
        } catch (UrlTimeStampIsNotValid ignored) {
        }
    }

    @Test
    public void checkUrlSourceValue() throws UrlSourceValueIsNotValid {
        try {
            ShortUrl shortUrl = new ShortUrl(iUrlRepository.getLastInsertEntityId().intValue() + 1, "src", "cut", 0L);
            urlService.checkUrlSourceValue(shortUrl);
        } catch (UrlSourceValueIsNotValid ignored) {
        }
    }

    @Test
    public void save() throws RuntimeException {
        int id = iUrlRepository.getLastInsertEntityId().intValue() + 1;
        ShortUrl shortUrl = new ShortUrl(id, "http://saveTest.ru", UrlShortCutter.getBase62String(id), 0L);
        urlService.saveUrl(shortUrl);
    }

    @Test
    public void redirect() throws UrlTimeStampIsNotValid {
        String srcUrlValue = "https://lk.sut.ru/cabinet/?login=yes";
        String cutUrlValue = "x";
        assert srcUrlValue.equals(iUrlRepository.getShortUrlByCutUrl(cutUrlValue).getSrcUrl());
    }

}
