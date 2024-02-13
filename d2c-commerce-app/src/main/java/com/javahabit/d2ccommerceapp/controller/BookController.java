package com.javahabit.d2ccommerceapp.controller;

import com.javahabit.d2ccommerceapp.service.book.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping
public class BookController {

    IService bookService;
    IService bookUserService;
    IService discountService;
    public BookController(@Qualifier("book-service") IService bookService,
                          @Qualifier("book-user-service") IService bookUserService,
                          @Qualifier("discount-service") IService discountService) {
        this.bookService = bookService;
        this.bookUserService = bookUserService;
        this.discountService = discountService;
    }

    @GetMapping("/book")
    public String getBook( Model model, HttpServletRequest request){


        model.addAttribute("books", bookService.process(null));
        model.addAttribute("customerName", getUserName());
        model.addAttribute("userRole", getUserRole());
        model.addAttribute("state", bookUserService.process(getUserName()));
        model.addAttribute("discount", discountService.process(null));
        return "book";

    }

    private static String getUserName() {
        String userName =   SecurityContextHolder.getContext().getAuthentication().getName();
        return userName;
    }

    private static String getUserRole() {
        List roles =  SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(u-> u.getAuthority()).collect(Collectors.toList()) ;
        return roles.get(0).toString();

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
