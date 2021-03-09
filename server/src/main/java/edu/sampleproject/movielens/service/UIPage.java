package edu.sampleproject.movielens.service;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@CrossOrigin
public class UIPage {

    @RequestMapping("/ui")
    public String index() {
        return "index";
    }
}
