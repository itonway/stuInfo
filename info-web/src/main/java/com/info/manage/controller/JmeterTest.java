package com.info.manage.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JmeterTest {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Boot!";
    }
}
