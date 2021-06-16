package com.url.cutter.controllers;

import com.url.cutter.core.UrlShortCutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.exceptions.UrlIsNotValid;
import com.url.cutter.services.UrlService;

@Controller
@RequestMapping(value = {"/index", ""})
public class UrlController {

    @Value("${application.HOST}")
    private String HOST;

    private final UrlService urlsService;

    @Autowired
    public UrlController(UrlService urlsService) {
        this.urlsService = urlsService;
    }

    @GetMapping()
    public String index(@ModelAttribute(name = "url") ShortUrl url) {
        return "home";
    }

    @PostMapping()
    public String save(ShortUrl url, Model model) {
        urlsService.saveUrl(url);
        model.addAttribute("HOST", HOST);
        model.addAttribute("shortenedUrl", urlsService.getShortUrl(url));
        return "shortened";
    }

    @GetMapping("/{url}")
    public String redirect(@PathVariable(value = "url") String url,
                           Model model) throws UrlIsNotValid {
        try {
            String srcUrl = urlsService.redirect(url);
            return "redirect:" + srcUrl;
        } catch (UrlIsNotValid urlIsNotValid) {
            model.addAttribute("statusCode", urlIsNotValid.getCodeStatus());
            return "error";
        }
    }
}

