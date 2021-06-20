package com.url.cutter.controllers;

import com.url.cutter.exceptions.UrlTimeStampIsNotValid;
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
        urlService.saveUrl(url);
        model.addAttribute("HOST", HOST);
        model.addAttribute("shortenedUrl", urlService.getShortUrl(url));
        return "shortened";
    }

    @GetMapping("/{url}")
    public String redirect(@PathVariable(value = "url") String url,
                           Model model) throws UrlTimeStampIsNotValid {
        try {
            String srcUrl = urlService.redirect(url);
            return "redirect:" + srcUrl;
        } catch (UrlTimeStampIsNotValid urlIsNotValid) {
            model.addAttribute("statusCode", UrlTimeStampIsNotValid.getCodeStatus());
            return "exception";
        }
    }

}

