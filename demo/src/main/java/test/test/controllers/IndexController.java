package test.test.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import test.test.entities.Url;
import test.test.exceptions.UrlIsNotValid;
import test.test.services.UrlsService;

@Controller
@RequestMapping(value = {"/index", ""})
public class IndexController {

    private final UrlsService urlsService;

    @Autowired
    public IndexController(UrlsService urlsService) {
        this.urlsService = urlsService;
    }

    @GetMapping()
    public String index(@ModelAttribute(name = "url") Url url) {
        return "index";
    }

    @PostMapping()
    public String save(Url url, Model model) {
        urlsService.saveUrl(url, model);
        return "saved";
    }

    @GetMapping("/{url}")
    public String redirect(@PathVariable(value = "url") String url, Model model) throws UrlIsNotValid {
        try {
            String srcUrl = urlsService.redirect(url);
            return "redirect:" + srcUrl;
        } catch (UrlIsNotValid urlIsNotValid) {
            model.addAttribute("statusCode", urlIsNotValid.getCodeStatus());
            return "error";
        }
    }
}

