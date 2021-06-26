package com.url.cutter;

import com.url.cutter.entities.ShortUrl;
import com.url.cutter.exceptions.UrlSourceValueIsNotValid;
import com.url.cutter.exceptions.UrlTimeStampIsNotValid;
import com.url.cutter.repositories.interfaces.IUrlRepository;
import com.url.cutter.services.implementations.UrlService;
import com.url.cutter.utilities.UrlShortCutter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UnitTests {

    @Spy
    UrlService urlService;

    @Mock
    IUrlRepository urlRepository;

    @Test
    public void getBase62StringTest() {
        final String expectedResult = "qi";
        final String actualResult = UrlShortCutter.getBase62String(1000);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void checkUrlSourceValueTest() {
        final ShortUrl testShortUrl = new ShortUrl(1, "test.com", "b", 0L);
        assertThrows(UrlSourceValueIsNotValid.class, () -> urlService.checkUrlSourceValue(testShortUrl));
    }

    @Test
    public void checkUrlTimeStampTest() {
        final ShortUrl testShortUrl = new ShortUrl(1, "http://test.com", "b", 0L);
        assertThrows(UrlTimeStampIsNotValid.class, () -> urlService.checkUrlTimeStamp(testShortUrl));
    }

    @Test
    public void getShortUrlTest() {
        urlService.setUrlRepository(urlRepository);
        ShortUrl testShortUrl = new ShortUrl(1, "http://test.com", "b", 0L);
        Mockito.when(urlService.getShortUrl(testShortUrl)).thenReturn(testShortUrl);
        Assertions.assertDoesNotThrow(() -> urlService.getShortUrl(testShortUrl));
    }

    @Test
    public void processingUrlTest() {
        urlService.setUrlRepository(urlRepository);
        ShortUrl shortUrl = new ShortUrl(1, "http://test.com", null, 0L);
        Mockito.when(urlRepository.getLastInsertEntityId()).thenReturn(BigInteger.ONE);
        urlService.processingUrl(shortUrl);
        assertNotNull(shortUrl.getCutUrl());
        assertNotEquals(0L, shortUrl.getTimeStamp());
    }

    @Test
    public void redirectTest() {
        urlService.setUrlRepository(urlRepository);
        ShortUrl testShortUrl = new ShortUrl(1, "http://test.com", "b", System.currentTimeMillis());
        Mockito.when(urlRepository.getShortUrlByCutUrl("a")).thenReturn(testShortUrl);
        final String srcUrlValue = urlService.redirect("a");
        assertEquals(testShortUrl.getSrcUrl(), srcUrlValue);
    }

}


