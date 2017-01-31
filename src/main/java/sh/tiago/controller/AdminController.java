package sh.tiago.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by tiago.oliveira on 30/01/17.
 * This controller is responsible for dealing with admin tasks:
 *  provide UI
 *  handling the exceptions
 * TODO: The error method should be using an ExceptionHandler and an exception should be raised when
 *  the long url is not found for a short URL
 */
@Controller
public class AdminController {

    @RequestMapping(value = "/notFound/{id}")
    public String error(@PathVariable("id") String id, Model model) {
        model.addAttribute("id", id);
        return "error";
    }

    @RequestMapping(value = "/")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String index(Model model) {
        return "index";
    }
}
