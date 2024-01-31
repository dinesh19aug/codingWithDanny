package com.javahabit.d2ccommerceapp.controller;

import com.javahabit.d2ccommerceapp.service.IService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class BookController {
    @Qualifier("book-service")
    IService bookService;
    public BookController(IService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/book")
    public String getBook( Model model){
        model.addAttribute("books", bookService.apply());
        // (Collection<SimpleGrantedAuthority>)    SecurityContextHolder.getContext().getAuthentication().getAuthorities();
         List roles =  SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(u-> u.getAuthority().substring(5)).collect(Collectors.toList()) ;
         String userName =   SecurityContextHolder.getContext().getAuthentication().getName();
         return "book";

    }

    @GetMapping("/login")
    public String login()
    {
        return "/login";
    }

    @GetMapping("")
    public ModelAndView loginBack(ModelMap model)
    {
        return new ModelAndView("redirect:/book", model);
    }


}
