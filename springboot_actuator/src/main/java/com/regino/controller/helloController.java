package com.regino.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController {

    @GetMapping("sayHello")
    public String sayHello() {
        return "hello";
    }
}
