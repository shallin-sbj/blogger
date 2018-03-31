package com.blogger.blogger.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class helloController {

    @RequestMapping("hello")
    public String hellWorld() {
        return new Date() +  "     hello world ";
    }
}
