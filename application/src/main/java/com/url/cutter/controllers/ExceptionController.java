package com.url.cutter.controllers;

import com.url.cutter.exceptions.UrlIsNotValid;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ExceptionController implements ErrorController {

    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest httpServletRequest, Model model) {
        int statusCode = (int) httpServletRequest.getAttribute("javax.servlet.error.status_code");
        model.addAttribute("statusCode", statusCode);
        model.addAttribute("codeMessage", UrlIsNotValid.getCodeMessage());
        return "404_Not_Found_exception";
    }

}
