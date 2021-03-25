package br.com.alura.forum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("/")
    @ResponseBody()
    public String hello() {
        return "<div style=\"font-weight:bolder;font-size:48px;\">Hello World!</div>";
    }

}
