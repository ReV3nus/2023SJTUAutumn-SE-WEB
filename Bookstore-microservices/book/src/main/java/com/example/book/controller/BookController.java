package com.example.book.controller;

import com.example.book.service.BookService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class BookController {
    @Autowired
    BookService bookService;
    @RequestMapping("sayHello")
    public String sayHello() {
        return "Hello Cloud";
    }
    @RequestMapping("getAuthor")
    public String getAuthor(@RequestParam String title)
    {
        return bookService.getAuthorByName(title);
    }
}
