package com.javahabit.d2ccommerceapp.controller;

import com.javahabit.d2ccommerceapp.service.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BookController {
    @Qualifier("book-service")
    IService bookService;
    public BookController(IService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public String greeting( Model model){
        model.addAttribute("books", bookService.apply());
        return "book";

    }
}
