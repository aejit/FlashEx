package com.flashex.demo.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RefreshScope
@Controller
public class DemoController {

    @RequestMapping(value = "/**/{[path:[^\\.]*}")
    public String redirectToAngular() {
        return "forward:/";
    }

    @GetMapping("/hello")
    public  String hello() {
        System.out.println("In demo controller");
        return "hi working";
    }

}