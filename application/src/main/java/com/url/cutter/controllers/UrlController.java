package com.url.cutter.controllers;

import com.url.cutter.exceptions.UrlIsNotValid;
import com.url.cutter.exceptions.UrlSourceValueIsNotValid;
import com.url.cutter.services.interfaces.IUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.url.cutter.entities.ShortUrl;
import com.url.cutter.services.implementations.UrlService;

@Controller
@RequestMapping(value = {"/urls", ""})
public class UrlController {

    @Value(value = "${HOST}")
    private String HOST;

    private final IUrlService urlService;

    @Autowired
    public UrlController(UrlService urlsService) {
        this.urlService = urlsService;
    }

    @GetMapping()
    public String index(@ModelAttribute(name = "url") ShortUrl url) {
        return "home";
    }

    @PostMapping()
    public String save(ShortUrl url, Model model) {
        try {
            ShortUrl shortUrl = urlService.saveUrl(url);
            model.addAttribute("HOST", HOST);
            model.addAttribute("shortenedUrl", shortUrl);
            return "shortened";
        } catch (UrlSourceValueIsNotValid urlSourceValueIsNotValid) {
            return "url_source_value_is_not_valid";
        }
    }

    @GetMapping("/{url}")
    public String redirect(@PathVariable(value = "url") String url, Model model) {
        try {
            String srcUrl = urlService.redirect(url);
            return "redirect:" + srcUrl;
        } catch (UrlIsNotValid urlIsNotValid) {
            model.addAttribute("statusCode", UrlIsNotValid.getCodeStatus());
            model.addAttribute("codeMessage", UrlIsNotValid.getCodeMessage());
            return "404_Not_Found_exception";
        }
    }

}

