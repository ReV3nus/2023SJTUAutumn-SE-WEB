package com.example.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin("*")
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("getAuthor")
    public String getAuthor(@RequestParam("title") String title) {
        System.out.println("received title:"+title);
        return restTemplate.getForObject("http://book/getAuthor?title="+title, String.class);
    }

    @RequestMapping("hello")
    public String hello() {
        return restTemplate.getForObject("http://book/sayHello", String.class);
    }
}
