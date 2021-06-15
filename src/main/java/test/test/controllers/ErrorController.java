package test.test.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping(value = "/error")
    public String handleError(HttpServletRequest httpServletRequest, Model model) {
        Integer statusCode = (Integer) httpServletRequest.getAttribute("javax.servlet.error.status_code");
        model.addAttribute("statusCode", statusCode);
        return "error";
    }
}
