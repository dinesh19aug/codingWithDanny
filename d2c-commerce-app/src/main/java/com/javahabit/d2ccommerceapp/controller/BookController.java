package com.javahabit.d2ccommerceapp.controller;

import com.javahabit.d2ccommerceapp.service.book.IService;
import jakarta.servlet.http.HttpServletRequest;
import org.ff4j.FF4j;
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

    final FF4j ff4j;
    IService bookService;
    IService bookUserService;
    public BookController(@Qualifier("book-service") IService bookService, @Qualifier("book-user-service") IService bookUserService, FF4j ff4j) {
        this.bookService = bookService;
        this.ff4j = ff4j;
        this.bookUserService = bookUserService;
    }

    @GetMapping("/book")
    public String getBook( Model model, HttpServletRequest request){
        List roles =  SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(u-> u.getAuthority()).collect(Collectors.toList()) ;
        String userName =   SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("books", bookService.process(null));
        model.addAttribute("customerName", userName);
        model.addAttribute("userRole", roles.get(0).toString());
        model.addAttribute("state", bookUserService.process(userName));
//        ff4j.isAllowed(ff4j.getFeature("show-copyright"));
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

    private String getClientIp(HttpServletRequest request)
    {

        String remoteAddr = "";

        if (request != null)
        {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            System.out.println("X-FORWARDED-FOR : " + remoteAddr);
            if (remoteAddr == null || "".equals(remoteAddr))
            {
                remoteAddr = request.getRemoteAddr();

                System.out.println("Remote Addr : " + remoteAddr);
            }
        }

        return remoteAddr;
    }
}
