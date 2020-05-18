package com.chuangsys_manager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Hello {
    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}